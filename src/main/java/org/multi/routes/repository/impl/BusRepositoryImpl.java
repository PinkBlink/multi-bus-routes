package org.multi.routes.repository.impl;

import org.multi.routes.model.Bus;
import org.multi.routes.repository.EntityRepository;
import org.multi.routes.service.DataEntityInitializer;

import java.util.List;

public class BusRepositoryImpl implements EntityRepository<Bus> {
    private DataEntityInitializer dataEntityInitializer;

    public BusRepositoryImpl(DataEntityInitializer dataEntityInitializer) {
        this.dataEntityInitializer = dataEntityInitializer;
    }

    @Override
    public List<Bus> getAll() {
        return dataEntityInitializer.getBuses();
    }
}