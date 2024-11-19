package org.multi.routes.ulils;

import org.multi.routes.constans.TextConstants;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.List;
import java.util.regex.Pattern;

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
}