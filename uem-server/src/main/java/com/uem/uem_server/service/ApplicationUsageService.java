package com.uem.uem_server.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.uem.uem_server.dto.ApplicationUsageDTO;
import com.uem.uem_server.entity.ApplicationUsage;
import com.uem.uem_server.entity.Device;
import com.uem.uem_server.repository.ApplicationUsageRepository;
import com.uem.uem_server.repository.DeviceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationUsageService {

    private final ApplicationUsageRepository applicationUsageRepository;

    private final DeviceRepository deviceRepository;

    public void saveUsage(
            ApplicationUsageDTO dto) {

        Device device = deviceRepository
                .findByDeviceMacId(
                        dto.getDeviceMacId())
                .orElseThrow();

        ApplicationUsage usage = new ApplicationUsage();

        usage.setDevice(device);

        usage.setApplicationName(
                dto.getApplicationName());

        usage.setProcessName(
                dto.getProcessName());

        usage.setWindowTitle(
                dto.getWindowTitle());

        usage.setDurationSeconds(
                dto.getDurationSeconds());

        usage.setStartTime(
                LocalDateTime.now()
                        .minusSeconds(
                                dto.getDurationSeconds()));

        usage.setEndTime(
                LocalDateTime.now());

        applicationUsageRepository
                .save(usage);
    }
}