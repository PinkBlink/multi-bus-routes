package org.multi.routes.service;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;

import java.util.List;

public interface DataEntityInitializer {
    List<BusStop> getBusStops();
    List<BusRoute> getBusRoutes();
    List<Bus> getBuses();
    List<Passenger> getPassengers();
}
