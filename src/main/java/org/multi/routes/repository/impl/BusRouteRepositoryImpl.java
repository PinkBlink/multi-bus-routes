package org.multi.routes.repository.impl;

import org.multi.routes.model.BusRoute;
import org.multi.routes.repository.EntityRepository;
import org.multi.routes.service.DataEntityInitializer;

import java.util.List;

public class BusRouteRepositoryImpl implements EntityRepository<BusRoute> {
    private DataEntityInitializer dataEntityInitializer;

    public BusRouteRepositoryImpl(DataEntityInitializer dataEntityInitializer) {
        this.dataEntityInitializer = dataEntityInitializer;
    }

    @Override
    public List<BusRoute> getAll() {
        return dataEntityInitializer.getBusRoutes();
    }
}