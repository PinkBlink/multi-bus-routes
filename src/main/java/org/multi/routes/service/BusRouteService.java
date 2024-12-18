package org.multi.routes.service;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.repository.impl.BusRouteRepositoryImpl;

import java.util.List;

public interface BusRouteService {
    boolean containsStop(BusRoute route, BusStop stop);

    void setBusRouteRepository(BusRouteRepositoryImpl busRouteRepository);

    BusRouteRepositoryImpl getBusRouteRepository();

    List<BusRoute> getBusRoutes();
}