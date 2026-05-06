package com.agent.service;

import com.agent.service.intraface.BrowserHistoryStrategy;
import com.agent.service.intraface.impl.browser.BraveWindowsStrategy;
import com.agent.service.intraface.impl.browser.ChromeWindowsStrategy;
import com.agent.service.intraface.impl.browser.EdgeWindowsStrategy;
import com.agent.util.OSUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BrowserHistoryCollector {

    public static List<Map<String, Object>> collectAll(String deviceId) {

        List<Map<String, Object>> all = new ArrayList<>();

        try {

            if (OSUtil.isWindows()) {

                List<BrowserHistoryStrategy> strategies = List.of(

                        new ChromeWindowsStrategy(),
                        new EdgeWindowsStrategy(),
                        new BraveWindowsStrategy());

                for (BrowserHistoryStrategy s : strategies) {

                    all.addAll(
                            s.collect(deviceId));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return all;
    }
}