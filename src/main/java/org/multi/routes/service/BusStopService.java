package org.multi.routes.service;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.repository.impl.BusStopsRepositoryImpl;

import java.util.List;

public interface BusStopService {
    BusStop addBusToStop(BusStop stop, Bus bus);

    BusStop removeBusFromStop(BusStop stop, Bus bus);

    BusStop addPassengerToLine(BusStop stop, Passenger passenger);

    BusStop removePassengerFromLine(BusStop stop, Passenger passenger);

    void setBusStopRepository(BusStopsRepositoryImpl busStopRepository);
    BusStopsRepositoryImpl getBusStopRepository();
    List<BusStop> getBusStops();
}