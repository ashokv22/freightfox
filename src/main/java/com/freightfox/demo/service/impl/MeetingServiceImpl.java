package com.freightfox.demo.service.impl;

import com.freightfox.demo.domain.Employee;
import com.freightfox.demo.domain.MeetingSlot;
import com.freightfox.demo.domain.Meetings;
import com.freightfox.demo.repository.MeetingsRepository;
import com.freightfox.demo.service.MeetingsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class MeetingServiceImpl implements MeetingsService {

    private MeetingsRepository meetingsRepository;

    @Override
    public List<MeetingSlot> getFreeTimeSlotsOfEmployees(Long employee1Id, Long employee2Id) {
        List<MeetingSlot> slots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(19, 0); // 7 PM
        int duration = 30;
        while (startTime.isBefore(endTime)) {
            if (!checkForMeetingConflict(employee1Id, startTime, startTime.plusMinutes(duration)) && !checkForMeetingConflict(employee2Id, startTime, startTime.plusMinutes(duration))) {
                MeetingSlot slot = new MeetingSlot();
                slot.setStartTime(startTime);
                slot.setEndTime(startTime.plusMinutes(duration));
                slots.add(slot);
            }
            startTime = startTime.plusMinutes(duration);
        }
        log.info("Free slots: {}", slots);
        return slots;
    }

    @Override
    public Meetings saveMeeting(Meetings meeting) {
        return meetingsRepository.save(meeting);
    }

    @Override
    public List<Employee> getMeetingConflictsForUsers(Long meetingId) {

        Optional<Meetings> meetingOptional = meetingsRepository.findById(meetingId);
        if (meetingOptional.isEmpty()) {
            return null;
        }

        Meetings meeting = meetingOptional.get();
        LocalTime startTime = meeting.getStartTime();
        LocalTime endTime = meeting.getEndTime();

        // Find other meetings where any of the attendees are also attendees
//        List<Meetings> conflictingMeetings = meetingsRepository.findByStartTimeAndEndTime(startTime, endTime);

        List<Meetings> conflictingMeetings = meetingsRepository.findConflictingMeetings(meeting.getOwner().getId(),
                meeting.getParticipant().getId(),
                startTime, endTime,
                meetingId);

        // Extract conflicted participants from conflicting meetings
        List<Employee> conflictedParticipants = new ArrayList<>();
        for (Meetings conflictingMeeting : conflictingMeetings) {
            conflictedParticipants.add(conflictingMeeting.getParticipant());
        }
        // Remove the organizer from the conflicted participants
        conflictedParticipants.remove(meeting.getOwner());

        return conflictedParticipants;
    }

    private boolean checkForMeetingConflict(Long employeeId, LocalTime startTime, LocalTime endTime) {
        return meetingsRepository.existsByOwnerIdAndStartTimeAndEndTime(employeeId, startTime, endTime);
    }
    
}
