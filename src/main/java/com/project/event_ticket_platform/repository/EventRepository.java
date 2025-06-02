package com.project.event_ticket_platform.repository;

import com.project.event_ticket_platform.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findByIdAndOrganizerId(UUID id,UUID organizerId);
    Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);
}
