package com.freightfox.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "distance")
public class DistanceResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourcePincode;

    private String destinationPincode;

    private String distance;

    private String duration;

}
