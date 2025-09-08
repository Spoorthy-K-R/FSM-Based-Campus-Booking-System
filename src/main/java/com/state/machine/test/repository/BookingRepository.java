package com.state.machine.test.repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.state.machine.test.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // You get these for free:
    // save(), findById(), findAll(), deleteById(), count(), etc.

    // You can also define custom queries:
    List<Booking> findByUserId(String userId);
    List<Booking> findByFacilityId(String facilityId);

    @Query(value = """
    select count(*) 
    from booking b
    where b.facility_id = :facilityId
        and (:excludeId is null or b.id <> :excludeId)
        and b.state in ('APPROVED','IN_USE')
        and b.start_time < :endTime
        and b.end_time   > :startTime
    """, nativeQuery = true)
    long countConflicts(@Param("facilityId") String facilityId,
                            @Param("startTime")  java.time.LocalDateTime startTime,
                            @Param("endTime")    java.time.LocalDateTime endTime,
                            @Param("excludeId")  Long excludeId);
    
}