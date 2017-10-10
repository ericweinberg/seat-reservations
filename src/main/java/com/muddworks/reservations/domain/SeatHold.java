package com.muddworks.reservations.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 10/9/17.
 */
public class SeatHold {

    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    private List<Seat> seats;
    private int id;
    private String customerEmail;
    private LocalDateTime createdDate;

    public SeatHold(List<Seat> seats, String customerEmail) {
        this.seats = seats;
        this.id = idGenerator.getAndIncrement();
        this.customerEmail = customerEmail;
        this.createdDate = LocalDateTime.now();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
