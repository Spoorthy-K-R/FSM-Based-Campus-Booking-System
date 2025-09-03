package com.state.machine.test.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BookingEventPayload {
    
    private String type;     
    private String bookingId;  
    private String userId;
    private String userName;
    private String facilityId;
    private String startTime;
    private String endTime;

}