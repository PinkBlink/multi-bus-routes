package org.multi.routes.model;

import org.multi.routes.service.BusState;
import org.multi.routes.service.impl.StopStateImpl;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bus extends BaseEntity implements Callable<String> {
    private final ReentrantLock lock = new ReentrantLock();
    private int number;
    private int maximumPassengerCapacity;
    private Set<Passenger> passengers;
    private BusRoute route;
    private BusStop currentStop;
    private BusState state;
    private int iterationCounter;
    private int stopIndex;

    public Bus(int number, int maximumPassengerCapacity, int iterationCounter, HashSet<Passenger> passengers) {
        this.number = number;
        this.maximumPassengerCapacity = maximumPassengerCapacity;
        this.iterationCounter = iterationCounter;
        this.passengers = passengers;
    }

    public Lock getLock() {
        return lock;
    }

    public int getStopIndex() {
        return stopIndex;
    }

    public int getMaximumPassengerCapacity() {
        return maximumPassengerCapacity;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public BusStop getCurrentStop() {
        return currentStop;
    }

    public BusRoute getRoute() {
        return route;
    }

    public void setMaximumPassengerCapacity(int maximumPassengerCapacity) {
        this.maximumPassengerCapacity = maximumPassengerCapacity;
    }

    public void setCurrentStop(BusStop currentStop) {
        this.currentStop = currentStop;
    }

    public void setStopIndex(int stopIndex) {
        this.stopIndex = stopIndex;
    }


    public void setRoute(BusRoute route) {
        this.route = route;
        stopIndex = 0;
        currentStop = route.getStops().get(stopIndex);
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String call() {
        if (state == null) {
            state = new StopStateImpl(this);
        }
        while (iterationCounter > 0) {
            while (stopIndex < route.getStops().size()) {
                state = state.doAction();
            }
            iterationCounter--;
            stopIndex = 0;
        }
        return this + " FINISHED THE ROUTE (LAPS LEFT : " + iterationCounter + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bus bus)) {
            return false;
        }
        return number == bus.number
                && maximumPassengerCapacity == bus.maximumPassengerCapacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, maximumPassengerCapacity);
    }

    @Override
    public String toString() {
        return "[BUS #" + number + " passengers in bus: " + passengers + "]";
    }
}