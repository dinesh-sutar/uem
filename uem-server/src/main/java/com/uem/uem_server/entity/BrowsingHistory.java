package com.uem.uem_server.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(indexes = {
        @Index(name = "idx_device_time", columnList = "deviceMacId, visitedAt")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "deviceMacId",
                "browser",
                "profileName",
                "urlHash",
                "visitedAt"
        })
})
public class BrowsingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceMacId;

    private String browser;

    private String profileName;

    @Column(columnDefinition = "TEXT")
    private String url;

    private String urlHash;

    private String domain;

    @Column(columnDefinition = "TEXT")
    private String title;

    private LocalDateTime visitedAt;
}