package org.multi.routes.ulils;

import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.List;

public class Validator {
    public static boolean willGetDestination(Passenger passenger, Bus bus) {
        BusStop passengerDestination = passenger.getDestination();
        List<BusStop> stops = bus.getRoute().getStops();
        for (BusStop stop : stops) {
            if (passengerDestination.equals(stop)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStopFull(BusStop busStop) {
        int amountStoppedBusses = busStop.getStoppedBuses().size();
        int maxBussesCapacity = busStop.getMaxBussesCapacity();
        return amountStoppedBusses == maxBussesCapacity;
    }
}