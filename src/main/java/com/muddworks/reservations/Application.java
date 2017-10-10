package com.muddworks.reservations;

import com.muddworks.reservations.client.TicketServiceRunner;

/**
 *
 * Created on 10/9/17.
 */
public class Application {

    public static void main(String[] args) throws Exception {

        TicketServiceRunner runner = new TicketServiceRunner(10, 8);
        runner.start();

    }

}
