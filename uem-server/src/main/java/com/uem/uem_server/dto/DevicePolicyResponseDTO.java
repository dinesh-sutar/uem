package com.uem.uem_server.dto;

import java.util.List;

import lombok.Data;

@Data
public class DevicePolicyResponseDTO {

    private Boolean usbBlocked;

    private List<BlockedApplicationDTO> blockedApplications;
}
