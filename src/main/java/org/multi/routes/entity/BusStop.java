package org.multi.routes.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BusStop {
    private final Logger logger = LogManager.getLogger(this);
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final String stopName;
    private final int maxBussesCapacity;
    private final List<Bus>  stoppedBuses;

    private List<Passenger> passengerLine = new ArrayList<>();

    public BusStop(String stopName, int maxBussesCapacity) {
        this.stopName = stopName;
        this.maxBussesCapacity = maxBussesCapacity;
        this.stoppedBuses = new ArrayList<>(maxBussesCapacity);
    }

    public void takeBus(Bus bus) {
        try {
            lock.lock();
            while (stoppedBuses.size() == maxBussesCapacity) {
                logger.log(Level.INFO, "There is not enough space for the bus," + this + " the departure of those already stopped is awaiting");
                condition.await();
            }
            stoppedBuses.add(bus);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }

    public void sendBus(Bus bus) {
        try {
            lock.lock();
            stoppedBuses.remove(bus);
        } finally {
            condition.signalAll();
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
