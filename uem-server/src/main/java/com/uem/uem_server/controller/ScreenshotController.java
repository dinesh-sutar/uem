package com.uem.uem_server.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.uem.uem_server.dto.ScreenshotRequest;
import com.uem.uem_server.entity.DeviceScreenshot;
import com.uem.uem_server.repository.DeviceScreenshotRepository;
import com.uem.uem_server.service.ScreenshotStorageService;

import java.util.List;

@RestController
@RequestMapping("/api/screenshots")
@CrossOrigin("*")
public class ScreenshotController {

    @Autowired
    private ScreenshotStorageService service;

    @Autowired
    private DeviceScreenshotRepository repository;

    @PostMapping
    public String upload(
            @RequestBody ScreenshotRequest request)
            throws Exception {

        service.save(request);

        return "Uploaded";
    }

    @GetMapping("/{deviceMacId}")
    public List<DeviceScreenshot> getScreenshots(
            @PathVariable String deviceMacId) {

        return repository
                .findTop100ByDeviceMacIdOrderByCapturedAtDesc(
                        deviceMacId);
    }
}