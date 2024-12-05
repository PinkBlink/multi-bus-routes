package org.multi.routes.service;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;

import java.util.List;

public interface EntityParser {
    List<Bus> getBuses(List<String> busesString);
    List<BusStop> getBusStops(List<String> busStopsString);
    List<Passenger> getPassengers(List<String> passengersString);

}
