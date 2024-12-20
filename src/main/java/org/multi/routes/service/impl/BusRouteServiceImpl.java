package org.multi.routes.service.impl;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.repository.impl.BusRouteRepositoryImpl;
import org.multi.routes.service.BusRouteService;

import java.util.List;

public class BusRouteServiceImpl implements BusRouteService {
    private BusRouteRepositoryImpl busRouteRepository;

    @Override
    public BusRouteRepositoryImpl getBusRouteRepository() {
        return busRouteRepository;
    }

    @Override
    public void setBusRouteRepository(BusRouteRepositoryImpl busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }

    @Override
    public boolean containsStop(BusRoute route, BusStop stop) {
        List<BusStop> stops = route.getStops();
        return stops.contains(stop);
    }

    @Override
    public List<BusRoute> getBusRoutes() {
        return busRouteRepository.getAll();
    }
}