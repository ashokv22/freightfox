package com.freightfox.demo.repository;

import com.freightfox.demo.domain.Meetings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface MeetingsRepository extends JpaRepository<Meetings, Long> {
    List<Meetings> findByOwnerIdAndStartTimeBeforeAndEndTimeAfter(Long employeeId, LocalTime startTime, LocalTime endTime);

    boolean existsByOwnerIdAndStartTimeAndEndTime(Long employeeId, LocalTime startTime, LocalTime endTime);

    List<Meetings> findByOwnerId(Long ownerId);

}
