package com.uem.uem_server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

import com.uem.uem_server.constant.OSType;

@Entity
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String deviceId;

    @Column(unique = true)
    private String deviceMacId;

    private String deviceName;

    private String osName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private OSType osType;

    private String ipAddress;

    private String status;

    private LocalDateTime lastSeen;
}