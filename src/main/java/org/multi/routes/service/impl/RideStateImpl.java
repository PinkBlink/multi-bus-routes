package org.multi.routes.service.impl;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.service.BusState;
import org.multi.routes.service.BusStopService;

import static org.apache.logging.log4j.Level.INFO;

public class RideStateImpl implements BusState {
    private BusStopService busStopService = new BusStopServiceImpl();
    private Bus bus;
    private BusStop currentStop;

    public RideStateImpl(Bus bus) {
        this.bus = bus;
        currentStop = bus.getCurrentStop();
    }

    @Override
    public BusState doAction() {
        ride();
        return new StopStateImpl(bus);
    }

    private void ride() {
        currentStop.getLock().lock();
        try {
            busStopService.removeBusFromStop(currentStop,bus);
            bus.getLogger().log(INFO, bus + " going from " + currentStop);
            bus.setStopIndex(bus.getStopIndex() + 1);
        } finally {
            currentStop.getLock().unlock();
        }
    }
}