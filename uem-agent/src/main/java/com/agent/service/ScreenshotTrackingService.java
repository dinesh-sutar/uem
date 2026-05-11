package com.agent.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenshotTrackingService {

    public static void start(String deviceId) {

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(() -> {

            try {

                String image = ScreenshotService.captureBase64();

                if (image == null) {
                    return;
                }

                Map<String, Object> request = new HashMap<>();

                request.put("deviceMacId", deviceId);

                request.put("timestamp",
                        System.currentTimeMillis());

                request.put("image", image);

                HttpClientService.post(
                        "http://172.24.224.1:8081/api/screenshots",
                        request);

                System.out.println(
                        "Screenshot uploaded");

            } catch (Exception e) {

                e.printStackTrace();
            }

        }, 0, 1, TimeUnit.MICROSECONDS);
    }
}