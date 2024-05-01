package com.freightfox.demo.service;

import com.freightfox.demo.domain.Employee;
import com.freightfox.demo.domain.MeetingSlot;
import com.freightfox.demo.domain.Meetings;

import java.util.List;

public interface MeetingsService {
    List<MeetingSlot> getFreeTimeSlotsOfEmployees(Long employeeId, Long employee2Id);

    Meetings saveMeeting(Meetings meeting);

    List<Employee> getMeetingConflictsForUsers(Long ownerId);
}
