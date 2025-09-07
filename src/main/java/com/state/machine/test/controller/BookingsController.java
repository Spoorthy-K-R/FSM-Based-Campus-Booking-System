package com.state.machine.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.state.machine.test.configuration.KafkaProducer;


@Controller
public class BookingsController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/book-facility")
    @ResponseBody
    public String handleFacilityBooking(@RequestBody String bookingRequest) {
        // Logic to handle the booking request
        kafkaProducer.publish("booking-events", bookingRequest);
        System.out.println("Received booking request: " + bookingRequest);
        return "Booking request received";
    }

    @GetMapping("/health/check")
    @ResponseBody
    public String checkHealth() {
        System.out.println("Health Check call made");
        return "Health Check successful";
    }


    
}
