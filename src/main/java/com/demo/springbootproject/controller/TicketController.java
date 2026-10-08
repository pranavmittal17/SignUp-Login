package com.demo.springbootproject.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.demo.springbootproject.dto.TicketRequestDTO;
import com.demo.springbootproject.dto.TicketResponseDTO;
import com.demo.springbootproject.service.TicketService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@CrossOrigin(origins="http://localhost:4200")
@RestController
@Slf4j 

@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public Page<TicketResponseDTO> findAllTickets(@PageableDefault(size = 10) Pageable pageable) {
        log.debug(
        "Received request to fetch tickets. page={}, size={}",
        pageable.getPageNumber(),
        pageable.getPageSize()
        );
        return ticketService.findAllTickets(pageable); //based on latest updates show the tickets, if not latest then use the create option 
    
    }
    @GetMapping("/my-tickets")
    public Page<TicketResponseDTO> getMyTickets(Pageable pageable) {

        return ticketService.getMyTickets(pageable);
    }

    @PostMapping
    public TicketResponseDTO createTicket(
            @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {
    log.debug(
    "Received request to create ticket. title={}, priority={}, assignedTo={}",
    ticketRequestDTO.getTitle(),
    ticketRequestDTO.getTicketPriority(),
    ticketRequestDTO.getAssignedTo()
);
        return ticketService.createTicket(ticketRequestDTO);
    }

    @GetMapping("/{id}")
    public TicketResponseDTO getTicketById(
            @PathVariable Long id) {
        log.debug("Received request to fetch ticket. ticketId={}", id);

        return ticketService.getTicketById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public TicketResponseDTO updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {
        log.debug("Received request to update ticket. ticketId={}", id);
        return ticketService.updateTicket(id, ticketRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        log.debug("Received request to delete ticket. ticketId={}", id);
        ticketService.deleteTicket(id);
    }






}
        

 
