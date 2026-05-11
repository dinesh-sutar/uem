package com.uem.uem_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.uem_server.entity.DeviceScreenshot;

import java.util.List;

public interface DeviceScreenshotRepository
        extends JpaRepository<DeviceScreenshot, Long> {

    List<DeviceScreenshot> findTop100ByDeviceMacIdOrderByCapturedAtDesc(
            String deviceMacId);
}