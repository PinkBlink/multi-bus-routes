package org.multi.routes.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.ulils.Validator;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.INFO;

public class BusStop {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private String stopName;
    private int maxBusesCapacity;
    private Set<Bus> stoppedBuses;

    private final Set<Passenger> passengerLine = new HashSet<>();

    public BusStop(String stopName, int maxBusesCapacity) {
        this.stopName = stopName;
        this.maxBusesCapacity = maxBusesCapacity;
        this.stoppedBuses = new HashSet<>(maxBusesCapacity);
    }

    public Lock getLock() {
        return lock;
    }

    public Set<Bus> getStoppedBuses() {
        return stoppedBuses;
    }

    public Set<Passenger> getPassengerLine() {
        return passengerLine;
    }

    public int getMaxBusesCapacity() {
        return maxBusesCapacity;
    }

    public String getStopName() {
        return stopName;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public void setMaxBusesCapacity(int maxBusesCapacity) {
        this.maxBusesCapacity = maxBusesCapacity;
    }

    public void setStoppedBuses(Set<Bus> stoppedBuses) {
        this.stoppedBuses = stoppedBuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusStop busStop)) {
            return false;
        }
        return maxBusesCapacity == busStop.maxBusesCapacity
                && Objects.equals(stopName, busStop.stopName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stopName, maxBusesCapacity);
    }

    @Override
    public String toString() {
        return "[BUS_STOP:" + stopName + " passengers: " + passengerLine + "]";
    }
}