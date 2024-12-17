package org.multi.routes.service.impl;

import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.repository.impl.PassengerRepositoryImpl;
import org.multi.routes.service.PassengerService;

import java.util.List;

public class PassengerServiceImpl implements PassengerService {
    private PassengerRepositoryImpl passengerRepository;

    public PassengerServiceImpl(PassengerRepositoryImpl passengerRepository) {
        this.passengerRepository = passengerRepository;
    }
    public PassengerServiceImpl(){
    }

    @Override
    public boolean removeTransitStop(Passenger passenger, BusStop stop) {
        return passenger.getTransitStops().remove(stop);
    }

    @Override
    public List<Passenger> getPassengers() {
        return passengerRepository.getAll();
    }
}