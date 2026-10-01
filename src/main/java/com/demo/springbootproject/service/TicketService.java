package com.demo.springbootproject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.demo.springbootproject.Entity.TicketEntity;
import com.demo.springbootproject.dto.TicketRequestDTO;
import com.demo.springbootproject.dto.TicketResponseDTO;
import com.demo.springbootproject.repository.TicketRepository;
import com.demo.springbootproject.enums.TicketStatus;
import com.demo.springbootproject.exception.ResourceNotFoundException;


@Service
public class TicketService {

    private String getCurrentUsername() {
    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    return authentication.getName();
    }

    private final TicketRepository ticketRepository;

    // Authentication authentication =
    //         SecurityContextHolder.getContext().getAuthentication();

    // Long userId = (Long) authentication.getPrincipal();

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository; 
    }

    private TicketEntity findTicketById(Long id) {
    return ticketRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Ticket not found with id: " + id
                    )
            );
    }

    private TicketEntity createTicketEntity(TicketRequestDTO ticketRequest) {

        TicketEntity ticketEntity = new TicketEntity();

        ticketEntity.setTitle(ticketRequest.getTitle());
        ticketEntity.setDescription(ticketRequest.getDescription());
        ticketEntity.setTicketPriority(ticketRequest.getTicketPriority());
        ticketEntity.setCreatedBy(getCurrentUsername());    
    return ticketEntity;
}

    public List<TicketResponseDTO> getAllTickets(){
        List<TicketEntity> tickets = ticketRepository.findAll(); //this will return the ticketentity from the database, not rsponsedto
        List<TicketResponseDTO> responses = new ArrayList<>();

        for (TicketEntity ticket : tickets) {

        TicketResponseDTO response = new TicketResponseDTO(
            ticket.getId(),
            ticket.getTitle(),
            ticket.getDescription(),
            ticket.getTicketStatus(),
            ticket.getTicketPriority(),
            ticket.getCreatedBy(),
            ticket.getCreatedAt(),
            ticket.getUpdatedBy(),
            ticket.getUpdatedAt()
        );

        responses.add(response);
    }

    return responses;}

    public TicketResponseDTO createTicket(TicketRequestDTO ticketRequest) {

        TicketEntity ticketEntity = createTicketEntity(ticketRequest);

        TicketEntity savedTicket = ticketRepository.save(ticketEntity);

        return new TicketResponseDTO(
                savedTicket.getId(),
                savedTicket.getTitle(),
                savedTicket.getDescription(),
                savedTicket.getTicketStatus(),
                savedTicket.getTicketPriority(),
                savedTicket.getCreatedBy(),
                savedTicket.getCreatedAt(),
                savedTicket.getUpdatedBy(),
                savedTicket.getUpdatedAt()
        );
    }

    public TicketResponseDTO getTicketById(Long id) {

        TicketEntity ticket = findTicketById(id);

        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getTicketStatus(),
                ticket.getTicketPriority(),
                ticket.getCreatedBy(),
                ticket.getCreatedAt(),
                ticket.getUpdatedBy(),
                ticket.getUpdatedAt()
        );
    }

    public TicketResponseDTO updateTicket( Long id, TicketRequestDTO ticketRequest) {

        TicketEntity ticket = findTicketById(id);
            validateAndAssignvalues( ticket, ticketRequest);
            ticket.setUpdatedBy(getCurrentUsername());
            TicketEntity updatedTicket = ticketRepository.save(ticket);


            return new TicketResponseDTO(
                    updatedTicket.getId(),
                    updatedTicket.getTitle(),
                    updatedTicket.getDescription(),
                    updatedTicket.getTicketStatus(),
                    updatedTicket.getTicketPriority(),
                    updatedTicket.getCreatedBy(),
                    updatedTicket.getCreatedAt(),
                    updatedTicket.getUpdatedBy(),
                    updatedTicket.getUpdatedAt()
            );
    }

    public void deleteTicket(Long id) {
        TicketEntity ticket = findTicketById(id);

        ticketRepository.delete(ticket);
    }


    //helper methods

    void validateAndAssignvalues(TicketEntity ticket, TicketRequestDTO ticketRequest) {
    if (ticketRequest.getTitle() != null) {
        ticket.setTitle(ticketRequest.getTitle());
    }

    if (ticketRequest.getDescription() != null) {
        ticket.setDescription(ticketRequest.getDescription());
    }

    if (ticketRequest.getTicketPriority() != null) {
        ticket.setTicketPriority(ticketRequest.getTicketPriority());
    }

    if (ticketRequest.getTicketStatus() != null) {
        ticket.setTicketStatus(ticketRequest.getTicketStatus());
    }
    }
}


