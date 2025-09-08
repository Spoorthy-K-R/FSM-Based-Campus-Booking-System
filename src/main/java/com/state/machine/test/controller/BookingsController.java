package com.state.machine.test.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.state.machine.test.configuration.KafkaProducer;
import com.state.machine.test.model.Booking;
import com.state.machine.test.service.BookingService;


@Controller
@RequestMapping("/booking")
public class BookingsController {

    @Autowired
    private BookingService service;

    @PostMapping("/book-facility")
    @ResponseBody
    public String handleFacilityBooking(@RequestBody String bookingRequest) {
        service.publishRequest(bookingRequest);
        // kafkaProducer.publish("booking-events", bookingRequest);
        System.out.println("Received booking request: " + bookingRequest);
        return "Booking request received";
    }

    @PostMapping("/{id}/approve")  
    public Map<String,Object> approve(@PathVariable Long id) 
    { 
        service.approve(id); 
        return Map.of("id", id, "state", "APPROVED"); 
    }

    @PostMapping("/{id}/reject")   
    public Map<String,Object> reject(@PathVariable Long id)  
    { 
        service.reject(id);  
        return Map.of("id", id, "state", "REJECTED"); 
    }
    
    @PostMapping("/{id}/start")    
    public Map<String,Object> start(@PathVariable Long id)   
    { 
        service.start(id);   
        return Map.of("id", id, "state", "IN_USE"); 
    }
    
    @PostMapping("/{id}/complete") 
    public Map<String,Object> complete(@PathVariable Long id)
    { 
        service.complete(id);
        return Map.of("id", id, "state", "COMPLETED"); 
    }

    @PostMapping("/{id}/cancel")   
    public Map<String,Object> cancel(@PathVariable Long id)  
    { 
        service.cancel(id);  
        return Map.of("id", id, "state", "CANCELLED"); 
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getUserBookings(@PathVariable String userId) {
        return service.getUserBookings(userId);
    }

    @GetMapping("/health/check")
    @ResponseBody
    public String checkHealth() {
        System.out.println("Health Check call made");
        return "Health Check successful";
    }


    
}
