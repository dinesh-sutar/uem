package com.agent.dto;

public class ApplicationUsageDTO {

    private String deviceMacId;

    private String applicationName;

    private String processName;

    private String windowTitle;

    private Long durationSeconds;

    public String getDeviceMacId() {
        return deviceMacId;
    }

    public void setDeviceMacId(
            String deviceMacId) {

        this.deviceMacId = deviceMacId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(
            String applicationName) {

        this.applicationName = applicationName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(
            String processName) {

        this.processName = processName;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(
            String windowTitle) {

        this.windowTitle = windowTitle;
    }

    public Long getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(
            Long durationSeconds) {

        this.durationSeconds = durationSeconds;
    }
}