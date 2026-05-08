package com.agent.service;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.agent.dto.InstalledApplicationDTO;

public class InstalledApplicationService {

    public static Set<InstalledApplicationDTO> getInstalledApplications() {

        Set<InstalledApplicationDTO> apps = new HashSet<>();

        ProcessHandle.allProcesses()
                .forEach(ph -> {

                    ph.info()
                            .command()
                            .ifPresent(cmd -> {

                                try {

                                    String processName = Paths.get(cmd)
                                            .getFileName()
                                            .toString();

                                    InstalledApplicationDTO dto = new InstalledApplicationDTO();

                                    dto.setAppName(
                                            processName);

                                    dto.setProcessName(
                                            processName);

                                    apps.add(dto);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                });

        return apps;
    }
}