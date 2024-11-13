package org.multi.routes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Bus {
    private final int number;
    private final int maximumPassengerCapacity;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final BusRoute route;
    private List<Passenger> passengers;

    private BusStop currentStop;
    private boolean isStop;

    public Bus(int number, BusRoute route, int maximumPassengerCapacity , BusStop currentStop) {
        this.number = number;
        this.route = route;
        this.currentStop = currentStop;
        this.maximumPassengerCapacity = maximumPassengerCapacity;
        passengers = new ArrayList<>(maximumPassengerCapacity);
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

    public Lock getLock() {
        return lock;
    }

    public Condition getCondition() {
        return condition;
    }

    public BusRoute getRoute() {
        return route;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public void addPassengerToBus(Passenger passenger) {
        passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bus bus)) {
            return false;
        }
        return number == bus.number && maximumPassengerCapacity == bus.maximumPassengerCapacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, maximumPassengerCapacity);
    }

    @Override
    public String toString() {
        return "Bus #" + number;
    }
}