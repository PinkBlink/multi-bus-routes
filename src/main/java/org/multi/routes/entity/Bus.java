package org.multi.routes.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.ulils.Validator;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.logging.log4j.Level.INFO;


public class Bus implements Callable<String> {
    private final Logger logger = LogManager.getLogger(this);
    private final Lock lock = new ReentrantLock();
    private final int number;
    private final int maximumPassengerCapacity;
    private final Set<Passenger> passengers;
    private BusRoute route;
    private BusStop currentStop;
    private BusState state;
    private int iterationCounter = 0;
    private int stopIndex = 0;

    public Bus(int number, int maximumPassengerCapacity) {
        this.number = number;
        this.maximumPassengerCapacity = maximumPassengerCapacity;
        passengers = new HashSet<>(maximumPassengerCapacity);

    }

    public int getStopIndex() {
        return stopIndex;
    }

    public void setStopIndex(int stopIndex) {
        this.stopIndex = stopIndex;
    }

    public Logger getLogger() {
        return logger;
    }

    public Lock getLock() {
        return lock;
    }

    public int getMaximumPassengerCapacity() {
        return maximumPassengerCapacity;
    }

    public BusStop getCurrentStop() {
        return currentStop;
    }

    public void setCurrentStop(BusStop currentStop) {
        this.currentStop = currentStop;
    }

    public BusRoute getRoute() {
        return route;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
        stopIndex = 0;
        currentStop = route.getStops().get(stopIndex);
    }

    public void addPassengerToBus(Passenger passenger) {
        if (!Validator.isBusFull(this)) {
            passengers.add(passenger);
        } else {
            logger.log(INFO, this + " is full. Passengers :" + passengers);
        }
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
    }

    @Override
    public String call() {
        if (state == null) {
            state = new StopState(this);
        }
        while (iterationCounter < 6) {
            while (stopIndex < route.getStops().size()) {
                state = state.act();
            }
            iterationCounter++;
            stopIndex = 0;
        }
        return this + " FINISHED THE ROUTE (LAPS COMPLETED : " + iterationCounter + ")";
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