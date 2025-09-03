package com.state.machine.test.configuration;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.state.machine.test.dto.Booking;
import com.state.machine.test.dto.BookingStates;
import com.state.machine.test.dto.BookingEventPayload;

@Configuration
public class KafkaConsumer {

    @KafkaListener(topics = "booking-events", groupId = "booking-service")
    public void consume(String eventJson) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookingEventPayload event = objectMapper.readValue(eventJson, BookingEventPayload.class);

        Booking booking = new Booking();
        booking.setUserName(event.getUserName());
        booking.setUserId(event.getUserId());
        booking.setFacilityId(event.getFacilityId());
        booking.setBookingState(BookingStates.REQUESTED);
        booking.setStartTime(LocalDateTime.parse(event.getStartTime()));
        booking.setEndTime(LocalDateTime.parse(event.getEndTime()));

        System.out.println("Booking created for userId: " + booking.getUserId());
    }
}