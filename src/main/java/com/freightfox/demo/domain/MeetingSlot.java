package com.freightfox.demo.domain;

import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeetingSlot {

    private LocalTime startTime;

    private LocalTime endTime;

}
