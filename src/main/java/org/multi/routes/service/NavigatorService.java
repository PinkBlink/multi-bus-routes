package org.multi.routes.service;

import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;

import java.util.List;

public interface NavigatorService {
    List<BusStop> getTransitStops(Passenger passenger);
}