package org.multi.routes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.BusService;
import org.multi.routes.service.BusState;
import org.multi.routes.service.BusStopService;
import org.multi.routes.service.PassengerService;
import org.multi.routes.ulils.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.logging.log4j.Level.INFO;

public class StopStateImpl implements BusState {
    Logger logger = LogManager.getLogger(StopStateImpl.class);
    private BusStopService busStopService = new BusStopServiceImpl();
    private BusService busService = new BusServiceImpl(FileDataRepository.getInstance());
    private PassengerService passengerService = new PassengerServiceImpl();
    private Bus bus;
    private List<BusStop> stops;
    private BusStop currentStop;

    public StopStateImpl(Bus bus) {
        this.bus = bus;
        stops = bus.getRoute().getStops();
    }

    @Override
    public BusState doAction() {
        stop();
        disembarkationPassengers();
        boardingPassengers();
        return new RideStateImpl(bus);
    }

    private void stop() {
        currentStop = stops.get(bus.getStopIndex());
        bus.setCurrentStop(currentStop);
        busStopService.addBusToStop(currentStop, bus);
    }

    private void disembarkationPassengers() {
        bus.getLock().lock();
        try {
            logger.log(INFO, bus + " began disembarking passengers;");
            Set<Passenger> passengerCopy = new HashSet<>(bus.getPassengers());
            for (Passenger passenger : passengerCopy) {
                if (Validator.hasTransitStops(passenger)
                        && passenger.getTransitStops().getFirst().equals(currentStop)) {
                    movePassengerToStop(passenger);
                }
                if (passenger.getDestination().equals(currentStop)) {
                    movePassengerToStop(passenger);
                }
            }
        } finally {
            bus.getLock().unlock();
        }
    }

    private void movePassengerToStop(Passenger passenger) {
        busService.removePassengerFromBus(bus, passenger);
        busStopService.addPassengerToLine(currentStop, passenger);
        logger.log(INFO, passenger + " removed from " + bus + " to " + currentStop);
        passenger.setCurrentStop(currentStop);
        if (passenger.getDestination().equals(currentStop)) {
            passenger.setArrivedAtDestination(true);
            logger.log(INFO, passenger + " IS ARRIVED TO DESTINATION");
        }
        passengerService.removeTransitStop(passenger, currentStop);
    }

    private void boardingPassengers() {
        Set<Passenger> passengersInLineCopy;
        currentStop.getLock().lock();
        try {
            passengersInLineCopy = new HashSet<>(currentStop.getPassengerLine());
        } finally {
            currentStop.getLock().unlock();
        }
        bus.getLock().lock();
        try {
            movePassengersToBus(passengersInLineCopy);
        } finally {
            bus.getLock().unlock();
        }
    }

    private void movePassengersToBus(Set<Passenger> passengers) {
        for (Passenger passenger : passengers) {
            if (!Validator.isBusFull(bus)
                    && Validator.isDesireBus(passenger, bus)
                    && !passenger.isArrivedAtDestination()) {
                currentStop.getLock().lock();
                try {
                    if (currentStop.getPassengerLine().contains(passenger)) {
                        busStopService.removePassengerFromLine(currentStop, passenger);
                        busService.addPassengerToBus(bus, passenger);
                        logger.log(INFO, passenger + " added to " + bus);
                    }
                } finally {
                    currentStop.getLock().unlock();
                }
            }
        }
    }
}