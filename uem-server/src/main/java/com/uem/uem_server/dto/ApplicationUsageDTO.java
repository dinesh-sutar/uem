package com.uem.uem_server.dto;

import lombok.Data;

@Data
public class ApplicationUsageDTO {

    private String deviceMacId;

    private String applicationName;

    private String processName;

    private String windowTitle;

    private Long durationSeconds;
}