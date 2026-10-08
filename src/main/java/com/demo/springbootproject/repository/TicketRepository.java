package com.demo.springbootproject.repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest; 

import java.util.Optional;
import com.demo.springbootproject.Entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    Optional<TicketEntity> findById(Long id);

    @Query("SELECT t FROM TicketEntity t")
    Page<TicketEntity> findAllTickets(Pageable pageable);

    @Query("SELECT t FROM TicketEntity t WHERE t.assignedTo = :username ")
    Page<TicketEntity> getMyTickets(Pageable pageable, @Param("username") String username );
}