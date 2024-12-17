package org.multi.routes.repository.impl;

import org.multi.routes.model.BusStop;
import org.multi.routes.repository.EntityRepository;
import org.multi.routes.service.DataEntityInitializer;
import org.multi.routes.service.impl.DataEntityInitializerImpl;
import org.multi.routes.service.impl.DataEntityParserImpl;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

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
