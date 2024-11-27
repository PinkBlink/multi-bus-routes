package org.multi.routes.ulils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.multi.routes.exception.IllegalStringException;
import org.multi.routes.exception.NoFileException;

import java.util.*;

import static org.multi.routes.constans.TextConstants.*;

public class DataParser {
    private static final Logger logger = LogManager.getLogger(DataParser.class);
    private static final String SEPARATOR = "/";

    public static List<BusStop> getBusStopsFromData() {
        List<BusStop> busStops = new ArrayList<>();
        try {
            List<String> busStopStringFromData = DataFileReader.getBusStopsList();
            for (String busStopString : busStopStringFromData) {
                BusStop busStop = getBusStopFromString(busStopString);
                busStops.add(busStop);
            }
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return busStops;
    }

    public static List<Bus> getBusesFromData() {
        List<Bus> busesList = new ArrayList<>();
        try {
            List<String> busesStringList = DataFileReader.getBusesList();
            for (String busString : busesStringList) {
                Bus bus = getBusFromString(busString);
                busesList.add(bus);
            }
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return busesList;
    }

    public static Map<Passenger, List<String>> getPassengersFromData() {
        Map<Passenger, List<String>> passengerMap = new HashMap<>();
        try {
            List<String> passengerStringList = DataFileReader.getPassengersList();
            for (String passenger : passengerStringList) {
                Map.Entry<Passenger,List<String>> passengerEntry = getPassengerFromString(passenger);
                Passenger passengerKey = passengerEntry.getKey();
                List<String> passengerValue = passengerEntry.getValue();
                passengerMap.put(passengerKey,passengerValue);
            }
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return passengerMap;
    }

    private static String getCleanString(String string, String... regex) {
        for (int i = 0; i < regex.length; i++) {
            if (i == 0) {
                string = string.replace(regex[i], "");
            } else {
                string = string.replace(regex[i], SEPARATOR);
            }
        }
        return string;
    }

    public static void main(String[] args) {
        System.out.println(getCleanString("bus_stop: 1 max_buses: 2", BUS_STOP_STRING, MAX_BUSES_CAPACITY_STRING));
    }

    private static Map.Entry<Passenger, List<String>> getPassengerFromString(String passengerString) {
        if (Validator.isValidPassengerInput(passengerString)) {
            String[] passengerInfo = getCleanString(passengerString, PASSENGER_BEGIN_STRING
                    , PASSENGER_CURRENT_STOP_STRING
                    , PASSENGER_DESTINATION_STRING)
                    .split(SEPARATOR);
            Passenger passenger = new Passenger(passengerInfo[0]);
            List<String> fromToDestination = new ArrayList<>(Arrays.asList(passengerInfo[1], passengerInfo[2]));
            return new AbstractMap.SimpleEntry<>(passenger, fromToDestination);
        } else {
            throw new IllegalStringException("Wrong string :" + passengerString);
        }
    }

    private static Bus getBusFromString(String busString) {
        if (!Validator.isValidBusInput(busString)) {
            throw new IllegalStringException(busString + "string does not match Bus;");
        }
        String[] separatedBusString = getCleanString(busString, BUS_NUMBER_STRING, MAX_PASSENGERS_CAPACITY_STRING)
                .split(SEPARATOR);
        if (Validator.isValidNumberString(separatedBusString[0])
                && Validator.isValidNumberString(separatedBusString[1])) {
            int number = Integer.parseInt(separatedBusString[0]);
            int maxPassengerCapacity = Integer.parseInt(separatedBusString[1]);
            return new Bus(number, maxPassengerCapacity);
        } else {
            throw new IllegalStringException(separatedBusString[0] + " or "
                    + separatedBusString[1] + "not a valid number");
        }
    }

    private static BusStop getBusStopFromString(String busStopString) {
        if (!Validator.isValidBusStopInput(busStopString)) {
            throw new IllegalStringException(busStopString + " does not much BusStop;");
        }
        String[] separatedBusStopString = getCleanString(busStopString, BUS_STOP_STRING, MAX_BUSES_CAPACITY_STRING)
                .split(SEPARATOR);
        String busStopName = separatedBusStopString[0];
        if (Validator.isValidNumberString(separatedBusStopString[1])) {
            int maxBusesCapacity = Integer.parseInt(separatedBusStopString[1]);
            return new BusStop(busStopName, maxBusesCapacity);
        } else {
            throw new IllegalStringException(separatedBusStopString[1] + " is not a number");
        }
    }
}
