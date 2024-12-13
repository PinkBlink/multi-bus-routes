package org.multi.routes.service;

import org.multi.routes.model.Bus;
import org.multi.routes.model.Passenger;

import java.util.List;
import java.util.concurrent.Callable;

public interface BusService{
    Bus addPassengerToBus(Bus bus, Passenger passenger);
    Passenger removePassengerFromBus(Bus bus, Passenger passenger);
    List<Bus> getBuses();
}