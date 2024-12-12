package org.multi.routes.service;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;

public interface BusStopService {
    public BusStop addBusToStop(BusStop stop, Bus bus);

    public BusStop removeBusFromStop(BusStop stop, Bus bus);

    public BusStop addPassengerToLine(BusStop stop, Passenger passenger);

    public BusStop removePassengerFromLine(BusStop stop, Passenger passenger);
}