package com.state.machine.test.dto;

import lombok.Data;
// import lombok.Getter;
// import lombok.Setter;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookingEventPayload {
    
    private String type;     
    private String bookingId;  
    private String userId;
    private String userName;
    private String facilityId;
    private String startTime;
    private String endTime;

    public String getType() {
        return type;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}