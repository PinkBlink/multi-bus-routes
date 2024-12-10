package org.multi.routes.model;

import org.multi.routes.ulils.IdGenerator;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BusStop {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int id;
    private String stopName;
    private int maxBusesCapacity;
    private Set<Bus> stoppedBuses;

    private final Set<Passenger> passengerLine = new HashSet<>();

    public BusStop(String stopName, int maxBusesCapacity) {
        this.stopName = stopName;
        this.maxBusesCapacity = maxBusesCapacity;
        this.stoppedBuses = new HashSet<>(maxBusesCapacity);
        this.id = IdGenerator.getNewId();
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

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
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