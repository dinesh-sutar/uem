package com.agent.service;

import java.io.File;
import java.nio.file.Files;

public class SyncStateService {

    private static final String DIR = "browser-sync";

    public static long getLastSync(
            String browser,
            String profile) {

        try {

            File dir = new File(DIR);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(
                    dir,
                    browser + "_" + profile + ".txt");

            if (!file.exists()) {
                return 0;
            }

            String value = Files.readString(file.toPath());

            return Long.parseLong(value);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void saveLastSync(
            String browser,
            String profile,
            long timestamp) {

        try {

            File dir = new File(DIR);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(
                    dir,
                    browser + "_" + profile + ".txt");

            Files.writeString(
                    file.toPath(),
                    String.valueOf(timestamp));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}