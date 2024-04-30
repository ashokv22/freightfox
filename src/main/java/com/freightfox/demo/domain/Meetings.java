package com.freightfox.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "meetings")
public class Meetings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Employee owner;

    private LocalTime startTime;

    private LocalTime endTime;

//    private List<Employee> attendees;

    private String description;

}
