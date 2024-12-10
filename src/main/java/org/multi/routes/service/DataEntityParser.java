package org.multi.routes.service;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;

import java.util.List;

public interface DataEntityParser {
    List<BusStop> getBusStopsFromData();
    List<BusRoute> getBusRoutesFromData(List<BusStop> busStops);
    List<Bus> getBusesFromData(List<BusRoute> routes);
    List<Passenger> getPassengersFromData(List<BusStop> stops, List<BusRoute> routes);
}
