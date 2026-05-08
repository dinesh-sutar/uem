package com.uem.uem_server.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DeviceMetricsResponseDTO {

    private String deviceId;

    private String deviceMacId;

    private String deviceName;

    private String osName;

    private String ipAddress;

    // CPU
    private double cpuUsage;

    // RAM
    private long totalMemory;
    private long availableMemory;

    // DISK
    private long totalDisk;
    private long freeDisk;

    // BATTERY
    private double batteryLevel;

    // NETWORK
    private long bytesSent;
    private long bytesReceived;

    private LocalDateTime timestamp;
}