package com.uem.uem_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uem.uem_server.dto.ScreenshotRequest;
import com.uem.uem_server.entity.DeviceScreenshot;
import com.uem.uem_server.repository.DeviceScreenshotRepository;

import java.io.File;
import java.io.FileOutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Base64;

@Service
public class ScreenshotStorageService {

    private static final String ROOT_DIR = "screenshots";

    @Autowired
    private DeviceScreenshotRepository repository;

    public void save(ScreenshotRequest request)
            throws Exception {

        byte[] imageBytes = Base64.getDecoder()
                .decode(request.getImage());

        String dateFolder = LocalDate.now().toString();

        Path directory = Paths.get(
                ROOT_DIR,
                request.getDeviceMacId(),
                dateFolder);

        Files.createDirectories(directory);

        String fileName = request.getTimestamp() + ".jpg";

        File file = directory.resolve(fileName).toFile();

        try (FileOutputStream fos = new FileOutputStream(file)) {

            fos.write(imageBytes);
        }

        DeviceScreenshot screenshot = new DeviceScreenshot();

        screenshot.setDeviceMacId(
                request.getDeviceMacId());

        screenshot.setFilePath(
                file.getAbsolutePath());

        screenshot.setFileSize(
                file.length());

        screenshot.setCapturedAt(
                LocalDateTime.now());

        repository.save(screenshot);
    }
}
