package org.multi.routes.entity;

import java.util.Objects;

public class Passenger {
    private final String name;
    private final BusStop destination;
    private Bus currentBus;

    public Passenger(String name, BusStop destination) {
        this.name = name;
        this.destination = destination;
    }

    public BusStop getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Passenger passenger)) {
            return false;
        }
        return Objects.equals(name, passenger.name) && Objects.equals(destination, passenger.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, destination);
    }

    @Override
    public String toString() {
        return "Passenger:" +
                "name: " + name +
                "\ncurrentBus: " + currentBus +
                "\ndestination: " + destination;
    }
}