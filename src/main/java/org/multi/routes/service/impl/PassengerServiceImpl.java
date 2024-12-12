package org.multi.routes.service.impl;

import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.PassengerService;

public class PassengerServiceImpl implements PassengerService {
    @Override
    public boolean removeTransitStop(Passenger passenger, BusStop stop) {
        return passenger.getTransitStops().remove(stop);
    }
}