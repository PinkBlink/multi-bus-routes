package org.multi.routes.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Passenger {
    private final Logger logger = LogManager.getLogger(this);
    private final String name;
    private BusStop destination;
    private BusStop currentStop;
    private List<BusStop> transitStops = new ArrayList<>();
    private Bus currentBus;
    private boolean isArrivedAtDestination;

    public Passenger(String name) {
        this.name = name;
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

    public Bus getCurrentBus() {
        return currentBus;
    }

    public boolean isArrivedAtDestination() {
        return isArrivedAtDestination;
    }

    public void setTransitStops(List<BusStop> transitStops) {
        this.transitStops = transitStops;
    }

    public void setCurrentStop(BusStop currentStop) {
        this.currentStop = currentStop;
        transitStops.remove(currentStop);
        isArrivedAtDestination = currentStop.equals(destination);
        if (isArrivedAtDestination) {
            logger.log(Level.INFO, this + " ARRIVED TO DESTINATION");
        }
    }

    public void setDestination(BusStop destination) {
        this.destination = destination;
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