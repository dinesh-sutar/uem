package com.agent.service;

import java.util.ArrayList;

import com.agent.dto.ApplicationSyncDTO;

public class ApplicationSyncService {

    public static void sync(String macId) {

        try {

            ApplicationSyncDTO dto = new ApplicationSyncDTO();

            dto.setDeviceMacId(macId);

            dto.setApplications(
                    new ArrayList<>(
                            InstalledApplicationService
                                    .getInstalledApplications()));

            HttpClientService.post(
                    "http://192.168.1.13:8081/agent/applications/sync",
                    dto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}