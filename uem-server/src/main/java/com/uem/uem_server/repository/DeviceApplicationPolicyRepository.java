package com.uem.uem_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.uem_server.entity.ApplicationPolicy;
import com.uem.uem_server.entity.Device;
import com.uem.uem_server.entity.DeviceApplicationPolicy;

public interface DeviceApplicationPolicyRepository
        extends JpaRepository<DeviceApplicationPolicy, Long> {

    List<DeviceApplicationPolicy> findByDeviceAndEnabledTrue(Device device);

    boolean existsByDeviceAndApplicationPolicy(
            Device device,
            ApplicationPolicy applicationPolicy);

}