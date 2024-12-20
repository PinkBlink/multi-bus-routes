package org.multi.routes.service.impl;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Bus;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.DataEntityInitializer;
import org.multi.routes.service.DataEntityParser;
import org.multi.routes.ulils.LogisticUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class DataEntityInitializerImpl implements DataEntityInitializer {
    private static ReentrantLock lock = new ReentrantLock();
    private static DataEntityInitializerImpl instance;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private List<BusStop> busStops;
    private List<BusRoute> busRoutes;
    private List<Bus> buses;
    private List<Passenger> passengers;
    private DataEntityParser dataEntityParser;

    public static DataEntityInitializerImpl getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (!isCreated.get()) {
                    instance = new DataEntityInitializerImpl(new DataEntityParserImpl());
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private DataEntityInitializerImpl(DataEntityParser dataEntityParser) {
        this.dataEntityParser = dataEntityParser;
        setUp();
    }

    @Override
    public List<BusStop> getBusStops() {
        return busStops;
    }

    @Override
    public List<BusRoute> getBusRoutes() {
        return busRoutes;
    }

    @Override
    public List<Bus> getBuses() {
        return buses;
    }

    @Override
    public List<Passenger> getPassengers() {
        return passengers;
    }

    private void setUp() {
        busStops = dataEntityParser.getBusStopsFromData();
        busRoutes = dataEntityParser.getBusRoutesFromData(busStops);
        LogisticUtils.createMap(busRoutes);
        buses = dataEntityParser.getBusesFromData(busStops, busRoutes);
        passengers = dataEntityParser.getPassengersFromData(busStops, busRoutes);
    }
}