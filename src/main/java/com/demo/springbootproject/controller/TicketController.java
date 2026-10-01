package com.demo.springbootproject.controller;
// package com.demo.springbootproject.service.TicketService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.demo.springbootproject.dto.TicketRequestDTO;
import com.demo.springbootproject.dto.TicketResponseDTO;
import com.demo.springbootproject.service.TicketService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketResponseDTO> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PostMapping
    public TicketResponseDTO createTicket(
            @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {

        return ticketService.createTicket(ticketRequestDTO);
    }

    @GetMapping("/{id}")
    public TicketResponseDTO getTicketById(
            @PathVariable Long id) {

        return ticketService.getTicketById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public TicketResponseDTO updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {

        return ticketService.updateTicket(id, ticketRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {

        ticketService.deleteTicket(id);
    }






}
        

 
