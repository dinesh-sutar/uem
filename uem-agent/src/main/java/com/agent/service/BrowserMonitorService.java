package com.agent.service;

import com.agent.config.Config;

import java.util.List;
import java.util.Map;

public class BrowserMonitorService {

    public static void start(String deviceId) {

        new Thread(() -> {

            while (true) {

                try {

                    List<Map<String, Object>> history = BrowserHistoryCollector
                            .collectAll(deviceId);

                    if (!history.isEmpty()) {

                        HttpClientService.post(
                                Config.SERVER_URL
                                        + "/history",
                                history);

                        System.out.println(
                                "Uploaded History: "
                                        + history.size());
                    }

                    Thread.sleep(60000);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }
}