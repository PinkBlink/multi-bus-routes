package org.multi.routes.ulils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.multi.routes.exception.IllegalStringException;
import org.multi.routes.exception.NoFileException;

import java.util.ArrayList;
import java.util.List;

import static org.multi.routes.constans.TextConstants.*;

public class DataParser {
    private static final Logger logger = LogManager.getLogger(DataParser.class);
    private static final String SEPARATOR = "/";

    public static List<BusStop> getBusStopsFromData() {
        List<BusStop> busStops = new ArrayList<>();
        try {
            List<String> busStopStringFromData = DataFileReader.getBusStopsList();
            for (String busStopString : busStopStringFromData) {
                String[] separatedBusStop = getCleanString(busStopString, BUS_STOP_STRING, MAX_BUSES_CAPACITY_STRING)
                        .split(SEPARATOR);
                String busStopName = separatedBusStop[0];
                if (Validator.isValidNumberString(separatedBusStop[1])) {
                    int maxBusesCapacity = Integer.parseInt(separatedBusStop[1]);
                    busStops.add(new BusStop(busStopName, maxBusesCapacity));
                } else {
                    throw new IllegalStringException(separatedBusStop[1] + " is not a number");
                }
            }
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return busStops;
    }

    public static List<Bus> getBusesFromData() {
        return null;
    }

    public static List<Passenger> getPassengersFromData() {
        List<Passenger> passengerList = new ArrayList<>();
        try {
            List<String> passengerStringList = DataFileReader.getPassengersList();
            for (String passenger : passengerStringList) {

                passengerList.add(getPassengerFromString(passenger));
            }
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return passengerList;
    }

    private static String getCleanString(String string, String regex) {
        return string.replace(regex, "");
    }

    private static String getCleanString(String string, String regexFirst, String regexSecond) {
        return string.replace(regexFirst, "").replace(regexSecond, SEPARATOR);
    }

    private static Passenger getPassengerFromString(String passenger) {
        if (Validator.isValidPassengerInput(passenger)) {
            String name = getCleanString(passenger, PASSENGER_BEGIN_STRING);
            return new Passenger(name);
        } else {
            throw new IllegalStringException("Wrong string :" + passenger);
        }
    }

    public static void main(String[] args) {
        System.out.println(getPassengersFromData());
    }
}
