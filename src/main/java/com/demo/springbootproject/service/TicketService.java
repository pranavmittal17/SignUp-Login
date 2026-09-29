package com.demo.springbootproject.service;

import org.springframework.stereotype.Service;

import com.demo.springbootproject.Entity.TicketEntity;
import com.demo.springbootproject.dto.TicketRequestDTO;
import com.demo.springbootproject.dto.TicketResponseDTO;
import com.demo.springbootproject.repository.TicketRepository;
import com.demo.springbootproject.enums.TicketStatus;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


private TicketEntity createTicketEntity(TicketRequestDTO ticketRequest) {

    TicketEntity ticketEntity = new TicketEntity();

    ticketEntity.setTitle(ticketRequest.getTitle());
    ticketEntity.setDescription(ticketRequest.getDescription());
    ticketEntity.setTicketPriority(ticketRequest.getTicketPriority());
    ticketEntity.setCreatedBy(ticketRequest.getCreatedBy()); //JWT
    ticketEntity.setTicketStatus(TicketStatus.OPEN);

    return ticketEntity;
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
                savedTicket.getUpdatedAt()
        );
    }
}