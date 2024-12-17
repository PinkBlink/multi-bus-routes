package org.multi.routes.repository.impl;

import org.multi.routes.model.Passenger;
import org.multi.routes.repository.EntityRepository;
import org.multi.routes.service.DataEntityInitializer;

import java.util.List;

public class PassengerRepositoryImpl implements EntityRepository<Passenger> {
    private DataEntityInitializer dataEntityInitializer;

    public PassengerRepositoryImpl(DataEntityInitializer dataEntityInitializer) {
        this.dataEntityInitializer = dataEntityInitializer;
    }

    @Override
    public List<Passenger> getAll() {
        return dataEntityInitializer.getPassengers();
    }
}
