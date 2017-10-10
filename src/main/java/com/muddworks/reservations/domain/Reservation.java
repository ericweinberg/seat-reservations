package com.muddworks.reservations.domain;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a reservation that's already been made.
 *
 * Created on 10/10/17.
 */
public class Reservation {
    private String id;
    private List<Seat> seats;
    private String emailAddress;

    public Reservation(List<Seat> seats, String emailAddress) {
        this.id = UUID.randomUUID().toString();
        this.seats = seats;
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(seats, that.seats) &&
                Objects.equals(emailAddress, that.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seats, emailAddress);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Reservation{");

        sb.append("Confirmation order: ").append(id).append('\n');
        sb.append("Email Address: ").append(emailAddress).append("\n");
        sb.append("Seats: \n");
        seats.forEach(seat -> {
            sb.append("Row number ").append(seat.getRowNumber()).append(", Seat number ").append(seat.getSeatNumber()).append("\n");
        });

        return sb.toString();
    }
}
