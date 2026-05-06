package com.uem.uem_server.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.uem.uem_server.dto.ViolationReportDTO;
import com.uem.uem_server.entity.Device;
import com.uem.uem_server.entity.DeviceViolation;
import com.uem.uem_server.repository.DeviceRepository;
import com.uem.uem_server.repository.DeviceViolationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViolationService {

    private final DeviceRepository deviceRepository;

    private final DeviceViolationRepository violationRepository;

    public void reportViolation(
            ViolationReportDTO dto) {

        Device device = deviceRepository
                .findByDeviceMacId(dto.getMacId())
                .orElseThrow();

        DeviceViolation violation = new DeviceViolation();

        violation.setDevice(device);
        violation.setViolationType(
                dto.getViolationType());
        violation.setDetails(dto.getDetails());
        violation.setViolationTime(
                LocalDateTime.now());

        violationRepository.save(violation);
    }
}
