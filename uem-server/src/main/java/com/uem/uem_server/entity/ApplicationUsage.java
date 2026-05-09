package com.uem.uem_server.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "application_usage")
@Data
public class ApplicationUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Device device;

    private String applicationName;

    private String processName;

    private String windowTitle;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long durationSeconds;
}