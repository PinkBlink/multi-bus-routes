package org.multi.routes.entity;

import java.util.Objects;

public class Passenger {
    private final String name;
    private final BusStop destination;
    private BusStop currentStop;
    private Bus currentBus;
    private boolean isArrivedAtDestination;

    public Passenger(String name, BusStop currentStop, BusStop destination) {
        this.name = name;
        this.currentStop = currentStop;
        this.destination = destination;
        currentStop.addPassengerToLine(this);
    }

    public BusStop getDestination() {
        return destination;
    }

    public BusStop getCurrentStop() {
        return currentStop;
    }

    public boolean isArrivedAtDestination() {
        return isArrivedAtDestination;
    }

    public void setArrivedAtDestination(boolean arrivedAtDestination) {
        isArrivedAtDestination = arrivedAtDestination;
    }

    public void setCurrentStop(BusStop currentStop) {
        this.currentStop = currentStop;
        isArrivedAtDestination = currentStop.equals(destination);
    }

    public Bus getCurrentBus() {
        return currentBus;
    }

    public void setCurrentBus(Bus currentBus) {
        this.currentBus = currentBus;
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
        return "Passenger:" + name;
    }
}