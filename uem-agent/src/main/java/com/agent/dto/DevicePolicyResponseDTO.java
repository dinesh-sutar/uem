package com.agent.dto;

import java.util.List;

public class DevicePolicyResponseDTO {

    private Boolean usbBlocked;

    private List<BlockedApplicationDTO> blockedApplications;

    public Boolean getUsbBlocked() {
        return usbBlocked;
    }

    public void setUsbBlocked(Boolean usbBlocked) {
        this.usbBlocked = usbBlocked;
    }

    public List<BlockedApplicationDTO> getBlockedApplications() {
        return blockedApplications;
    }

    public void setBlockedApplications(
            List<BlockedApplicationDTO> blockedApplications) {

        this.blockedApplications = blockedApplications;
    }
}