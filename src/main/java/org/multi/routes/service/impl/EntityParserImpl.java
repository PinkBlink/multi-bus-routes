package org.multi.routes.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.exception.IllegalStringException;
import org.multi.routes.exception.NoFileException;
import org.multi.routes.service.EntityParser;
import org.multi.routes.ulils.DataFileReader;
import org.multi.routes.ulils.Validator;

import java.util.*;

import static org.multi.routes.ulils.constans.TextConstants.*;

public class EntityParserImpl implements EntityParser {
    private final Logger logger = LogManager.getLogger(EntityParserImpl.class);

    public Map<Passenger, List<String>> getPassengersFromData() {
        Map<Passenger, List<String>> passengerMap = new HashMap<>();
        try {
            List<String> passengerStringList = DataFileReader.getPassengersList();
            for (String passenger : passengerStringList) {
                Map.Entry<Passenger, List<String>> passengerEntry = getPassengerFromString(passenger);
                Passenger passengerKey = passengerEntry.getKey();
                List<String> passengerValue = passengerEntry.getValue();
                passengerMap.put(passengerKey, passengerValue);
            }
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return passengerMap;
    }

    private String getCleanString(String string, String... regex) {
        for (int i = 0; i < regex.length; i++) {
            if (i == 0) {
                string = string.replace(regex[i], "");
            } else {
                string = string.replace(regex[i], SEPARATOR);
            }
        }
        return string;
    }

    private Map.Entry<Passenger, List<String>> getPassengerFromString(String passengerString) {
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

    private Bus getBusFromString(String busString) {
        if (!Validator.isValidBusInput(busString)) {
            throw new IllegalStringException(busString + "string does not match Bus;");
        }
        String[] separatedBusString = getCleanString(busString, BUS_NUMBER_STRING, MAX_PASSENGERS_CAPACITY_STRING)
                .split(SEPARATOR);
        if (Validator.isValidNumberString(separatedBusString[0])
                && Validator.isValidNumberString(separatedBusString[1])) {
            int number = Integer.parseInt(separatedBusString[0]);
            int maxPassengerCapacity = Integer.parseInt(separatedBusString[1]);
            return new Bus(number, maxPassengerCapacity, 6, new HashSet<>());
        } else {
            throw new IllegalStringException(separatedBusString[0] + " or "
                    + separatedBusString[1] + "not a valid number");
        }
    }

    private BusStop getBusStopFromString(String busStopString) {
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

    @Override
    public List<Bus> getBuses(List<String> busesString) {
        List<Bus> busStops = new ArrayList<>();
        for (String busString : busesString) {
            Bus busStop = getBusFromString(busString);
            busStops.add(busStop);
        }
        return busStops;
    }

    @Override
    public List<BusStop> getBusStops(List<String> busStopsString) {
        List<BusStop> busStops = new ArrayList<>();
        for (String busStopString : busStopsString) {
            BusStop busStop = getBusStopFromString(busStopString);
            busStops.add(busStop);
        }
        return busStops;
    }

    @Override
    public List<Passenger> getPassengers(List<String> passengersString) {
        return null;
    }
}