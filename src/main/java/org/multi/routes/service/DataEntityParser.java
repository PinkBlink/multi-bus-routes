package org.multi.routes.service;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;

import java.util.List;
import java.util.Map;

public interface DataEntityParser {
    List<Bus> getBusesFromData();
    List<BusStop> getBusStopsFromData();
    Map<Passenger, List<String>> getPassengersFromData();
}