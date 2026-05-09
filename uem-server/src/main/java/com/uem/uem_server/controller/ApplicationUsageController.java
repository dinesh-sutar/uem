package com.uem.uem_server.controller;

import org.springframework.web.bind.annotation.*;

import com.uem.uem_server.dto.ApplicationUsageDTO;
import com.uem.uem_server.service.ApplicationUsageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class ApplicationUsageController {

    private final ApplicationUsageService applicationUsageService;

    @PostMapping("/application-usage")
    public void saveUsage(
            @RequestBody ApplicationUsageDTO dto) {

        applicationUsageService
                .saveUsage(dto);
    }
}