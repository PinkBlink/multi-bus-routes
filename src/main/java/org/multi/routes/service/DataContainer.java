package org.multi.routes.service;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;

import java.util.List;

public interface DataContainer {
    List<Bus> getBuses();
    List<BusStop> getBusStops();
    List<Passenger> getPassengers();
}
