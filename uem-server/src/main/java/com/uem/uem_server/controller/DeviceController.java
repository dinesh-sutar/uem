package com.uem.uem_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.uem.uem_server.dto.BrowsingHistoryDTO;
import com.uem.uem_server.dto.DeviceMetricsDTO;
import com.uem.uem_server.dto.DeviceRegisterRequest;
import com.uem.uem_server.dto.HeartbeatMetricsDTO;
import com.uem.uem_server.entity.BrowsingHistory;
import com.uem.uem_server.entity.Device;
import com.uem.uem_server.entity.DeviceCommand;
import com.uem.uem_server.entity.DeviceMetrics;
import com.uem.uem_server.entity.DevicePolicy;
import com.uem.uem_server.repository.BrowsingHistoryRepository;
import com.uem.uem_server.repository.DeviceCommandRepository;
import com.uem.uem_server.repository.DeviceMetricsRepository;
import com.uem.uem_server.repository.DevicePolicyRepository;
import com.uem.uem_server.repository.DeviceRepository;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceRepository deviceRepository;
    private final DeviceMetricsRepository deviceMetricsRepository;
    private final DeviceCommandRepository deviceCommandRepository;
    private final DevicePolicyRepository devicePolicyRepository;
    private final BrowsingHistoryRepository browsingHistoryRepository;

    @PostMapping("/register")
    public String registerDevice(
            @RequestBody DeviceRegisterRequest request) {

        Device device = deviceRepository
                .findByDeviceMacId(request.getDeviceMacId())
                .orElse(new Device());

        device.setDeviceMacId(request.getDeviceMacId());
        device.setDeviceName(request.getDeviceName());
        device.setOsName(request.getOsName());
        device.setIpAddress(request.getIpAddress());
        device.setStatus("ONLINE");
        device.setLastSeen(LocalDateTime.now());

        deviceRepository.save(device);

        return "Device Registered";
    }

    @PostMapping("/heartbeat")
    public String heartbeat(@RequestBody HeartbeatMetricsDTO dto) {

        Device device = deviceRepository
                .findByDeviceMacId(dto.getDeviceMacId())
                .orElseThrow();

        // 1. Update heartbeat
        device.setLastSeen(LocalDateTime.now());
        device.setStatus("ONLINE");
        deviceRepository.save(device);

        // 2. Save metrics
        DeviceMetrics metrics = new DeviceMetrics();

        metrics.setDevice(device);

        metrics.setCpuUsage(dto.getCpuUsage());

        metrics.setTotalMemory(dto.getTotalMemory());
        metrics.setAvailableMemory(dto.getAvailableMemory());

        metrics.setTotalDisk(dto.getTotalDisk());
        metrics.setFreeDisk(dto.getFreeDisk());

        metrics.setBatteryLevel(dto.getBatteryLevel());

        metrics.setBytesSent(dto.getBytesSent());
        metrics.setBytesReceived(dto.getBytesReceived());

        metrics.setTimestamp(LocalDateTime.now());

        deviceMetricsRepository.save(metrics);

        return "Heartbeat + Metrics Saved";
    }

    @PostMapping("/metrics")
    public String save(@RequestBody DeviceMetricsDTO dto) {

        Device device = deviceRepository
                .findByDeviceMacId(dto.getDeviceMacId()).orElseThrow();

        DeviceMetrics metrics = new DeviceMetrics();

        metrics.setDevice(device);

        metrics.setCpuUsage(dto.getCpuUsage());

        metrics.setTotalMemory(dto.getTotalMemory());
        metrics.setAvailableMemory(dto.getAvailableMemory());

        metrics.setTotalDisk(dto.getTotalDisk());
        metrics.setFreeDisk(dto.getFreeDisk());

        metrics.setBatteryLevel(dto.getBatteryLevel());

        metrics.setBytesSent(dto.getBytesSent());
        metrics.setBytesReceived(dto.getBytesReceived());

        metrics.setTimestamp(LocalDateTime.now());

        deviceMetricsRepository.save(metrics);

        return "saved";
    }

    @GetMapping
    public Object getAllDevices() {
        return deviceRepository.findAll();
    }

    @PostMapping("/command")
    public String sendCommand(@RequestBody DeviceCommand cmd) {

        cmd.setStatus("PENDING");
        cmd.setCreatedAt(LocalDateTime.now());

        deviceCommandRepository.save(cmd);

        return "Command queued";
    }

    @GetMapping("/commands/{macId}")
    public List<DeviceCommand> getCommands(
            @PathVariable String macId) {

        return deviceCommandRepository.findByDeviceMacIdAndStatus(
                macId, "PENDING");
    }

    @PostMapping("/command/update")
    public void updateStatus(@RequestBody DeviceCommand cmd) {

        DeviceCommand existing = deviceCommandRepository.findById(cmd.getId()).orElseThrow();

        existing.setStatus(cmd.getStatus());

        deviceCommandRepository.save(existing);
    }

    @GetMapping("/policy/{macId}")
    public List<String> getBlockedSites(@PathVariable String macId) {

        return devicePolicyRepository
                .findByDeviceMacIdAndBlockedTrue(macId)
                .stream()
                .map(DevicePolicy::getWebsite)
                .toList();
    }

    @PostMapping("/history")
    public String saveHistory(
            @RequestBody List<BrowsingHistoryDTO> list) {

        List<BrowsingHistory> entities = list.stream().map(dto -> {

            BrowsingHistory h = new BrowsingHistory();

            h.setDeviceMacId(dto.getDeviceMacId());
            h.setBrowser(dto.getBrowser());
            h.setProfileName(dto.getProfileName());
            h.setUrl(dto.getUrl());
            h.setUrlHash(md5(dto.getUrl()));
            h.setDomain(dto.getDomain());
            h.setTitle(dto.getTitle());
            h.setVisitedAt(dto.getVisitedAt());

            return h;

        }).toList();

        browsingHistoryRepository.saveAll(entities);

        return "History Saved";
    }

    private String md5(String input) {

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] digest = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b : digest) {
                sb.append(
                        String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}