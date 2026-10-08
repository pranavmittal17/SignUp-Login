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
    Page<TicketEntity> getAllTickets(Pageable pageable);

    @Query("SELECT t FROM TicketEntity t WHERE t.assignedTo = :username ")
    Page<TicketEntity> getMyTickets(Pageable pageable, @Param("username") String username );


     @Query(value = """
        SELECT *
        FROM ticket_entity
        WHERE CAST(id AS CHAR) LIKE CONCAT('%', :query, '%')
           OR LOWER(title) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(description) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(ticket_status) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(ticket_priority) LIKE LOWER(CONCAT('%', :query, '%'))
        """,
        countQuery = """
        SELECT COUNT(*)
        FROM ticket_entity
        WHERE CAST(id AS CHAR) LIKE CONCAT('%', :query, '%')
           OR LOWER(title) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(description) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(ticket_status) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(ticket_priority) LIKE LOWER(CONCAT('%', :query, '%'))
        """,
        nativeQuery = true)
    Page<TicketEntity> searchTickets(
            @Param("query") String query,
            Pageable pageable
    );
}