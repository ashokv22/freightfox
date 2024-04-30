package com.freightfox.demo.service.impl;

import com.freightfox.demo.domain.Employee;
import com.freightfox.demo.domain.MeetingSlot;
import com.freightfox.demo.domain.Meetings;
import com.freightfox.demo.exeptions.TimeSlotConflictException;
import com.freightfox.demo.repository.MeetingsRepository;
import com.freightfox.demo.service.MeetingsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MeetingServiceImpl implements MeetingsService {

    private MeetingsRepository meetingsRepository;

    @Override
    public List<MeetingSlot> generateTimeSlots(Long employeeId) {
        List<MeetingSlot> slots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(7, 0);
        LocalTime endTime = LocalTime.of(19, 0); // 7 PM
        int duration = 30;
        while (startTime.isBefore(endTime)) {
            if (!checkForMeetingConflict(employeeId, startTime, startTime.plusMinutes(duration))) {
                MeetingSlot slot = new MeetingSlot();
                slot.setStartTime(startTime);
                slot.setEndTime(startTime.plusMinutes(duration));
                slots.add(slot);
            }
            startTime = startTime.plusMinutes(duration);
        }
        return slots;
    }

    @Override
    public Meetings saveMeeting(Meetings meeting) {
        // Validate meeting details (title, start/end time, attendees)
        Employee owner = meeting.getOwner();

        // Check for conflicts with owner's existing meetings
        if (checkForMeetingConflict(owner.getId(), meeting.getStartTime(), meeting.getEndTime())) {
            throw new TimeSlotConflictException("Meeting conflicts with existing meeting");
        }
        return meetingsRepository.save(meeting);
    }

    private boolean checkForMeetingConflict(Long id, LocalTime startTime, LocalTime endTime) {
        return meetingsRepository.existsByOwnerIdAndStartTimeAndEndTime(id, startTime, endTime);
    }
    
}
