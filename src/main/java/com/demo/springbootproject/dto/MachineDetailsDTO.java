package com.demo.springbootproject.dto;

import lombok.Data;

@Data
public class MachineDetailsDTO {

    private String osName;
    private String osVersion;
    private String architecture;
    private int processors;

    private long totalRam;
    private long freeRam;

    private long totalStorage;
    private long freeStorage;

    private String javaVersion;
}
