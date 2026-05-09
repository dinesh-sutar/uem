package com.agent.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommandService {

    public static void start(String macId) {

        new Thread(() -> {

            while (true) {

                try {

                    String url = "http://192.168.1.9:8081/devices/commands/" + macId;

                    List<Map> commands = HttpClientService.get(url);

                    for (Map cmd : commands) {

                        String command = (String) cmd.get("command");

                        Long id = Long.valueOf(cmd.get("id").toString());

                        execute(command);

                        updateStatus(id, "EXECUTED");
                    }

                    Thread.sleep(15000);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }

    private static void execute(String cmd) throws Exception {

        String os = System.getProperty("os.name").toLowerCase();

        switch (cmd) {

            case "RESTART":
                if (os.contains("win")) {
                    Runtime.getRuntime().exec("shutdown -r -t 0");
                } else if (os.contains("linux") || os.contains("mac")) {
                    Runtime.getRuntime().exec("shutdown -r now");
                }
                break;

            case "SHUTDOWN":
                if (os.contains("win")) {
                    Runtime.getRuntime().exec("shutdown -s -t 0");
                } else if (os.contains("linux") || os.contains("mac")) {
                    Runtime.getRuntime().exec("shutdown -h now");
                }
                break;

            case "SLEEP":
                if (os.contains("win")) {
                    Runtime.getRuntime().exec("rundll32.exe powrprof.dll,SetSuspendState 0,1,0");
                } else if (os.contains("linux")) {
                    Runtime.getRuntime().exec("systemctl suspend");
                } else if (os.contains("mac")) {
                    Runtime.getRuntime().exec("pmset sleepnow");
                }
                break;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<Map> get(String url) {

        try {

            ObjectMapper mapper = new ObjectMapper();

            URL u = new URL(url);

            HttpURLConnection con = (HttpURLConnection) u.openConnection();

            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            System.out.println("GET " + url + " -> " + responseCode);

            if (responseCode == 200) {

                return mapper.readValue(
                        con.getInputStream(),
                        List.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return List.of();
    }

    private static void updateStatus(Long id, String status) {

        try {

            Map<String, Object> body = new HashMap<>();

            body.put("id", id);
            body.put("status", status);

            HttpClientService.post(
                    "http://192.168.1.9:8081/devices/command/update",
                    body);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}