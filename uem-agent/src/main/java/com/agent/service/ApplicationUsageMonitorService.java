package com.agent.service;

import com.agent.config.Config;
import com.agent.dto.ActiveWindowInfo;
import com.agent.dto.ApplicationUsageDTO;

public class ApplicationUsageMonitorService {

    private static String lastWindow = "";

    private static String lastProcess = "";

    private static long startTime = System.currentTimeMillis();

    public static void start(
            String deviceMacId) {

        new Thread(() -> {

            while (true) {

                try {

                    ActiveWindowInfo info = ActiveWindowService
                            .getActiveWindow();

                    if (info == null) {

                        Thread.sleep(5000);

                        continue;
                    }

                    String currentWindow = info.getWindowTitle();

                    String currentProcess = info.getProcessName();

                    if (!currentWindow.equals(
                            lastWindow)) {

                        if (!lastWindow.isEmpty()) {

                            long duration = (System.currentTimeMillis()
                                    - startTime) / 1000;

                            ApplicationUsageDTO dto = new ApplicationUsageDTO();

                            dto.setDeviceMacId(
                                    deviceMacId);

                            dto.setApplicationName(
                                    lastProcess);

                            dto.setProcessName(
                                    lastProcess);

                            dto.setWindowTitle(
                                    lastWindow);

                            dto.setDurationSeconds(
                                    duration);

                            HttpClientService.post(
                                    Config.SERVER_AGENT_URL
                                            + "/agent/application-usage",
                                    dto);

                            System.out.println(
                                    "Usage Sent : "
                                            + lastProcess
                                            + " -> "
                                            + duration
                                            + " seconds");
                        }

                        lastWindow = currentWindow;

                        lastProcess = currentProcess;

                        startTime = System.currentTimeMillis();
                    }

                    Thread.sleep(5000);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

        }).start();
    }
}