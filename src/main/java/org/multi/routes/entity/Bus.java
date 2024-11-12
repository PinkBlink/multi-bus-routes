package org.multi.routes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Bus {
    private final int number;
    private final int maximumPassengerCapacity;
    private final BusRoute route;
    private List<Passenger> passengers;

    public Bus(int number, BusRoute route, int maximumPassengerCapacity) {
        this.number = number;
        this.route = route;
        this.maximumPassengerCapacity = maximumPassengerCapacity;
        passengers = new ArrayList<>(maximumPassengerCapacity);
    }

    public BusRoute getRoute() {
        return route;
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