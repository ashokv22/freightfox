package com.freightfox.demo.repository;

import com.freightfox.demo.domain.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participants, Long> {
}
