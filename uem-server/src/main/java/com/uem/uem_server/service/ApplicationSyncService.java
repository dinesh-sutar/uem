package com.uem.uem_server.service;

import org.springframework.stereotype.Service;

import com.uem.uem_server.dto.ApplicationSyncDTO;
import com.uem.uem_server.dto.InstalledApplicationDTO;
import com.uem.uem_server.entity.ApplicationPolicy;
import com.uem.uem_server.entity.Device;
import com.uem.uem_server.entity.DeviceApplicationPolicy;
import com.uem.uem_server.repository.ApplicationPolicyRepository;
import com.uem.uem_server.repository.DeviceApplicationPolicyRepository;
import com.uem.uem_server.repository.DeviceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationSyncService {

    private final DeviceRepository deviceRepository;

    private final ApplicationPolicyRepository applicationPolicyRepository;

    private final DeviceApplicationPolicyRepository deviceApplicationPolicyRepository;

    public void syncApplications(
            ApplicationSyncDTO dto) {

        Device device = deviceRepository
                .findByDeviceMacId(
                        dto.getDeviceMacId())
                .orElseThrow();

        for (InstalledApplicationDTO app : dto.getApplications()) {

            ApplicationPolicy application = applicationPolicyRepository
                    .findByProcessNameAndOsType(
                            app.getProcessName(),
                            device.getOsType())
                    .orElseGet(() -> {

                        ApplicationPolicy ap = new ApplicationPolicy();

                        ap.setAppName(
                                app.getAppName());

                        ap.setProcessName(
                                app.getProcessName());

                        ap.setOsType(
                                device.getOsType());

                        ap.setEnabled(true);

                        return applicationPolicyRepository
                                .save(ap);
                    });

            boolean exists = deviceApplicationPolicyRepository
                    .existsByDeviceAndApplicationPolicy(
                            device,
                            application);

            if (!exists) {

                DeviceApplicationPolicy mapping = new DeviceApplicationPolicy();

                mapping.setDevice(device);

                mapping.setApplicationPolicy(
                        application);

                // DEFAULT NOT BLOCKED
                mapping.setEnabled(false);

                deviceApplicationPolicyRepository
                        .save(mapping);
            }
        }
    }
}