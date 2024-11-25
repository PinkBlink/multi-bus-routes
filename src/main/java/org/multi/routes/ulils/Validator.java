package org.multi.routes.ulils;

import org.apache.logging.log4j.core.appender.routing.Routes;
import org.multi.routes.constans.TextConstants;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.List;
import java.util.regex.Pattern;

public class Validator {
    public static boolean hasTransitStops(Passenger passenger) {
        return !passenger.getTransitStops().isEmpty();
    }

    public static boolean willGetDestination(Passenger passenger, Bus bus) {
        BusStop passengerDestination = passenger.getDestination();
        return bus.getRoute().contain(passengerDestination);
    }

    public static boolean willGetToTransitStop(Passenger passenger, Bus bus) {
        if(hasTransitStops(passenger)) {
            BusStop stop = passenger.getTransitStops().getFirst();
            return bus.getRoute().contain(stop);
        }
        return false;
    }

    public static boolean isDesireBus(Passenger passenger, Bus bus) {
        return willGetToTransitStop(passenger, bus)
                || willGetDestination(passenger, bus);
    }

    public static int getTheIndexOfTheDesiredBus(Passenger passenger, List<Bus> buses) {
        for (int i = 0; i < buses.size(); i++) {
            Bus currentBus = buses.get(i);
            if (willGetDestination(passenger, currentBus)) {
                return i;
            }
        }
        return -1;
    }//mb delete

    public static boolean isValidBusInput(String input) {
        return Pattern.matches(TextConstants.BUS_REGEX, input);
    }

    public static boolean isValidBusStopInput(String input) {
        return Pattern.matches(TextConstants.BUS_STOP_REGEX, input);
    }

    public static boolean isValidPassengerInput(String input) {
        return Pattern.matches(TextConstants.PASSENGER_REGEX, input);
    }

    public static boolean isBusFull(Bus bus) {
        return bus.getPassengers().size() == bus.getMaximumPassengerCapacity();
    }

    public static boolean isStopFull(BusStop busStop) {
        int amountStoppedBuses = busStop.getStoppedBuses().size();
        int maxBusesCapacity = busStop.getMaxBusesCapacity();
        return amountStoppedBuses == maxBusesCapacity;
    }

    public static boolean isValidNumberString(String string) {
        for (int i = 0; i < string.length(); i++) {
            char currentChar = string.charAt(i);
            if (!Character.isDigit(currentChar)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCurrentStopOnOneRouteWithDestination(Passenger passenger, List<BusRoute> busRoutes) {
        BusStop current = passenger.getCurrentStop();
        BusStop destination = passenger.getDestination();
        for (BusRoute route : busRoutes) {
            if (route.getStops().contains(current)
                    && route.getStops().contains(destination)) {
                return true;
            }
        }
        return false;
    }
}