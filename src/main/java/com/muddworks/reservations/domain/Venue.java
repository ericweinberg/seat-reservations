package com.muddworks.reservations.domain;

import com.muddworks.reservations.exception.NoSeatsAvailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The venue is responsible for managing which seats have been reserved, held, and are available.
 * <p>
 * Created on 10/9/17.
 */
public class Venue {

    private final int numberOfSeats;
    private final Seat[][] venueLayout;
    private int availableSeats;

    /**
     * Create a new Venue with the given number of rows and seats per row.
     *
     * @param rows        the number of rows in the venue
     * @param seatsPerRow the number of seats per row
     */
    public Venue(int rows, int seatsPerRow) {
        venueLayout = new Seat[rows][seatsPerRow];

        for (int i = 0; i < venueLayout.length; i++) {
            for (int j = 0; j < venueLayout[i].length; j++) {
                venueLayout[i][j] = new Seat(i, j);
            }
        }

        this.numberOfSeats = rows * seatsPerRow;
        this.availableSeats = numberOfSeats;
    }

    /**
     * Get the total number of seats the venue holds.
     *
     * @return the total number of seats
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Get the number of available seats.
     *
     * @return the number of available seats
     */
    public synchronized int getNumberOfAvailableSeats() {
        return availableSeats;
    }

    /**
     * Hold the best seats. For simplicity sake, the "Best Seats" have the following rules.
     * <p>
     * All seating groups must be in the same row
     * The best seats are at the front of venue (row 0)
     * Rows are filled starting at seat 0
     *
     * @param numSeats the number of seats to reserve
     * @return a list of held seats
     */
    public synchronized List<Seat> holdBestSeats(int numSeats) {
        availableSeats -= numSeats;

        boolean foundSeats = false;
        List<Seat> openSeats = new ArrayList<>(numSeats);

        for (int i = 0; i < venueLayout.length; i++) {
            for (int j = 0; j < venueLayout[i].length; j++) {
                Seat seat = venueLayout[i][j];
                if (seat.getStatus() == Status.OPEN) {
                    openSeats.add(seat);
                    if (openSeats.size() == numSeats) {
                        foundSeats = true;
                        break;
                    }
                } else {
                    openSeats.clear();
                    foundSeats = false;
                }
            }

            if (!foundSeats) {
                openSeats.clear();
            } else {
                break;
            }
        }

        if (!foundSeats) {
            throw new NoSeatsAvailableException();
        } else {
            openSeats.forEach(Seat::holdSeat);
        }

        return openSeats.stream().map(Seat::new).collect(Collectors.toList());
    }

    /**
     * Release the pending seats that are being held.
     *
     * @param seats the seats to be released
     */
    public synchronized void releaseSeats(List<Seat> seats) {
        seats.forEach(seat -> venueLayout[seat.getRowNumber()][seat.getSeatNumber()].releaseSeat());
        availableSeats += seats.size();
    }

    /**
     * Convert the seats from held to reserved.
     *
     * @param seats the seats to reserve
     */
    public synchronized void reserveSeats(List<Seat> seats) {
        seats.forEach(seat -> venueLayout[seat.getRowNumber()][seat.getSeatNumber()].reserveSeat());
    }

    /**
     * Print a seat map of the venue.
     */
    public void printVenueMap() {
        System.out.println("Stage");

        for (Seat[] aVenueLayout : venueLayout) {
            StringBuilder builder = new StringBuilder();
            for (Seat seat : aVenueLayout) {
                switch (seat.getStatus()) {
                    case HELD: {
                        builder.append("H");
                        break;
                    }
                    case RESERVED: {
                        builder.append("X");
                        break;
                    }
                    case OPEN: {
                        builder.append("O");
                        break;
                    }
                }

                builder.append(" ");
            }
            System.out.println(builder.toString());
        }
        System.out.println();
        System.out.println();
        System.out.println("Key");
        System.out.println("O -> Open Seat");
        System.out.println("H -> Held Seat");
        System.out.println("X -> Reserved Seat");
    }
}
