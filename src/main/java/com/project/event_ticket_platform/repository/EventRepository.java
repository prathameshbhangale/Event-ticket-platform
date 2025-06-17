package com.project.event_ticket_platform.repository;

import com.project.event_ticket_platform.domain.enums.EventStatusEnum;
import com.project.event_ticket_platform.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findByIdAndOrganizerId(UUID id,UUID organizerId);
    Optional<Event> findByIdAndStatus(UUID id, EventStatusEnum status);
    Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);
    Page<Event> findByStatus(EventStatusEnum status, Pageable pageable);

    @Query(
            value = "SELECT e FROM Event e " +
                    "WHERE e.status = 'PUBLISHED' AND (" +
                    "LOWER(e.name) LIKE LOWER(concat('%', :searchTerm, '%')) " +
                    "OR LOWER(e.venue) LIKE LOWER(concat('%', :searchTerm, '%')))",
            countQuery = "SELECT count(e) FROM Event e " +
                    "WHERE e.status = 'PUBLISHED' AND (" +
                    "LOWER(e.name) LIKE LOWER(concat('%', :searchTerm, '%')) " +
                    "OR LOWER(e.venue) LIKE LOWER(concat('%', :searchTerm, '%')))"
    )
    Page<Event> searchEvents(@Param("searchTerm") String searchTerm, Pageable pageable);
}
