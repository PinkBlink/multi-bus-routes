package org.multi.routes.service;

import org.multi.routes.model.Bus;
import org.multi.routes.model.Passenger;
import org.multi.routes.repository.impl.BusRepositoryImpl;

import java.util.List;

public interface BusService {
    Bus addPassengerToBus(Bus bus, Passenger passenger);

    Passenger removePassengerFromBus(Bus bus, Passenger passenger);

    void setBusRepository(BusRepositoryImpl busRepository);

    BusRepositoryImpl getBusRepository();

    List<Bus> getBuses();
}