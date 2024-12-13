package org.multi.routes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.BusStopService;
import org.multi.routes.ulils.Validator;

import java.util.List;
import java.util.Set;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.INFO;

public class BusStopServiceImpl implements BusStopService {
    private final Logger logger = LogManager.getLogger(BusStopServiceImpl.class);

    @Override
    public BusStop addBusToStop(BusStop stop, Bus bus) {
        stop.getLock().lock();
        try {
            while (Validator.isStopFull(stop)) {
                stop.getCondition().await();
            }
            Set<Bus> stoppedBuses = stop.getStoppedBuses();
            stoppedBuses.add(bus);
            logger.log(INFO, bus + " arrived at the stop " + stop);
            logger.log(INFO, stop + " stopped buses : " + stoppedBuses);
            stop.getCondition().signalAll();
        } catch (InterruptedException e) {
            logger.log(ERROR, e.getMessage());
        } finally {
            stop.getLock().unlock();
        }
        return stop;
    }

    @Override
    public BusStop removeBusFromStop(BusStop stop, Bus bus) {
        stop.getLock().lock();
        try {
            Set<Bus> stoppedBuses = stop.getStoppedBuses();
            stoppedBuses.remove(bus);
            logger.log(INFO, bus + " was removed from " + stop);
            logger.log(INFO, stop + " stopped buses : " + stoppedBuses);
            stop.getCondition().signalAll();
            return stop;
        } finally {
            stop.getLock().unlock();
        }
    }

    @Override
    public BusStop addPassengerToLine(BusStop stop, Passenger passenger) {
        stop.getLock().lock();
        try {
            Set<Passenger> passengerLine = stop.getPassengerLine();
            passengerLine.add(passenger);
            logger.log(INFO, passenger + " added to " + stop);
            return stop;
        } finally {
            stop.getLock().unlock();
        }
    }

    @Override
    public BusStop removePassengerFromLine(BusStop stop, Passenger passenger) {
        stop.getLock().lock();
        try {
            Set<Passenger> passengerLine = stop.getPassengerLine();
            passengerLine.remove(passenger);
            logger.log(INFO, passenger + " was removed from " + this);
            return stop;
        } finally {
            stop.getLock().unlock();
        }
    }
}