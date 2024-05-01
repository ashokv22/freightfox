package com.freightfox.demo.repository;

import com.freightfox.demo.domain.Meetings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface MeetingsRepository extends JpaRepository<Meetings, Long> {

    boolean existsByOwnerIdAndStartTimeAndEndTime(Long employeeId, LocalTime startTime, LocalTime endTime);


    @Query(value = "SELECT * FROM meetings " +
            "WHERE (owner_id = :ownerId OR participant_id = :participantId) " +
            "AND start_time = :startTime  " +
            "AND end_time = :endTime " +
            "AND id != :meetingId", nativeQuery = true)
    List<Meetings> findConflictingMeetings(@Param("ownerId") Long organizerId,
                                          @Param("participantId") Long attendeeIds,
                                          @Param("startTime") LocalTime startTime,
                                          @Param("endTime") LocalTime endTime,
                                          @Param("meetingId") Long meetingId);
}
