package org.multi.routes.repository.impl;

import org.multi.routes.model.BusStop;
import org.multi.routes.repository.EntityRepository;
import org.multi.routes.service.DataEntityInitializer;

import java.util.List;

public class BusStopsRepositoryImpl implements EntityRepository<BusStop> {
    private DataEntityInitializer dataEntityInitializer;

    public BusStopsRepositoryImpl(DataEntityInitializer dataEntityInitializer) {
        this.dataEntityInitializer = dataEntityInitializer;
    }

    @Override
    public List<BusStop> getAll() {
        return dataEntityInitializer.getBusStops();
    }
}