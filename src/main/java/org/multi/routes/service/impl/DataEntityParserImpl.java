package org.multi.routes.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.exception.IllegalStringException;
import org.multi.routes.exception.NoFileException;
import org.multi.routes.service.BusStopService;
import org.multi.routes.service.DataEntityParser;
import org.multi.routes.service.Navigator;
import org.multi.routes.ulils.DataFileReader;
import org.multi.routes.ulils.LogisticUtils;
import org.multi.routes.ulils.Validator;

import java.util.*;

import static org.multi.routes.ulils.constans.TextConstants.*;

public class DataEntityParserImpl implements DataEntityParser {

    private final Logger logger = LogManager.getLogger(DataEntityParserImpl.class);

    @Override
    public List<BusStop> getBusStopsFromData() {
        List<BusStop> result = new ArrayList<>();
        try {
            List<String> stringBusStops = DataFileReader.getBusStopsList();
            for (String busString : stringBusStops) {
                BusStop busStop = getBusStopFromString(busString);
                result.add(busStop);
            }
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return result;
    }

    @Override
    public List<BusRoute> getBusRoutesFromData(List<BusStop> busStops) {
        List<BusRoute> busRoutes = new ArrayList<>();
        try {
            List<String> stringBusRoutes = DataFileReader.getBusRoutesList();
            for (String routeString : stringBusRoutes) {
                BusRoute busRoute = getBusRouteFromString(routeString, busStops);
                busRoutes.add(busRoute);
            }
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return busRoutes;
    }

    @Override
    public List<Passenger> getPassengersFromData(List<BusStop> stops, List<BusRoute> routes) {
        List<Passenger> passengers = new ArrayList<>();
        try {
            List<String> passengerStringList = DataFileReader.getPassengersList();
            for (String passengerString : passengerStringList) {
                Passenger passenger = getPassengerFromString(passengerString, stops);
                passengers.add(passenger);

            }
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        passengers = addTransitStopsToPassengers(passengers, routes);
        return addPassengersToStop(passengers);
    }

    @Override
    public List<Bus> getBusesFromData(List<BusRoute> routes) {
        List<Bus> buses = new ArrayList<>();
        try {
            List<String> busesString = DataFileReader.getBusesList();
            for (String busString : busesString) {
                Bus bus = getBusFromString(busString, routes);
                buses.add(bus);
            }
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return buses;
    }

    private Passenger getPassengerFromString(String passengerString, List<BusStop> stops) {
        String[] separatedString = getCleanString(passengerString
                , PASSENGER_NAME_STRING
                , PASSENGER_CURRENT_STOP_STRING
                , PASSENGER_DESTINATION_STRING)
                .split(SEPARATOR);
        String name = separatedString[0];
        String currentStopName = separatedString[1];
        String destinationStopName = separatedString[2];
        Passenger passenger = new Passenger(name);
        passenger = setCurrentStopAndDestination(passenger, currentStopName, destinationStopName, stops);
        return passenger;
    }


    private BusRoute getBusRouteFromString(String busRouteString, List<BusStop> allStops) {
        if (!Validator.isValidBusRouteInput(busRouteString)) {
            throw new IllegalStringException(busRouteString);
        }
        String[] separatedString = getCleanString(busRouteString
                , BUS_ROUTE_NUMBER_STRING
                , BUS_ROUTE_STOPS)
                .split(SEPARATOR);
        String[] stopsString = separatedString[1].split(", ");
        List<BusStop> routeStops = LogisticUtils.findBusesByNames(allStops, stopsString);
        int routeNumber = Integer.parseInt(separatedString[0]);
        return new BusRoute(routeNumber, routeStops, new HashMap<>());
    }

    private Bus getBusFromString(String busString, List<BusRoute> routes) {
        if (!Validator.isValidBusInput(busString)) {
            throw new IllegalStringException(busString + "  does not match Bus;");
        }
        String[] separatedBusString = getCleanString(busString
                , BUS_NUMBER_STRING
                , MAX_PASSENGERS_CAPACITY_STRING
                , SPACE + BUS_ROUTE_NUMBER_STRING)
                .split(SEPARATOR);
        int busNumber = Integer.parseInt(separatedBusString[0]);
        int maxPassengerCapacity = Integer.parseInt(separatedBusString[1]);
        int routeNumber = Integer.parseInt(separatedBusString[2]);
        BusRoute route = LogisticUtils.findRouteByNumber(routeNumber, routes);
        Bus bus = new Bus(busNumber, maxPassengerCapacity, 6, new HashSet<>());

        bus.setRoute(route);
        return bus;
    }

    private BusStop getBusStopFromString(String busStopString) {
        if (!Validator.isValidBusStopInput(busStopString)) {
            throw new IllegalStringException(busStopString + " does not much BusStop;");
        }
        String[] separatedBusStopString = getCleanString(busStopString
                , BUS_STOP_NAME_STRING
                , MAX_BUSES_CAPACITY_STRING)
                .split(SEPARATOR);
        String busStopName = separatedBusStopString[0];
        if (Validator.isValidNumberString(separatedBusStopString[1])) {
            int maxBusesCapacity = Integer.parseInt(separatedBusStopString[1]);
            return new BusStop(busStopName, maxBusesCapacity);
        } else {
            throw new IllegalStringException(separatedBusStopString[1] + " is not a number");
        }
    }

    private String getCleanString(String string, String... regex) {
        for (int i = 0; i < regex.length; i++) {
            if (i == 0) {
                string = string.replace(regex[i], EMPTY_STRING);
            } else {
                string = string.replace(regex[i], SEPARATOR);
            }
        }
        return string;
    }

    private Passenger setCurrentStopAndDestination(Passenger passenger, String currentStopName
            , String destinationStopName, List<BusStop> stops) {
        BusStop currentBusStop = LogisticUtils.findBusStopByName(currentStopName, stops);
        BusStop destinationStop = LogisticUtils.findBusStopByName(destinationStopName, stops);
        passenger.setCurrentStop(currentBusStop);
        passenger.setDestination(destinationStop);
        return passenger;
    }

    private List<Passenger> addTransitStopsToPassengers(List<Passenger> passengers, List<BusRoute> routes) {
        Navigator navigator = new NavigatorImpl(routes);
        for (Passenger passenger : passengers) {
            List<BusStop> transitStops = navigator.getTransitStops(passenger);
            passenger.setTransitStops(transitStops);
        }
        return passengers;
    }

    private List<Passenger> addPassengersToStop(List<Passenger> passengers) {
        BusStopService busStopService = new BusStopServiceImpl();
        for (Passenger passenger : passengers) {
            busStopService.addPassengerToLine(passenger.getCurrentStop(), passenger);
        }
        return passengers;
    }
}