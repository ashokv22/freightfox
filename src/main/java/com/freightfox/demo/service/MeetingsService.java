package com.freightfox.demo.service;

import com.freightfox.demo.domain.MeetingSlot;
import com.freightfox.demo.domain.Meetings;

import java.util.List;

public interface MeetingsService {
    List<MeetingSlot> generateTimeSlots(Long employeeId);

    Meetings saveMeeting(Meetings meeting);
}
