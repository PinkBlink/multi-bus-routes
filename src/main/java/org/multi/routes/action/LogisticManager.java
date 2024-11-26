package org.multi.routes.action;

import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.ulils.DataParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticManager {
    //singleton
    private static final Lock lock = new ReentrantLock();
    private static LogisticManager instance;
    private final List<BusRoute> routes = new ArrayList<>();
    private List<BusStop> stops;
    private List<Bus> buses;

    private LogisticManager() {
        setUp();
    }

    public static LogisticManager getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new LogisticManager();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public List<BusRoute> getRoutes() {
        return routes;
    }

    private void setUp() {
        stops = DataParser.getBusStopsFromData();
        buses = DataParser.getBusesFromData();
        buildRoutes();
        buildBuses();
    }

    private List<BusStop> getMergedStops(BusStop... busStops) {
        return new ArrayList<>(Arrays.asList(busStops));
    }

    private void buildRoutes() {
        List<BusStop> stopsForRoute1 = getMergedStops(stops.get(0), stops.get(1), stops.get(2), stops.get(3)
                , stops.get(4), stops.get(5));
        List<BusStop> stopsForRoute2 = getMergedStops(stops.get(6), stops.get(2), stops.get(3), stops.get(7));
        List<BusStop> stopsForRoute3 = getMergedStops(stops.get(7), stops.get(3), stops.get(4), stops.get(8)
                , stops.get(9));
        routes.add(new BusRoute(1, stopsForRoute1));
        routes.add(new BusRoute(2, stopsForRoute2));
        routes.add(new BusRoute(3, stopsForRoute3));
    }

    private void buildBuses() {
        buses.get(0).setRoute(routes.get(0));
        buses.get(1).setRoute(routes.get(0));
        buses.get(2).setRoute(routes.get(1));
        buses.get(3).setRoute(routes.get(1));
        buses.get(4).setRoute(routes.get(2));
    }
}