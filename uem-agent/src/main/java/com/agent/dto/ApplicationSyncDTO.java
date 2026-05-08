package com.agent.dto;

import java.util.List;

public class ApplicationSyncDTO {

    private String deviceMacId;

    private List<InstalledApplicationDTO> applications;

    public String getDeviceMacId() {
        return deviceMacId;
    }

    public void setDeviceMacId(String deviceMacId) {
        this.deviceMacId = deviceMacId;
    }

    public List<InstalledApplicationDTO> getApplications() {
        return applications;
    }

    public void setApplications(List<InstalledApplicationDTO> applications) {
        this.applications = applications;
    }

}