package com.muddworks.reservations;

import com.muddworks.reservations.domain.Seat;
import com.muddworks.reservations.domain.SeatHold;
import com.muddworks.reservations.exception.HoldNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Test suite for {@link VenueTicketService}
 * <p>
 * Created on 10/9/17.
 */
public class VenueTicketServiceTest {

    private TicketService ticketService;

    @Before
    public void setUp() {
        this.ticketService = new VenueTicketService(10, 5);
    }

    @Test
    public void testCreation() {
        assertThat(ticketService.numSeatsAvailable(), is(50));

        ticketService = new VenueTicketService(3, 10);
        assertThat(ticketService.numSeatsAvailable(), is(30));
    }

    @Test
    public void testFindAndHoldSeats() {
        List<Seat> expectedSeats = Arrays.asList(new Seat(0, 0),
                new Seat(0, 1));

        SeatHold seatHold = ticketService.findAndHoldSeats(2, "foo@bar.com");
        assertThat(seatHold.getCustomerEmail(), is("foo@bar.com"));
        assertThat(seatHold.getId(), is(1));
        assertThat(ticketService.numSeatsAvailable(), is(48));
        assertThat(seatHold.getSeats(), is(expectedSeats));
    }

    @Test
    public void testReserve() {
        List<Seat> expectedSeats = Arrays.asList(new Seat(0, 0),
                new Seat(0, 1),
                new Seat(0, 2),
                new Seat(0, 3));

        SeatHold seatHold = ticketService.findAndHoldSeats(4, "another@email.com");

        String confirmationNumber = ticketService.reserveSeats(seatHold.getId(), "another@gmail.com");
        assertThat(confirmationNumber, is(not(nullValue())));
        assertThat(ticketService.numSeatsAvailable(), is(46));
        assertThat(seatHold.getSeats(), is(expectedSeats));

        List<Seat> nextExpectedSeats = Arrays.asList(new Seat(1, 0),
                new Seat(1, 1));
        SeatHold nextSeatHold = ticketService.findAndHoldSeats(2, "next@email.com");
        String nextConfirmationNumber = ticketService.reserveSeats(nextSeatHold.getId(), "next@gmail.com");

        assertThat(nextConfirmationNumber, is(not(nullValue())));
        assertThat(ticketService.numSeatsAvailable(), is(44));
        assertThat(nextSeatHold.getSeats(), is(nextExpectedSeats));

    }

    @Test(expected = HoldNotFoundException.class)
    public void testInvalidSeatHoldId() {
        ticketService.reserveSeats(12412, "invalid@gmail.com");
    }

    @Test
    public void testReserveTimedOut() {

    }


}
