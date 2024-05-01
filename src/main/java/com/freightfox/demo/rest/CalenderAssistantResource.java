package com.freightfox.demo.rest;

import com.freightfox.demo.domain.Employee;
import com.freightfox.demo.domain.MeetingSlot;
import com.freightfox.demo.domain.Meetings;
import com.freightfox.demo.service.MeetingsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/calender")
@Slf4j
@AllArgsConstructor
public class CalenderAssistantResource {

    private MeetingsService meetingsService;

    @PostMapping("/meetings")
    public ResponseEntity<Meetings> bookMeeting(@RequestBody Meetings meeting) {
        log.info("Request to book meeting: {}", meeting);
        if (meeting.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            meeting = meetingsService.saveMeeting(meeting);
            return ResponseEntity.ok(meeting);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/free-slots")
    public ResponseEntity<List<MeetingSlot>> findUserFreeSlots(@RequestParam Long employee1Id, @RequestParam Long employee2Id) {
        log.info("Request to find free slots: employee1Id={}, employee2Id={}", employee1Id, employee2Id);
        List<MeetingSlot> freeSlots = meetingsService.getFreeTimeSlotsOfEmployees(employee1Id, employee2Id);
        return ResponseEntity.ok(freeSlots);
    }

    @GetMapping("/meeting-conflicts/{meetingId}")
    public ResponseEntity<List<Employee>> getMeetingConflictsForUsers(@PathVariable Long meetingId) {
        List<Employee> conflictsUsers = meetingsService.getMeetingConflictsForUsers(meetingId);
        if (conflictsUsers == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conflictsUsers);
    }
}
