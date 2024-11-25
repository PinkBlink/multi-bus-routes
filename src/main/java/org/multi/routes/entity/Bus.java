package org.multi.routes.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.ulils.Validator;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.apache.logging.log4j.Level.INFO;


public class Bus implements Callable<String> {
    private final Logger logger = LogManager.getLogger(this);
    private final int number;
    private int iterationCounter = 3;
    private final int maximumPassengerCapacity;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private BusRoute route;
    private final Set<Passenger> passengers;
    private BusStop currentStop;

    private int index;

    public Bus(int number, int maximumPassengerCapacity) {
        this.number = number;
        this.maximumPassengerCapacity = maximumPassengerCapacity;
        passengers = new HashSet<>(maximumPassengerCapacity);
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

    public Condition getCondition() {
        return condition;
    }

    public BusRoute getRoute() {
        return route;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
        index = 0;
        currentStop = route.getStops().get(index);
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
                if (Validator.hasTransitStops(passenger)
                 && passenger.getTransitStops().getFirst().equals(currentStop)) {
                    movePassengerToStop(passenger);
                }
                if (passenger.getDestination().equals(currentStop)) {
                    movePassengerToStop(passenger);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private void movePassengerToStop(Passenger passenger) {
        removePassenger(passenger);
        logger.log(INFO, passenger + " removed from " + this + " to " + currentStop);
        currentStop.addPassengerToLine(passenger);
        passenger.setCurrentStop(currentStop);
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
                        && Validator.isDesireBus(passenger, this)
                        && !passenger.isArrivedAtDestination()) {
                    currentStop.getLock().lock();
                    try {
                        if (currentStop.getPassengerLine().contains(passenger)) {
                            currentStop.removePassengerFromLine(passenger);
                            addPassengerToBus(passenger);
                            logger.log(INFO, passenger + " added to " + this);
                        }
                    } finally {
                        currentStop.getLock().unlock();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String call() throws InterruptedException {
        while (iterationCounter > 0) {
            while (index < route.getStops().size()) {
                stop();
                disembarkationPassengers();
                TimeUnit.SECONDS.sleep(2);
                boardingPassengers();
                TimeUnit.SECONDS.sleep(2);
                ride();
            }
            iterationCounter--;
            index = 0;
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