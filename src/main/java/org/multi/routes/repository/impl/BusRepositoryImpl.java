package org.multi.routes.repository.impl;

import org.multi.routes.model.Bus;
import org.multi.routes.repository.EntityRepository;
import org.multi.routes.service.impl.DataEntityInitializerImpl;

import java.util.List;

public class BusRepositoryImpl implements EntityRepository<Bus> {
    private DataEntityInitializerImpl dataEntityInitializer;

    private BusRepositoryImpl(DataEntityInitializerImpl dataEntityInitializer) {
        this.dataEntityInitializer = dataEntityInitializer;
    }
    @Override
    public List<Bus> getAll() {
        return dataEntityInitializer.getBuses();
    }
}
