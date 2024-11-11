package org.multi.routes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Bus implements Callable {
    private final int number;
    private BusRoute route;
    private final int maximumPassengerCapacity;
    private List<Passenger> passengers;

    private BusStop currentStop;
    private BusStop destination;

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
        System.out.println(this + " going from " + currentStop + " to " + destination);
        currentStop = null;

    }

    @Override
    public Object call() throws Exception {

        List<BusStop> stops = route.getRoute();
        int startndex = 0;

        this.currentStop = stops.getFirst();
        this.destination = stops.getLast();

        while (currentStop != destination) {
            ride();
            TimeUnit.SECONDS.sleep(10);
            startndex++;
            this.currentStop = stops.get(startndex);

        }
        return true;
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
        return "Bus #" + number + "\npassengers: " + passengers + "\nmaximum passengers :" + maximumPassengerCapacity;
    }
}