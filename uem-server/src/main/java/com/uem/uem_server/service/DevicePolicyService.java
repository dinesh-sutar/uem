package com.uem.uem_server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uem.uem_server.dto.BlockedApplicationDTO;
import com.uem.uem_server.dto.DevicePolicyResponseDTO;
import com.uem.uem_server.entity.ApplicationPolicy;
import com.uem.uem_server.entity.Device;
import com.uem.uem_server.entity.UsbPolicy;
import com.uem.uem_server.repository.DeviceApplicationPolicyRepository;
import com.uem.uem_server.repository.DeviceRepository;
import com.uem.uem_server.repository.UsbPolicyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevicePolicyService {

    private final DeviceRepository deviceRepository;

    private final DeviceApplicationPolicyRepository deviceApplicationPolicyRepository;

    private final UsbPolicyRepository usbPolicyRepository;

    public DevicePolicyResponseDTO getPolicies(String macId) {

        Device device = deviceRepository
                .findByDeviceMacId(macId)
                .orElseThrow();

        DevicePolicyResponseDTO dto = new DevicePolicyResponseDTO();

        // USB POLICY
        UsbPolicy usbPolicy = usbPolicyRepository
                .findByDevice(device)
                .orElse(null);

        dto.setUsbBlocked(
                usbPolicy != null &&
                        usbPolicy.getUsbBlocked());

        // APPLICATIONS
        List<BlockedApplicationDTO> apps = deviceApplicationPolicyRepository
                .findByDeviceAndEnabledTrue(device)
                .stream()
                .map(mapping -> {

                    ApplicationPolicy ap = mapping.getApplicationPolicy();

                    BlockedApplicationDTO app = new BlockedApplicationDTO();

                    app.setAppName(ap.getAppName());
                    app.setProcessName(ap.getProcessName());

                    return app;

                }).toList();

        dto.setBlockedApplications(apps);

        return dto;
    }
}