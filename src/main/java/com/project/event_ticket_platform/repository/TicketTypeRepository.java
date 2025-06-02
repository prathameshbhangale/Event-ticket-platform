package com.project.event_ticket_platform.repository;

import com.project.event_ticket_platform.domain.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType,UUID> {
}
