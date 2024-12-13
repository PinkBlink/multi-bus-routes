package org.multi.routes.repository.impl;

import org.multi.routes.model.Bus;
import org.multi.routes.repository.BusRepository;
import org.multi.routes.service.impl.DataEntityInitializerImpl;

import java.util.List;

public class BusRepositoryImpl implements BusRepository {
    private DataEntityInitializerImpl dataEntityInitializer;

    public BusRepositoryImpl(DataEntityInitializerImpl dataEntityInitializer) {
        this.dataEntityInitializer = dataEntityInitializer;
    }

    @Override
    public List<Bus> getBuses() {
        return dataEntityInitializer.getBuses();
    }
}
