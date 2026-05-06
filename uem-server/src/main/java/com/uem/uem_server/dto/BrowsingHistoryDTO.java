package com.uem.uem_server.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrowsingHistoryDTO {

    private String deviceMacId;

    private String browser;

    private String profileName;

    private String url;

    private String domain;

    private String title;

    private LocalDateTime visitedAt;
}