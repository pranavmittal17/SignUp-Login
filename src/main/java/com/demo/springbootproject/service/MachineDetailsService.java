package com.demo.springbootproject.service;

import java.io.File;

import org.springframework.stereotype.Service;

import com.demo.springbootproject.dto.MachineDetailsDTO;

@Service
public class MachineDetailsService {

    public MachineDetailsDTO getMachineDetails() {

        MachineDetailsDTO details = new MachineDetailsDTO();

        Runtime runtime = Runtime.getRuntime();

        // OS
        details.setOsName(System.getProperty("os.name"));
        details.setOsVersion(System.getProperty("os.version"));
        details.setArchitecture(System.getProperty("os.arch"));

        // CPU
        details.setProcessors(runtime.availableProcessors());

        // RAM
        details.setTotalRam(toGB(runtime.totalMemory()));
        details.setFreeRam(toGB(runtime.freeMemory()));

        // Storage
        File disk = new File("C:");

        details.setTotalStorage(toGB(disk.getTotalSpace()));
        details.setFreeStorage(toGB(disk.getFreeSpace()));

        // Java
        details.setJavaVersion(System.getProperty("java.version"));

        return details;
    }

    private long toGB(long bytes) {
        return bytes / (1024 * 1024 * 1024);
    }
}