package org.multi.routes.repository.impl;

import org.multi.routes.model.BusRoute;
import org.multi.routes.repository.BusRouteRepository;
import org.multi.routes.service.DataEntityInitializer;
import org.multi.routes.service.impl.DataEntityInitializerImpl;

import java.util.List;

public class BusRouteRepositoryImpl implements BusRouteRepository {
    private DataEntityInitializer dataEntityInitializer;

    public BusRouteRepositoryImpl(DataEntityInitializer dataEntityInitializer) {
        this.dataEntityInitializer = dataEntityInitializer;
    }

    @Override
    public List<BusRoute> getBusRoutes() {
        return dataEntityInitializer.getBusRoutes();
    }
}
