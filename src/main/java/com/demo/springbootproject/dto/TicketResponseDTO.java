package com.demo.springbootproject.dto;

import java.time.LocalDateTime;

import com.demo.springbootproject.enums.TicketPriority;
import com.demo.springbootproject.enums.TicketStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketResponseDTO {

    private Long id;
    private String title;
    private String description;
    private TicketStatus ticketStatus;
    private TicketPriority ticketPriority;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public TicketResponseDTO(
            Long id,
            String title,
            String description,
            TicketStatus ticketStatus,
            TicketPriority ticketPriority,
            String createdBy,
            LocalDateTime createdAt,
            String updatedBy,
            LocalDateTime updatedAt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.ticketStatus = ticketStatus;
        this.ticketPriority = ticketPriority;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
}