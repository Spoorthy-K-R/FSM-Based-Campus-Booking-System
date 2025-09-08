package com.state.machine.test.configuration;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.state.machine.test.model.Booking;
import com.state.machine.test.model.BookingStates;
import com.state.machine.test.service.BookingService;
import com.state.machine.test.dto.BookingEventPayload;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class KafkaConsumer {

    @Autowired
    private BookingService bookingService;

    @KafkaListener(topics = "booking-events", groupId = "booking-service")
    public void consume(String eventJson) throws Exception {

        System.out.println("Received event: " + eventJson);
        ObjectMapper objectMapper = new ObjectMapper();
        BookingEventPayload event = objectMapper.readValue(eventJson, BookingEventPayload.class);

        System.out.println("Parsed event: " + event);

        Booking booking = new Booking();
        booking.setUserName(event.getUserName());
        booking.setUserId(event.getUserId());
        booking.setFacilityId(event.getFacilityId());
        booking.setBookingState(BookingStates.REQUESTED);
        booking.setStartTime(LocalDateTime.parse(event.getStartTime()));
        booking.setEndTime(LocalDateTime.parse(event.getEndTime()));

        System.out.println("Booking created for userId: " + booking.getUserId());

        bookingService.createBooking(booking);
    }
}