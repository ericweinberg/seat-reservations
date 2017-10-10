# Usage

There's a CLI application that can be used to interact with the application. To run it, first you need to build the jar file via: 

#### Running The Application
*nix
``` 
./gradlew build
```
windows (untested)
```
gradlew.bah build
```

Then you can run via the sh script 

```
sh ./run.sh
```

or manually via java

```
java -jar build/libs/seat-reservations.jar
```

#### Interacting with the Application

The application is (hopefully) self explanatory. There is no error handling in the CLI, so be gentle. Some additional features outside of the problem statement have been added to help facilitate testing.    

#### Running Tests
*nix
```
./gradle test
```

windows (untested)
```
gradlew.bat test
```
#### Building

*nix
```
./gradle build
```

windows (untested)
```
gradlew build
```

# Assumptions

* Venues are considered to be rectangle venues. Every row has the same number of seats
* The 'best seat' algorithm is very simple. 
    * Rows closest to the stage, row 0 being the first, are preferred
    * requested seats will not be broken up across rows. 
    * within a row, seats are filled started from seat 0. There is no effort to get as close to the stage and then radiate out towards the edges of the stage
* Seats are held for 30 seconds, and then they become available again 

# Technologies Used

* gradle 
* java

# Problem Statement

Implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance
venue.


Your homework assignment is to design and write a Ticket Service that provides the
following functions:
* Find the number of seats available within the venue
Note: available seats are seats that are neither held nor reserved.
* Find and hold the best available seats on behalf of a customer
Note: each ticket hold should expire within a set number of seconds.
* Reserve and commit a specific group of held seats for a customer

