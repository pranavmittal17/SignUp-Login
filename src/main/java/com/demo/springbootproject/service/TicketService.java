package com.demo.springbootproject.service;
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

    public Page<TicketResponseDTO> findAllTickets(Pageable pageable){
        Page<TicketEntity> tickets = ticketRepository.findAllTickets(pageable); //this will return the ticketentity from the database, not rsponsedto
        
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


