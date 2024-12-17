package org.multi.routes.service;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;

import java.util.List;

public interface BusRouteService {

    boolean containsStop(BusRoute route, BusStop stop);

    List<BusRoute> getBusRoutes();
}