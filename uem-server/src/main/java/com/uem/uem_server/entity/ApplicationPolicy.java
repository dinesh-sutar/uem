package com.uem.uem_server.entity;

import com.uem.uem_server.constant.OSType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "application_policies")
@Data
public class ApplicationPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String appName;

    private String processName;

    @Enumerated(EnumType.STRING)
    private OSType osType;

    private Boolean enabled = true;
}