package com.uem.uem_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uem.uem_server.dto.response.DeviceMetricsResponseDTO;
import com.uem.uem_server.service.DeviceMetricsService;

@RestController
@RequestMapping("/device-metrics")
public class DeviceMetricsController {

    private final DeviceMetricsService deviceMetricsService;

    public DeviceMetricsController(DeviceMetricsService deviceMetricsService) {
        this.deviceMetricsService = deviceMetricsService;
    }

    @GetMapping("/latest/{macId}")
    public DeviceMetricsResponseDTO getLatestMetrics(
            @PathVariable String macId) {

        return deviceMetricsService.getLatestMetrics(macId);
    }
}