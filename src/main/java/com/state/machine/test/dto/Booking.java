package com.state.machine.test.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
// import lombok.Getter;
// import lombok.Setter;

@Data
// @Getter
// @Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String userId;

    private BookingStates bookingState;
    private String facilityId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

}