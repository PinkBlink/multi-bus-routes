package org.multi.routes.repository.impl;

import org.multi.routes.model.BusStop;
import org.multi.routes.repository.BusStopRepository;
import org.multi.routes.service.DataEntityInitializer;
import org.multi.routes.service.impl.DataEntityInitializerImpl;

import java.util.List;

public class BusStopsRepositoryImpl implements BusStopRepository {
    private DataEntityInitializer dataEntityInitializer;

    public BusStopsRepositoryImpl(DataEntityInitializer dataEntityInitializer) {
        this.dataEntityInitializer = dataEntityInitializer;
    }

    @Override
    public List<BusStop> getBusStops() {
        return dataEntityInitializer.getBusStops();
    }
}
