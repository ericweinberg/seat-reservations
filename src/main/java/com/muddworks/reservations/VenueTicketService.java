package com.muddworks.reservations;

import com.muddworks.reservations.domain.Seat;
import com.muddworks.reservations.domain.SeatHold;
import com.muddworks.reservations.domain.Venue;
import com.muddworks.reservations.exception.HoldNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the
 *
 * Created on 10/9/17.
 */
public class VenueTicketService implements TicketService {

    private static final int HELD_SEAT_TIMEOUT_SECONDS = 5;
    private Venue venue;
    private Map<Integer, SeatHold> seatHoldMap = new ConcurrentHashMap<>();

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public VenueTicketService(int rows, int seatsPerRow) {
        this.venue = new Venue(rows, seatsPerRow);

        executorService.scheduleAtFixedRate(() -> {
            seatHoldMap.values().forEach(seatHold -> {
                if(seatHold.getCreatedDate().plusSeconds(HELD_SEAT_TIMEOUT_SECONDS).isAfter(LocalDateTime.now())) {
                    venue.releaseSeats(seatHold.getSeats());
                }
            });
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void printSeatMap() {
        venue.printVenueMap();
    }

    @Override
    public int numSeatsAvailable() {
        return venue.getNumberOfAvailableSeats();
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        List<Seat> seats = venue.holdBestSeats(numSeats);
        SeatHold seatHold = new SeatHold(seats, customerEmail);
        seatHoldMap.put(seatHold.getId(), seatHold);
        return seatHold;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        //look up the SeatHold
        SeatHold seatHold = seatHoldMap.get(seatHoldId);
        //if seatHold isn't found, the id is bad, or the hold expired.
        if(seatHold == null) {
            throw new HoldNotFoundException();
        }
        //if it exists, reserve the seats
        venue.reserveSeats(seatHold.getSeats());
        return UUID.randomUUID().toString();
    }

    public void lookupConfirmation(String confirmationNumber) {

    }
}
