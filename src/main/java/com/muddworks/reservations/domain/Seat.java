package com.muddworks.reservations.domain;

import java.util.Objects;

/**
 * Represents a Seat in the venue
 *
 * Created on 10/9/17.
 */
public class Seat {

    private int rowNumber;
    private int seatNumber;
    private Status status;


    public Seat(Seat seat) {
        this(seat.getRowNumber(), seat.getSeatNumber(), seat.getStatus());
    }

    private Seat(int rowNumber, int seatNumber, Status status) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public Seat(int rowNumber, int seatNumber) {
        this(rowNumber, seatNumber, Status.OPEN);
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void releaseSeat() {
        if(this.status != Status.RESERVED) {
            this.status = Status.OPEN;
        }
    }

    public void holdSeat() {
        if(this.status != Status.RESERVED) {
            this.status = Status.HELD;
        }
    }

    public void reserveSeat() {
        this.status = Status.RESERVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return rowNumber == seat.rowNumber &&
                seatNumber == seat.seatNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNumber, seatNumber);
    }
}
