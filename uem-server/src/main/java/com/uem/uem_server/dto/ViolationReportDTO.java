package com.uem.uem_server.dto;

import lombok.Data;

@Data
public class ViolationReportDTO {

    private String macId;

    private String violationType;

    private String details;
}
