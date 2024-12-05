package org.multi.routes.ulils;

import org.multi.routes.ulils.constans.TextConstants;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;

import java.util.List;
import java.util.regex.Pattern;

public class Validator {
    public static boolean hasTransitStops(Passenger passenger) {
        return !passenger.getTransitStops().isEmpty();
    }

    public static boolean willGetDestination(Passenger passenger, Bus bus) {
        BusStop passengerDestination = passenger.getDestination();
        return bus.getRoute().containsStop(passengerDestination);
    }

    public static boolean willGetToTransitStop(Passenger passenger, Bus bus) {
        if (hasTransitStops(passenger)) {
            BusStop stop = passenger.getTransitStops().getFirst();
            return bus.getRoute().containsStop(stop);
        }
        return false;
    }

    public static boolean isDesireBus(Passenger passenger, Bus bus) {
        return willGetToTransitStop(passenger, bus)
                || willGetDestination(passenger, bus);
    }

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

    public static boolean isNeedTransition(Passenger passenger, List<BusRoute> routes) {
        BusStop current = passenger.getCurrentStop();
        BusStop destination = passenger.getDestination();
        return routes.stream().filter(r -> r.containsStop(current)
                && r.containsStop(destination)).toList().isEmpty();
    }
}