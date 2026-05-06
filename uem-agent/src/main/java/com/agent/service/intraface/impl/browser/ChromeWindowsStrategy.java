package com.agent.service.intraface.impl.browser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.agent.service.intraface.BrowserHistoryStrategy;
import com.agent.util.ChromiumHistoryUtil;

public class ChromeWindowsStrategy
        implements BrowserHistoryStrategy {

    @Override
    public List<Map<String, Object>> collect(
            String deviceId) {

        List<Map<String, Object>> all = new ArrayList<>();

        try {

            String base = System.getProperty("user.home")
                    + "\\AppData\\Local\\Google\\Chrome\\User Data";

            File dir = new File(base);

            if (!dir.exists()) {
                return all;
            }

            File[] profiles = dir.listFiles();

            if (profiles == null) {
                return all;
            }

            for (File profile : profiles) {

                if (profile.isDirectory()
                        && (profile.getName().equals("Default")
                                || profile.getName().startsWith("Profile"))) {

                    String historyPath = profile.getAbsolutePath()
                            + "\\History";

                    all.addAll(
                            ChromiumHistoryUtil.readHistory(
                                    "CHROME",
                                    historyPath,
                                    profile.getName(),
                                    deviceId));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return all;
    }
}