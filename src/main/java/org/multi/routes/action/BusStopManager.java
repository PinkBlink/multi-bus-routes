package org.multi.routes.action;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.ulils.Validator;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import static org.apache.logging.log4j.Level.INFO;

public class BusStopManager {
    private Logger logger;
    private BusStop busStop;

    private boolean isLoading;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setBusStop(BusStop busStop) {
        this.busStop = busStop;
        logger = LogManager.getLogger(this);
    }

    public void takeBus(Bus bus) {
        Lock lock = busStop.getLock();
        Condition condition = busStop.getCondition();
        lock.lock();
        try {
            while (Validator.isStopFull(busStop)) {
                logger.log(INFO, busStop + " not enough space for the bus," + bus + " the departure of those already stopped is awaiting");
                condition.await();
            }
            busStop.addBusToStop(bus);
            logger.log(INFO, bus + " added to " + busStop);
            logger.log(INFO, "stopped busses: " + busStop.getStoppedBuses());
            condition.signalAll();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public void sendBus(Bus bus) {
        Lock lock = busStop.getLock();
        Condition condition = busStop.getCondition();
        lock.lock();
        try {
            busStop.removeBusFromStop(bus);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}