package org.multi.routes.entity;

import org.multi.routes.ulils.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.logging.log4j.Level.INFO;

public class StopState implements BusState {
    private final Bus bus;
    private final List<BusStop> stops;
    private BusStop currentStop;

    public StopState(Bus bus) {
        this.bus = bus;
        currentStop = bus.getCurrentStop();
        stops = bus.getRoute().getStops();
    }

    @Override
    public BusState act() {
        stop();
        disembarkationPassengers();
        boardingPassengers();
        return new RideState(bus);
    }

    public void stop() {
        currentStop = stops.get(bus.getStopIndex());
        bus.setCurrentStop(currentStop);
        currentStop.addBusToStop(bus);
    }

    public void disembarkationPassengers() {
        bus.getLock().lock();
        try {
            bus.getLogger().log(INFO, bus + " began disembarking passengers;");
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
        bus.removePassenger(passenger);
        bus.getLogger().log(INFO, passenger + " removed from " + this + " to " + currentStop);
        currentStop.addPassengerToLine(passenger);
        passenger.setCurrentStop(currentStop);
    }

    public void boardingPassengers() {
        Set<Passenger> passengersInLineCopy;
        currentStop.getLock().lock();
        try {
            passengersInLineCopy = new HashSet<>(currentStop.getPassengerLine());
        } finally {
            currentStop.getLock().unlock();
        }
        bus.getLock().lock();
        try {
            for (Passenger passenger : passengersInLineCopy) {
                if (!Validator.isBusFull(bus)
                        && Validator.isDesireBus(passenger, bus)
                        && !passenger.isArrivedAtDestination()) {
                    currentStop.getLock().lock();
                    try {
                        if (currentStop.getPassengerLine().contains(passenger)) {
                            currentStop.removePassengerFromLine(passenger);
                            bus.addPassengerToBus(passenger);
                            bus.getLogger().log(INFO, passenger + " added to " + this);
                        }
                    } finally {
                        currentStop.getLock().unlock();
                    }
                }
            }
        } finally {
            bus.getLock().unlock();
        }
    }
}
