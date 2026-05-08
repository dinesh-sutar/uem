package com.uem.uem_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.uem_server.entity.Device;
import com.uem.uem_server.entity.DeviceMetrics;

public interface DeviceMetricsRepository extends JpaRepository<DeviceMetrics, Long> {

    Optional<DeviceMetrics> findTopByDeviceOrderByIdDesc(Device device);

}
