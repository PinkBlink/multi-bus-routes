package org.multi.routes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Passenger extends BaseEntity {
    private String name;
    private BusStop destination;
    private BusStop currentStop;
    private List<BusStop> transitStops = new ArrayList<>();
    private boolean isArrivedAtDestination;

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<BusStop> getTransitStops() {
        return transitStops;
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

    public void setTransitStops(List<BusStop> transitStops) {
        this.transitStops = transitStops;
    }

    public void setCurrentStop(BusStop currentStop) {
        this.currentStop = currentStop;
    }

    public void setDestination(BusStop destination) {
        this.destination = destination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArrivedAtDestination(boolean isArrivedAtDestination) {
        this.isArrivedAtDestination = isArrivedAtDestination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Passenger passenger)) {
            return false;
        }
        return Objects.equals(name, passenger.name)
                && Objects.equals(destination, passenger.destination);
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