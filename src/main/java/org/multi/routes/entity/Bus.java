package org.multi.routes.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.ulils.Validator;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.logging.log4j.Level.INFO;


public class Bus implements Callable<String> {
    private final Logger logger = LogManager.getLogger(this);
    private final int number;
    private final int maximumPassengerCapacity;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final BusRoute route;
    private final Set<Passenger> passengers;
    private BusStop currentStop;

    private int index;

    public Bus(int number, BusRoute route, int maximumPassengerCapacity, BusStop currentStop) {
        this.number = number;
        this.route = route;
        this.currentStop = currentStop;
        this.maximumPassengerCapacity = maximumPassengerCapacity;
        passengers = new HashSet<>(maximumPassengerCapacity);
        index = route.getStops().indexOf(currentStop);
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

    public Set<Passenger> getPassengers() {
        return passengers;
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

    public void ride() {
        currentStop.removeBusFromStop(this);
        logger.log(INFO, this + " going from " + currentStop);
        index++;
    }

    public void stop() {
        currentStop = route.getStops().get(index);
        currentStop.addBusToStop(this);
    }

    public void disembarkationPassengers() {
        lock.lock();
        try {
            logger.log(INFO, this + " began disembarking passengers;");
            Set<Passenger> passengerCopy = new HashSet<>(passengers);
            for (Passenger passenger : passengerCopy) {
                if (passenger.getDestination().equals(currentStop)) {
                    removePassenger(passenger);
                    logger.log(INFO, passenger + " removed from " + this + " to " + currentStop);
                    currentStop.addPassengerToLine(passenger);
                    passenger.setCurrentStop(currentStop);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void boardingPassengers() {
        logger.log(INFO, this + " began boarding passengers;");
        Set<Passenger> passengersInLineCopy;
        currentStop.getLock().lock();
        try {
            passengersInLineCopy = new HashSet<>(currentStop.getPassengerLine());
        } finally {
            currentStop.getLock().unlock();
        }

        lock.lock();
        try {
            for (Passenger passenger : passengersInLineCopy) {
                if (!Validator.isBusFull(this)
                        && Validator.willGetDestination(passenger, this)
                        && !passenger.isArrivedAtDestination()) {
                    currentStop.getLock().lock();
                    try {
                        currentStop.removePassengerFromLine(passenger);
                    } finally {
                        currentStop.getLock().unlock();
                    }
                    addPassengerToBus(passenger);
                    logger.log(INFO, passenger + " added to " + this);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String call() {
        while (index < route.getStops().size()) {
            stop();
            disembarkationPassengers();
            boardingPassengers();
            ride();
        }
        return this + " FINISHED THE ROUTE (LAST STATION WAS : " + currentStop + ")";
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