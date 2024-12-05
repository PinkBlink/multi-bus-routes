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
    private final Logger logger = LogManager.getLogger(this);
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final String stopName;
    private final int maxBusesCapacity;
    private final Set<Bus> stoppedBuses;

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

    public void addBusToStop(Bus bus) {
        lock.lock();
        try {
            while (Validator.isStopFull(this)) {
                condition.await();
            }
            stoppedBuses.add(bus);
            logger.log(INFO, bus + " arrived at the stop " + this);
            logger.log(INFO, this + " stopped buses : " + stoppedBuses);
            condition.signalAll();
        } catch (InterruptedException e) {
            logger.log(ERROR, e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public void removeBusFromStop(Bus bus) {
        lock.lock();
        try {
            stoppedBuses.remove(bus);
            logger.log(INFO, bus + " was removed from " + this);
            logger.log(INFO, this + " stopped buses : " + stoppedBuses);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void addPassengerToLine(Passenger passenger) {
        lock.lock();
        try {
            passengerLine.add(passenger);
            logger.log(INFO, passenger + " added to " + this);
        } finally {
            lock.unlock();
        }
    }

    public void removePassengerFromLine(Passenger passenger) {
        lock.lock();
        try {
            passengerLine.remove(passenger);
            logger.log(INFO, passenger + " was removed from " + this);
        } finally {
            lock.unlock();
        }
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