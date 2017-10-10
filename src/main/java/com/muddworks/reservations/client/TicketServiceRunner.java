package com.muddworks.reservations.client;

import com.muddworks.reservations.TicketService;
import com.muddworks.reservations.VenueTicketService;
import com.muddworks.reservations.domain.Reservation;
import com.muddworks.reservations.domain.SeatHold;
import com.muddworks.reservations.exception.HoldNotFoundException;
import com.muddworks.reservations.exception.NoSeatsAvailableException;
import com.muddworks.reservations.exception.ReservationNotFoundException;

import java.util.Scanner;

/**
 * Created on 10/9/17.
 */
public class TicketServiceRunner {

    private TicketService ticketService;
    private Scanner console;

    public TicketServiceRunner(int numberOfRows, int seatsPerRow) {
        console = new Scanner(System.in);
        ticketService = new VenueTicketService(numberOfRows, seatsPerRow);
    }

    public void start() {

        handleMainMenu();
    }

    public void handleMainMenu() {
        showMainMenu();
        String input = console.next();

        switch (input) {
            case "1" : {
                placeSeatHold();
                break;
            }
            case "2" : {
                reserveSeats();
                break;
            }
            case "3" : {
                lookupReservation();
                break;
            }
            case "4" : {
                viewSeatingChart();
                break;
            }
            case "5" : {
                exit();
                break;
            }
            default: {
                showMainMenu();
            }
        }

    }

    private void reserveSeats() {
        System.out.println("Welcome back, looks like you're ready to complete your order");
        System.out.println("Please enter your order id");
        int orderId = console.nextInt();
        System.out.println("Please enter your email address");
        String email = console.next();
        try {
            String confirmation = ticketService.reserveSeats(orderId, email);
            System.out.println(String.format("Thank you %s! Your order confirmation is %s",
                    email, confirmation));
        } catch(HoldNotFoundException e) {
            System.out.println("I'm sorry, we weren't able to find your order. It either expired, or you have a typo");
        }
        System.out.println();
        System.out.println();
        handleMainMenu();
    }

    private void lookupReservation() {
        System.out.println("Please enter your confirmation number");
        String confirmationNumber = console.next();
        try {
            //note, i wouldn't normally do this. I wanted to demonstrate the given interface
            Reservation reservation = ((VenueTicketService) ticketService).lookupConfirmation(confirmationNumber);
            System.out.println("We found your reservation!");
            System.out.println(reservation);
        } catch(ReservationNotFoundException e) {
            System.out.println("Unable to find confirmation: "+confirmationNumber);
        }
        System.out.println();
        System.out.println();
        handleMainMenu();
    }

    private void viewSeatingChart() {
        System.out.println();
        System.out.println();
        //note, i wouldn't normally do this. I wanted to demonstrate the given interface
        ((VenueTicketService)ticketService).printSeatMap();
        System.out.println();
        System.out.println();
        handleMainMenu();
    }

    private void exit() {
        System.exit(0);
    }

    private void placeSeatHold() {
        System.out.println("We aim to get you the best seats!");
        System.out.println("How many would you like?");
        int numberOfSeats = console.nextInt();
        System.out.println("What is your email address?");
        String email = console.next();

        try {
            SeatHold hold = ticketService.findAndHoldSeats(numberOfSeats, email);
            System.out.println(String.format("Thank you %s. Your seats will be held under order, %d",
                    hold.getCustomerEmail(), hold.getId()));
            System.out.println("Note: seats will only be held for 30 seconds");
        } catch(NoSeatsAvailableException e) {
            System.out.println("I'm sorry, there are no more blocks of seats for you, please try a smaller seat block");
        }
        System.out.println();
        System.out.println();
        handleMainMenu();
    }

    private void showMainMenu() {
        System.out.println("Welcome to the Ticket Service");
        System.out.println("Press (1) to place a seat hold");
        System.out.println("Press (2) to reserve your seats");
        System.out.println("Press (3) to lookup your reservation");
        System.out.println("Press (4) to view a seating chart of the venue");
        System.out.println("Press (5) to exit");
    }
}
