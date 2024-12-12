package org.multi.routes.service.impl;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Bus;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.DataEntityParser;
import org.multi.routes.ulils.LogisticUtils;

import java.util.List;

public class DataEntityInitializer {
    private List<BusStop> busStops;
    private List<BusRoute> busRoutes;
    private List<Bus> buses;
    private List<Passenger> passengers;
    private DataEntityParser dataEntityParser;

    public DataEntityInitializer(DataEntityParser dataEntityParser) {
        this.dataEntityParser = dataEntityParser;
        setUp();
    }

    public List<BusStop> getBusStops() {
        return busStops;
    }

    public List<BusRoute> getBusRoutes() {
        return busRoutes;
    }

    public List<Bus> getBuses() {
        return buses;
    }

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
