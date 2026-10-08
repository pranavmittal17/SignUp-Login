package com.demo.springbootproject.service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.demo.springbootproject.Entity.TicketEntity;
import com.demo.springbootproject.dto.TicketRequestDTO;
import com.demo.springbootproject.dto.TicketResponseDTO;
import com.demo.springbootproject.repository.TicketRepository;
import com.demo.springbootproject.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import com.demo.springbootproject.enums.TicketStatus;
import com.demo.springbootproject.exception.ResourceNotFoundException;


@Service
@Slf4j 
public class TicketService {
    private String getCurrentUsername() {
    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    return authentication.getName();
    }

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    // Authentication authentication =
    //         SecurityContextHolder.getContext().getAuthentication();

    // Long userId = (Long) authentication.getPrincipal();

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository; 
        this.userRepository = userRepository;
    }

    public Page<TicketResponseDTO> getMyTickets(Pageable pageable) {

        String username = getCurrentUsername();

        Page<TicketEntity> tickets = ticketRepository.getMyTickets(pageable, username);
        
        return tickets.map(ticket -> new TicketResponseDTO(
            ticket.getId(),
            ticket.getTitle(),
            ticket.getDescription(),
            ticket.getTicketStatus(),
            ticket.getTicketPriority(),
            ticket.getCreatedBy(),
            ticket.getCreatedAt(),
            ticket.getUpdatedBy(),
            ticket.getUpdatedAt(),
            ticket.getAssignedTo()
    ));
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
        ticketEntity.setTicketStatus(ticketRequest.getTicketStatus());
        log.info("STATUS SET ON ENTITY = {}", ticketEntity.getTicketStatus());
        ticketEntity.setCreatedBy(getCurrentUsername());    

        if ((ticketRequest.getAssignedTo() != null &&!ticketRequest.getAssignedTo().isBlank())) 
            {     
                userRepository.findByUsername(ticketRequest.getAssignedTo())
                .orElseThrow(
                    () -> new ResourceNotFoundException("Assigned user not found: "
                + ticketRequest.getAssignedTo())
                );
                ticketEntity.setAssignedTo(ticketRequest.getAssignedTo());
            }
        else 
            {
                ticketEntity.setAssignedTo(getCurrentUsername());
            }
    return ticketEntity;
}

    
    public Page<TicketResponseDTO> getAllTickets(Pageable pageable){
        Page<TicketEntity> tickets = ticketRepository.getAllTickets(pageable); //this will return the ticketentity from the database, not rsponsedto
        
        return tickets.map(ticket -> new TicketResponseDTO(
            ticket.getId(),
            ticket.getTitle(),
            ticket.getDescription(),
            ticket.getTicketStatus(),
            ticket.getTicketPriority(),
            ticket.getCreatedBy(),
            ticket.getCreatedAt(),
            ticket.getUpdatedBy(),
            ticket.getUpdatedAt(),
            ticket.getAssignedTo()

        
        ));
    }

    public Page<TicketResponseDTO> searchTickets(
            String query,
            Pageable pageable) {

        Page<TicketEntity> tickets =
                ticketRepository.searchTickets(query, pageable);

        return tickets.map(ticket -> new TicketResponseDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getTicketStatus(),
                ticket.getTicketPriority(),
                ticket.getCreatedBy(),
                ticket.getCreatedAt(),
                ticket.getUpdatedBy(),
                ticket.getUpdatedAt(),
                ticket.getAssignedTo()
        ));
    }
            
        
     
    

    public TicketResponseDTO createTicket(TicketRequestDTO ticketRequest) {

        TicketEntity ticketEntity = createTicketEntity(ticketRequest);

        log.info("STATUS RECEIVED FROM REQUEST = {}", ticketRequest.getTicketStatus());
        TicketEntity savedTicket = ticketRepository.save(ticketEntity);

        log.info(
            "Ticket created successfully. ticketId={}, createdBy={}, assignedTo={}",
            savedTicket.getId(),
            savedTicket.getCreatedBy(),
            savedTicket.getAssignedTo()
        );
        return new TicketResponseDTO(
                savedTicket.getId(),
                savedTicket.getTitle(),
                savedTicket.getDescription(),
                savedTicket.getTicketStatus(),
                savedTicket.getTicketPriority(),
                savedTicket.getCreatedBy(),
                savedTicket.getCreatedAt(),
                savedTicket.getUpdatedBy(),
                savedTicket.getUpdatedAt(),
                savedTicket.getAssignedTo()
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
                ticket.getUpdatedAt(),
                ticket.getAssignedTo()
        );
    }

    public TicketResponseDTO updateTicket( Long id, TicketRequestDTO ticketRequest) {

        TicketEntity ticket = findTicketById(id);
            validateAndAssignvalues( ticket, ticketRequest);
            ticket.setUpdatedBy(getCurrentUsername());
            TicketEntity updatedTicket = ticketRepository.save(ticket);
            
            log.info(
                "Ticket updated successfully. ticketId={}, updatedBy={}, assignedTo={}",
                updatedTicket.getId(),
                updatedTicket.getUpdatedBy(),
                updatedTicket.getAssignedTo()
            );

            return new TicketResponseDTO(
                    updatedTicket.getId(),
                    updatedTicket.getTitle(),
                    updatedTicket.getDescription(),
                    updatedTicket.getTicketStatus(),
                    updatedTicket.getTicketPriority(),
                    updatedTicket.getCreatedBy(),
                    updatedTicket.getCreatedAt(),
                    updatedTicket.getUpdatedBy(),
                    updatedTicket.getUpdatedAt(),
                    updatedTicket.getAssignedTo()
            );
    }

    public void deleteTicket(Long id) {
        TicketEntity ticket = findTicketById(id);
        ticketRepository.delete(ticket);

        log.info(
            "Ticket deleted successfully. ticketId={}, deletedBy={}",
            id,
            getCurrentUsername()
        );
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
    if (ticketRequest.getAssignedTo() != null) {
    ticket.setAssignedTo(ticketRequest.getAssignedTo());
}
    ticket.setUpdatedBy(getCurrentUsername());
    ticket.setUpdatedAt(LocalDateTime.now());
    }
}


