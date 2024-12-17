package org.multi.routes.service.impl;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.repository.EntityRepository;
import org.multi.routes.service.BusRouteService;

import java.util.List;
import java.util.Map;

public class BusRouteServiceImpl implements BusRouteService {
    private EntityRepository<BusRoute> busRouteRepository;

    public BusRouteServiceImpl(EntityRepository<BusRoute> busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }
    public BusRouteServiceImpl(){
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