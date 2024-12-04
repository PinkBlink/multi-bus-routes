package org.multi.routes.entity;

import static org.apache.logging.log4j.Level.INFO;

public class RideState implements BusState {
    private final Bus bus;
    private final BusStop currentStop;

    public RideState(Bus bus) {
        this.bus = bus;
        currentStop = bus.getCurrentStop();
    }

    @Override
    public BusState act() {
        ride();
        return new StopState(bus);
    }

    private void ride() {
        currentStop.getLock().lock();
        try {
            currentStop.removeBusFromStop(bus);
            bus.getLogger().log(INFO, bus + " going from " + currentStop);
            bus.setStopIndex(bus.getStopIndex() + 1);
        } finally {
            currentStop.getLock().unlock();
        }
    }
}