package com.freightfox.demo.rest;

import com.freightfox.demo.domain.MeetingSlot;
import com.freightfox.demo.domain.Meetings;
import com.freightfox.demo.exeptions.TimeSlotConflictException;
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
    public ResponseEntity<Meetings> bookMeeting(@RequestBody Meetings meeting) throws TimeSlotConflictException {
        log.info("Request to book meeting: {}", meeting);
        try {
            meeting = meetingsService.saveMeeting(meeting);
            return ResponseEntity.ok(meeting);
        } catch (TimeSlotConflictException t) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/free-slots/{employeeId}")
    public ResponseEntity<List<MeetingSlot>> findUserFreeSlots(@PathVariable Long employeeId) {
        // Find free slots considering meeting duration
        List<MeetingSlot> freeSlots = meetingsService.generateTimeSlots(employeeId);
        return ResponseEntity.ok(freeSlots);
    }
}
