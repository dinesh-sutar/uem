package com.uem.uem_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uem.uem_server.dto.ApplicationSyncDTO;
import com.uem.uem_server.dto.DevicePolicyResponseDTO;
import com.uem.uem_server.dto.ViolationReportDTO;
import com.uem.uem_server.service.ApplicationSyncService;
import com.uem.uem_server.service.DevicePolicyService;
import com.uem.uem_server.service.ViolationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agent")
public class AgentPolicyController {

    private final DevicePolicyService devicePolicyService;

    private final ViolationService violationService;

    private final ApplicationSyncService applicationSyncService;

    @GetMapping("/policies/{macId}")
    public DevicePolicyResponseDTO getPolicies(
            @PathVariable String macId) {

        return devicePolicyService
                .getPolicies(macId);
    }

    @PostMapping("/violations")
    public void reportViolation(
            @RequestBody ViolationReportDTO dto) {

        violationService.reportViolation(dto);
    }

    @PostMapping("/applications/sync")
    public void syncApplications(
            @RequestBody ApplicationSyncDTO dto) {

        applicationSyncService
                .syncApplications(dto);
    }
}
