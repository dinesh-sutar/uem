package com.uem.uem_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "device_application_policy")
@Data
public class DeviceApplicationPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Device device;

    @ManyToOne
    private ApplicationPolicy applicationPolicy;

    private Boolean enabled = true;
}
