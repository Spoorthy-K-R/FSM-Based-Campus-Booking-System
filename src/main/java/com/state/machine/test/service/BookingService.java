package com.state.machine.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.state.machine.test.configuration.KafkaProducer;
import com.state.machine.test.model.Booking;
import com.state.machine.test.model.BookingStates;
import com.state.machine.test.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repo;

    @Autowired
    private KafkaProducer kafkaProducer;
    
    @Transactional
    public void approve(Long id){
        var b = getOrThrow(id);
        requireState(b, BookingStates.REQUESTED);
        ensureNoOverlap(b);                 // see step 3
        b.setBookingState(BookingStates.APPROVED);
        repo.save(b);
        publishStatus(b);
    }

    @Transactional 
    public void reject(Long id){ 
        transition(id, BookingStates.REQUESTED, BookingStates.REJECTED); 
    }
    
    @Transactional 
    public void start(Long id){  
        transition(id, BookingStates.APPROVED,  BookingStates.IN_USE); 
    }
    
    @Transactional 
    public void complete(Long id){
        transition(id, BookingStates.IN_USE,    BookingStates.COMPLETED); 
    }
    
    @Transactional 
    public void cancel(Long id){
        var b = getOrThrow(id);
        if (b.getBookingState() == BookingStates.REQUESTED || b.getBookingState()==BookingStates.APPROVED){
        b.setBookingState(BookingStates.CANCELLED); repo.save(b); publishStatus(b);
        } else throw new IllegalStateException("Can cancel only REQUESTED/APPROVED");
    }

    @Transactional
    public Booking createBooking(Booking b) {
        return repo.save(b);  // persists the entity
    }

    private void transition(Long id, BookingStates expected, BookingStates target){
        var b = getOrThrow(id);
        requireState(b, expected);
        b.setBookingState(target);
        repo.save(b);
        publishStatus(b);
    }

    private void ensureNoOverlap(Booking b){
    if (b.getStartTime()!=null && b.getEndTime()!=null) {
        long n = repo.countConflicts(b.getFacilityId(), b.getStartTime(), b.getEndTime(), b.getId());
        if (n > 0) throw new IllegalStateException("Overlapping booking exists for facility "+b.getFacilityId());
    }
    }

    private Booking getOrThrow(Long id){
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Booking "+id+" not found"));
    }
    private void requireState(Booking b, BookingStates s){
        if (b.getBookingState()!=s) throw new IllegalStateException("Expected "+s+" but was "+b.getBookingState());
    }

    private void publishStatus(Booking b){
        String json = "{\"bookingId\":"+b.getId()+",\"state\":\""+b.getBookingState().name()+"\"}";
        kafkaProducer.publish("booking-status", json);
    }

    public void publishRequest(String b){
        kafkaProducer.publish("booking-events", b);
    }

    public List<Booking> getUserBookings(String userId) {
        return repo.findByUserId(userId);
    }
}
