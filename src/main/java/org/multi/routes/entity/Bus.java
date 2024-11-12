package org.multi.routes.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


public class Bus implements Callable<String> {
    private final Logger logger = LogManager.getLogger(this);
    private final int number;
    private final int maximumPassengerCapacity;

    private BusRoute route;
    private List<Passenger> passengers;
    private BusStop currentStop;
    private BusStop destination;
    private int indexOfCurrentStop = 0;


    public BusStop getCurrentStop() {
        return currentStop;
    }

    public void setCurrentStop(BusStop currentStop) {
        this.currentStop = currentStop;
    }

    public BusStop getDestination() {
        return destination;
    }

    public void setDestination(BusStop destination) {
        this.destination = destination;
    }

    public Bus(int number, BusRoute route, int maximumPassengerCapacity) {
        this.number = number;
        this.route = route;
        this.maximumPassengerCapacity = maximumPassengerCapacity;
        passengers = new ArrayList<>(maximumPassengerCapacity);
    }

    public void ride() {
        logger.log(Level.INFO, this + " going from " + currentStop + " to " + destination);
        currentStop.sendBus(this);
        currentStop = null;
        indexOfCurrentStop++;
    }

    public void stop() {
        List<BusStop> stops = route.getRoute();
        currentStop = stops.get(indexOfCurrentStop);
        currentStop.takeBus(this);
        logger.log(Level.INFO, "The bus " + this + " stopped at " + currentStop);
    }

    @Override
    public String call() throws Exception {
        while (indexOfCurrentStop < route.getRoute().size()) {
            try {
                stop();
                TimeUnit.SECONDS.sleep(4);
                ride();
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
        }
        return "Bus " + this + " has completed its route";
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