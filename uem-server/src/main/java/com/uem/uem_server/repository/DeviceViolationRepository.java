package com.uem.uem_server.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.uem_server.entity.Device;
import com.uem.uem_server.entity.DeviceViolation;

public interface DeviceViolationRepository
        extends JpaRepository<DeviceViolation, Long> {

    List<DeviceViolation> findByDeviceOrderByViolationTimeDesc(
            Device device);

    List<DeviceViolation> findByViolationType(String violationType);

    List<DeviceViolation> findByViolationTimeBetween(
            LocalDateTime start,
            LocalDateTime end);
}