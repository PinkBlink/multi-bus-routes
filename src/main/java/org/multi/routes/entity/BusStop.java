package org.multi.routes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BusStop {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private final String stopName;
    private final int maxBussesCapacity;
    private final List<Bus> stoppedBuses;

    private List<Passenger> passengerLine = new ArrayList<>();

    public BusStop(String stopName, int maxBussesCapacity) {
        this.stopName = stopName;
        this.maxBussesCapacity = maxBussesCapacity;
        this.stoppedBuses = new ArrayList<>(maxBussesCapacity);
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getCondition() {
        return condition;
    }

    public List<Bus> getStoppedBuses() {
        return stoppedBuses;
    }

    public void addBusToStop(Bus bus) {
        stoppedBuses.add(bus);
    }

    public void removeBusFromStop(Bus bus) {
        stoppedBuses.remove(bus);
    }

    public int getMaxBussesCapacity() {
        return maxBussesCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusStop busStop)) {
            return false;
        }
        return maxBussesCapacity == busStop.maxBussesCapacity
                && Objects.equals(stopName, busStop.stopName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stopName, maxBussesCapacity);
    }

    @Override
    public String toString() {
        return "BusStop:" + stopName;
    }
}