package com.agent.util;

import com.agent.service.SyncStateService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class ChromiumHistoryUtil {

    public static List<Map<String, Object>> readHistory(
            String browser,
            String historyPath,
            String profileName,
            String deviceId) {

        List<Map<String, Object>> result = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            File original = new File(historyPath);

            if (!original.exists()) {
                return result;
            }

            File temp = File.createTempFile("history", ".db");

            Files.copy(
                    original.toPath(),
                    temp.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            Class.forName("org.sqlite.JDBC");

            con = DriverManager.getConnection(
                    "jdbc:sqlite:" +
                            temp.getAbsolutePath());

            long lastSync = SyncStateService.getLastSync(
                    browser,
                    profileName);

            stmt = con.prepareStatement(
                    "SELECT url, title, last_visit_time " +
                            "FROM urls " +
                            "WHERE last_visit_time > ? " +
                            "ORDER BY last_visit_time ASC " +
                            "LIMIT 500");

            stmt.setLong(1, lastSync);

            rs = stmt.executeQuery();

            long latestTime = lastSync;

            while (rs.next()) {

                String url = rs.getString("url");

                long visitTime = rs.getLong("last_visit_time");

                if (visitTime > latestTime) {
                    latestTime = visitTime;
                }

                Map<String, Object> row = new HashMap<>();

                row.put("deviceMacId", deviceId);

                row.put("browser", browser);

                row.put("profileName",
                        profileName);

                row.put("url", url);

                row.put("title",
                        rs.getString("title"));

                row.put("domain",
                        extractDomain(url));

                row.put("visitedAt",
                        chromeTimeToDateTime(
                                visitTime));

                result.add(row);
            }

            SyncStateService.saveLastSync(
                    browser,
                    profileName,
                    latestTime);

            rs.close();
            stmt.close();
            con.close();

            temp.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String extractDomain(
            String url) {

        try {

            return new java.net.URL(url)
                    .getHost();

        } catch (Exception e) {

            return "";
        }
    }

    private static LocalDateTime chromeTimeToDateTime(
            long chromeTime) {

        long seconds = (chromeTime / 1000000L)
                - 11644473600L;

        return LocalDateTime.ofEpochSecond(
                seconds,
                0,
                ZoneOffset.UTC);
    }
}