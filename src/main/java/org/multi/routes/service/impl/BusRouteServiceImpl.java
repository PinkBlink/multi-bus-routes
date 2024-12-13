package org.multi.routes.service.impl;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.repository.BusRouteRepository;
import org.multi.routes.service.BusRouteService;

import java.util.List;
import java.util.Map;

public class BusRouteServiceImpl implements BusRouteService {
    private BusRouteRepository busRouteRepository;

    public BusRouteServiceImpl(BusRouteRepository busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }

    @Override
    public void addNextRoute(BusRoute currentRoute, BusRoute nextRoute, BusStop transitStop) {
        Map<BusRoute, BusStop> nextAccessibleRoute = currentRoute.getNextAccessibleRoutes();
        nextAccessibleRoute.put(nextRoute, transitStop);
    }

    @Override
    public boolean containsStop(BusRoute route, BusStop stop) {
        List<BusStop> stops = route.getStops();
        return stops.contains(stop);
    }

    @Override
    public List<BusRoute> getBusRoutes() {
        return busRouteRepository.getBusRoutes();
    }
}