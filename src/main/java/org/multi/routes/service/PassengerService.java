package org.multi.routes.service;

import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.repository.impl.PassengerRepositoryImpl;

import java.util.List;

public interface PassengerService {
    boolean removeTransitStop(Passenger passenger, BusStop stop);

    List<Passenger> getPassengers();

    void setPassengerRepository(PassengerRepositoryImpl passengerRepository);

    PassengerRepositoryImpl getPassengerRepository();
}