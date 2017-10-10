package com.muddworks.reservations.domain;

import com.muddworks.reservations.exception.NoSeatsAvailableException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test suite for {@link Venue}.
 * <p>
 * Created on 10/9/17.
 */
public class VenueTest {

    private Venue venue;

    @Before
    public void setUp() {
        this.venue = new Venue(10, 3);
    }


    @Test
    public void testNumberOfSeats() {
        assertThat(venue.getNumberOfSeats(), is(30));
    }

    @Test
    public void testAvailableSeats() {
        assertThat(venue.getNumberOfAvailableSeats(), is(30));
    }

    @Test
    public void testHoldBestSeats() {
        List<Seat> expectedSeats = Arrays.asList(new Seat(0, 0),
                new Seat(0, 1));
        List<Seat> seats = venue.holdBestSeats(2);

        assertThat(seats.size(), is(expectedSeats.size()));
        assertThat(seats.containsAll(expectedSeats), is(true));
        assertThat(venue.getNumberOfAvailableSeats(), is(28));

        expectedSeats = Arrays.asList(new Seat(0, 2));
        seats = venue.holdBestSeats(1);
        assertThat(seats.size(), is(expectedSeats.size()));
        assertThat(seats.containsAll(expectedSeats), is(true));
        assertThat(venue.getNumberOfAvailableSeats(), is(27));

        expectedSeats = Arrays.asList(new Seat(1, 0), new Seat(1, 1));
        seats = venue.holdBestSeats(2);
        assertThat(seats.size(), is(expectedSeats.size()));
        assertThat(seats.containsAll(expectedSeats), is(true));
        assertThat(venue.getNumberOfAvailableSeats(), is(25));

        expectedSeats = Arrays.asList(new Seat(2, 0), new Seat(2, 1),
                new Seat(2, 2));
        seats = venue.holdBestSeats(3);
        assertThat(seats.size(), is(expectedSeats.size()));
        assertThat(seats.containsAll(expectedSeats), is(true));
        assertThat(venue.getNumberOfAvailableSeats(), is(22));

        expectedSeats = Arrays.asList(new Seat(1, 2));
        seats = venue.holdBestSeats(1);
        assertThat(seats.size(), is(expectedSeats.size()));
        assertThat(seats.containsAll(expectedSeats), is(true));
        assertThat(venue.getNumberOfAvailableSeats(), is(21));
    }

    @Test(expected = NoSeatsAvailableException.class)
    public void testNoSeatsAvailableWhenBlockIsTooLarge() {
        venue.holdBestSeats(4);
    }

    @Test
    public void testReleaseSeat() {
        List<Seat> heldSeats = venue.holdBestSeats(2);
        assertThat(venue.getNumberOfAvailableSeats(), is(28));

        venue.releaseSeats(heldSeats);
        assertThat(venue.getNumberOfAvailableSeats(), is(30));

        List<Seat> reheldSeats = venue.holdBestSeats(2);
        assertThat(venue.getNumberOfAvailableSeats(), is(28));

        assertThat(heldSeats, is(reheldSeats));
    }

    @Test
    public void testReserveSeats() {

    }
}
