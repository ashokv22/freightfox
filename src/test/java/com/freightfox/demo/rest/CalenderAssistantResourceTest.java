package com.freightfox.demo.rest;


import com.freightfox.demo.domain.Employee;
import com.freightfox.demo.domain.MeetingSlot;
import com.freightfox.demo.domain.Meetings;
import com.freightfox.demo.service.MeetingsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalenderAssistantResourceTest {

    @Mock
    private MeetingsService meetingsService;

    @InjectMocks
    private CalenderAssistantResource calenderAssistantResource;

    @Test
    void testBookMeeting() {
        Meetings meeting = new Meetings();
        meeting.setId(null); // New meeting
        when(meetingsService.saveMeeting(any())).thenReturn(meeting);
        ResponseEntity<Meetings> responseEntity = calenderAssistantResource.bookMeeting(meeting);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(meeting, responseEntity.getBody());
    }

    @Test
    void testBookMeetingInvalidMeetingIdBadRequest() {
        Meetings meeting = new Meetings();
        meeting.setId(1L); // Existing meeting ID
        ResponseEntity<Meetings> responseEntity = calenderAssistantResource.bookMeeting(meeting);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void findUserFreeSlots_ValidInput_Success() {
        Long employee1Id = 1L;
        Long employee2Id = 2L;
        /* List of expected meeting slots */
        List<MeetingSlot> expectedFreeSlots = List.of();
        when(meetingsService.getFreeTimeSlotsOfEmployees(anyLong(), anyLong())).thenReturn(expectedFreeSlots);
        ResponseEntity<List<MeetingSlot>> responseEntity = calenderAssistantResource.findUserFreeSlots(employee1Id, employee2Id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedFreeSlots, responseEntity.getBody());
    }

    @Test
    void getMeetingConflictsForUsers_ValidMeetingId_Success() {
        Long meetingId = 1L;
        /* List of expected conflicting employees */
        List<Employee> expectedConflicts = List.of();

        when(meetingsService.getMeetingConflictsForUsers(anyLong())).thenReturn(expectedConflicts);
        ResponseEntity<List<Employee>> responseEntity = calenderAssistantResource.getMeetingConflictsForUsers(meetingId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedConflicts, responseEntity.getBody());
    }
}
