#Usage

####Running Tests
*nix
```
./gradle test
```

windows (untested)
```
gradlew.bat test
```
####Building

*nix
```
./gradle build
```

windows (untested)
```
gradlew build
```

#Assumptions

* Venues are considered to be rectangle venues. Every row has the same number of seats


#Technologies Used

* gradle 
* java

#Problem Statement

Implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance
venue.


Your homework assignment is to design and write a Ticket Service that provides the
following functions:
* Find the number of seats available within the venue
Note: available seats are seats that are neither held nor reserved.
* Find and hold the best available seats on behalf of a customer
Note: each ticket hold should expire within a set number of seconds.
* Reserve and commit a specific group of held seats for a customer

