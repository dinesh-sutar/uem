package com.uem.uem_server.dto;

import java.util.List;

import lombok.Data;

@Data
public class ApplicationSyncDTO {

    private String deviceMacId;

    private List<InstalledApplicationDTO> applications;
}
