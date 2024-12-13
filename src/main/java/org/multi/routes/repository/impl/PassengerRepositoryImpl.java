package org.multi.routes.repository.impl;

import org.multi.routes.model.Passenger;
import org.multi.routes.repository.PassengerRepository;
import org.multi.routes.service.DataEntityInitializer;
import org.multi.routes.service.impl.DataEntityInitializerImpl;

import java.util.List;

public class PassengerRepositoryImpl implements PassengerRepository {
    private DataEntityInitializer dataEntityInitializer;

    public PassengerRepositoryImpl(DataEntityInitializer dataEntityInitializer) {
        this.dataEntityInitializer = dataEntityInitializer;
    }

    @Override
    public List<Passenger> getPassengers() {
        return dataEntityInitializer.getPassengers();
    }
}
