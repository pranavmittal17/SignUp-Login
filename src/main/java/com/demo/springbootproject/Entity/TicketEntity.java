package com.demo.springbootproject.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.demo.springbootproject.enums.TicketPriority;
import com.demo.springbootproject.enums.TicketStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity 
@EntityListeners(AuditingEntityListener.class)
@Getter 
@Setter 
public class TicketEntity {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String title;

    @Lob  //large objext
    private String description;


    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus = TicketStatus.OPEN;


    @Enumerated(EnumType.STRING)
    private TicketPriority ticketPriority;

    private Long createdBy;

    private Long updatedBy;
    
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


}


