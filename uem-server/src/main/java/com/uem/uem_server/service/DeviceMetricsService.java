package com.uem.uem_server.service;

import org.springframework.stereotype.Service;

import com.uem.uem_server.dto.response.DeviceMetricsResponseDTO;
import com.uem.uem_server.entity.Device;
import com.uem.uem_server.entity.DeviceMetrics;
import com.uem.uem_server.repository.DeviceMetricsRepository;
import com.uem.uem_server.repository.DeviceRepository;

@Service
public class DeviceMetricsService {

    private final DeviceRepository deviceRepository;
    private final DeviceMetricsRepository deviceMetricsRepository;

    public DeviceMetricsService(DeviceRepository deviceRepository,
            DeviceMetricsRepository deviceMetricsRepository) {

        this.deviceRepository = deviceRepository;
        this.deviceMetricsRepository = deviceMetricsRepository;
    }

    public DeviceMetricsResponseDTO getLatestMetrics(String macId) {

        Device device = deviceRepository.findByDeviceMacId(macId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        DeviceMetrics metrics = deviceMetricsRepository
                .findTopByDeviceOrderByIdDesc(device)
                .orElseThrow(() -> new RuntimeException("Metrics not found"));

        DeviceMetricsResponseDTO dto = new DeviceMetricsResponseDTO();

        dto.setDeviceId(device.getDeviceId());
        dto.setDeviceMacId(device.getDeviceMacId());
        dto.setDeviceName(device.getDeviceName());
        dto.setOsName(device.getOsName());
        dto.setIpAddress(device.getIpAddress());

        dto.setCpuUsage(metrics.getCpuUsage());

        dto.setTotalMemory(metrics.getTotalMemory());
        dto.setAvailableMemory(metrics.getAvailableMemory());

        dto.setTotalDisk(metrics.getTotalDisk());
        dto.setFreeDisk(metrics.getFreeDisk());

        dto.setBatteryLevel(metrics.getBatteryLevel());

        dto.setBytesSent(metrics.getBytesSent());
        dto.setBytesReceived(metrics.getBytesReceived());

        dto.setTimestamp(metrics.getTimestamp());

        return dto;
    }
}