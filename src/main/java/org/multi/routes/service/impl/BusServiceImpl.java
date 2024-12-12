package org.multi.routes.service.impl;

import org.multi.routes.model.Bus;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.BusService;
import org.multi.routes.ulils.Validator;

import java.util.Set;

public class BusServiceImpl implements BusService {
    @Override
    public Bus addPassengerToBus(Bus bus, Passenger passenger) {
        bus.getLock().lock();
        try {
            if(Validator.isBusFull(bus)){
                return bus;
            }
            Set<Passenger> passengersInBus = bus.getPassengers();
            passengersInBus.add(passenger);
            return bus;
        } finally {
            bus.getLock().unlock();
        }
    }

    @Override
    public Passenger removePassengerFromBus(Bus bus, Passenger passenger) {
        bus.getLock().lock();
        try{
            Set<Passenger> passengersInBus = bus.getPassengers();
            passengersInBus.remove(passenger);
            return passenger;
        }finally {
            bus.getLock().unlock();
        }
    }
}