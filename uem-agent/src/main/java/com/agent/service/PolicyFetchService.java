package com.agent.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

import com.agent.config.Config;
import com.agent.dto.BlockedApplicationDTO;
import com.agent.dto.DevicePolicyResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PolicyFetchService {

    public static void start(String macId) {

        new Thread(() -> {

            while (true) {

                try {

                    URL url = new URL(
                            Config.SERVER_AGENT_URL
                                    + "/agent/policies/"
                                    + macId);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");

                    ObjectMapper mapper = new ObjectMapper();

                    DevicePolicyResponseDTO dto = mapper.readValue(
                            con.getInputStream(),
                            DevicePolicyResponseDTO.class);

                    PolicyCache.usbBlocked = dto.getUsbBlocked();

                    Set<String> blocked = dto.getBlockedApplications()
                            .stream()
                            .map(
                                    BlockedApplicationDTO::getProcessName)
                            .collect(Collectors.toSet());

                    PolicyCache.blockedProcesses = blocked;

                    System.out.println(
                            "Policies Updated");

                    Thread.sleep(10000);

                } catch (Exception e) {

                    e.printStackTrace();

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }).start();
    }
}