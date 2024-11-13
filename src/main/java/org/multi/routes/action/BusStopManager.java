package org.multi.routes.action;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.multi.routes.ulils.Validator;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BusStopManager {
    private Logger logger;
    private BusStop busStop;

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
                logger.log(Level.INFO, "There is not enough space for the bus," + bus + " the departure of those already stopped is awaiting");
                condition.await();
            }
            busStop.addBusToStop(bus);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }

    public void sendBus(Bus bus) {
        Lock lock = busStop.getLock();
        Condition condition = busStop.getCondition();
        lock.lock();

        try {
            busStop.removeBusFromStop(bus);
        } finally {
            condition.signalAll();
            lock.unlock();
        }
    }

    public void addPassengerToLine(Passenger passenger) {
        busStop.getLock().lock();
        busStop.addPassengerToLine(passenger);
        busStop.getCondition().signalAll();
        busStop.getLock().unlock();
    }
}