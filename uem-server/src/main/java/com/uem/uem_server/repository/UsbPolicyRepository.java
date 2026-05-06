package com.uem.uem_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.uem_server.entity.Device;
import com.uem.uem_server.entity.UsbPolicy;

public interface UsbPolicyRepository
        extends JpaRepository<UsbPolicy, Long> {

    Optional<UsbPolicy> findByDevice(Device device);
}
