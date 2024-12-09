package org.multi.routes.service.impl;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.ulils.LogisticUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticManager {
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

    public List<BusStop> getStops() {
        return stops;
    }

    public BusStop getStop(String name) {
        return stops.stream().filter(s -> s.getStopName().equals(name)).toList().getFirst();
    }

    private void setUp() {
        stops = DataEntityParserImpl.getBusStopsFromData();
        buses = DataEntityParserImpl.getBusesFromData();
        buildRoutes();
        LogisticUtils.createMap(routes);
        buildBuses();
    }

    private void buildRoutes() {
        List<BusStop> stopsForRoute1 = LogisticUtils.getMergedStops(stops.get(0), stops.get(1), stops.get(2));
        List<BusStop> stopsForRoute2 = LogisticUtils.getMergedStops(stops.get(2), stops.get(4));
        List<BusStop> stopsForRoute3 = LogisticUtils.getMergedStops(stops.get(3), stops.get(4), stops.get(5));
        List<BusStop> stopsForRoute4 = LogisticUtils.getMergedStops(stops.get(5), stops.get(6), stops.get(7));
        List<BusStop> stopsForRoute5 = LogisticUtils.getMergedStops(stops.get(5), stops.get(8), stops.get(9));
        routes.add(new BusRoute(1, stopsForRoute1));
        routes.add(new BusRoute(2, stopsForRoute2));
        routes.add(new BusRoute(3, stopsForRoute3));
        routes.add(new BusRoute(4, stopsForRoute4));
        routes.add(new BusRoute(5, stopsForRoute5));
    }

    private void buildBuses() {
        buses.get(0).setRoute(routes.get(0));
        buses.get(1).setRoute(routes.get(1));
        buses.get(2).setRoute(routes.get(2));
        buses.get(3).setRoute(routes.get(3));
        buses.get(4).setRoute(routes.get(4));
    }
}