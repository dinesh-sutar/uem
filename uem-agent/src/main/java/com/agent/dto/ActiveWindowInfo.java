package com.agent.dto;

public class ActiveWindowInfo {

    private String windowTitle;

    private String processName;

    public ActiveWindowInfo(
            String windowTitle,
            String processName) {

        this.windowTitle = windowTitle;
        this.processName = processName;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public String getProcessName() {
        return processName;
    }
}