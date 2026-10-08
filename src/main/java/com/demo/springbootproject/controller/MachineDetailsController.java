// package com.demo.springbootproject.controller;

// public class MachinenDetailController {
    
// }
package com.demo.springbootproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.demo.springbootproject.dto.MachineDetailsDTO;
import com.demo.springbootproject.service.MachineDetailsService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/machine")
public class MachineDetailsController {

    private final MachineDetailsService machineDetailsService;

    public MachineDetailsController(
            MachineDetailsService machineDetailsService) {
        this.machineDetailsService = machineDetailsService;
    }

    @GetMapping("/details")
    public MachineDetailsDTO getMachineDetails() {
        return machineDetailsService.getMachineDetails();
    }
}