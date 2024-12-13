package org.multi.routes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.service.BusState;
import org.multi.routes.service.BusStopService;

import static org.apache.logging.log4j.Level.INFO;

public class RideStateImpl implements BusState {
    Logger logger = LogManager.getLogger(RideStateImpl.class);
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
            busStopService.removeBusFromStop(currentStop, bus);
            logger.log(INFO, bus + " going from " + currentStop);
            bus.setStopIndex(bus.getStopIndex() + 1);
        } finally {
            currentStop.getLock().unlock();
        }
    }
}