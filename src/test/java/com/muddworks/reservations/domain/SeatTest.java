package com.muddworks.reservations.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created on 10/9/17.
 */
public class SeatTest {

    @Test
    public void testConstructor() {
        Seat seat = new Seat(1, 2);
        assertThat(seat.getRowNumber(), is(1));
        assertThat(seat.getSeatNumber(), is(2));
        assertThat(seat.getStatus(), is(Status.OPEN));

        seat = new Seat(new Seat(4, 1));
        assertThat(seat.getRowNumber(), is(4));
        assertThat(seat.getSeatNumber(), is(1));
        assertThat(seat.getStatus(), is(Status.OPEN));
    }

    @Test
    public void testHoldSeat() {
        Seat seat = new Seat(3, 1);
        seat.holdSeat();
        assertThat(seat.getStatus(), is(Status.HELD));
    }

    @Test
    public void testReserveSeat() {
        Seat seat = new Seat(3, 1);
        seat.reserveSeat();
        assertThat(seat.getStatus(), is(Status.RESERVED));
    }

    @Test
    public void testReleaseSeat() {
        Seat seat = new Seat(3, 1);
        seat.holdSeat();
        assertThat(seat.getStatus(), is(Status.HELD));

        seat.releaseSeat();
        assertThat(seat.getStatus(), is(Status.OPEN));

        seat.reserveSeat();
        assertThat(seat.getStatus(), is(Status.RESERVED));

        seat.releaseSeat();
        assertThat(seat.getStatus(), is(Status.RESERVED));

        seat.holdSeat();
        assertThat(seat.getStatus(), is(Status.RESERVED));
    }
}
