package org.multi.routes.action;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.multi.routes.ulils.Validator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PassengerManager {
    private final Logger logger = LogManager.getLogger(this);
    private Passenger passenger;

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void enterInBus() {
//        BusStop currentStop = passenger.getCurrentStop();
//
//        Lock lockBusStop = currentStop.getLock();
//        lockBusStop.lock();
//        Condition conditionBusStop = currentStop.getCondition();
//
//        try {
//            Set<Bus> stoppedBusses = passenger.getCurrentStop().getStoppedBuses();
//
//            int desireBusIndex = Validator.getTheIndexOfTheDesiredBus(passenger, stoppedBusses);
//
//            if (desireBusIndex != -1
//                    && !Validator.isBusFull(stoppedBusses.get(desireBusIndex))
//                    && !passenger.isArrivedAtDestination()) {
//                transferPassengerToBus(desireBusIndex, stoppedBusses);
//            }
//            conditionBusStop.signalAll();
//        } finally {
//            lockBusStop.unlock();
//        }
    }

    public void enterInBusStop() {
        Bus currentBus = passenger.getCurrentBus();
        BusStop currentStop = currentBus.getCurrentStop();
        if (currentStop.equals(passenger.getDestination())) {
            transferPassengerToStop();
            logger.log(Level.INFO, passenger + " ARRIVAL TO DESTINATION!");

        }
    }

    private void transferPassengerToBus(int desireBusIndex, List<Bus> stoppedBusses) {
        BusStop currentStop = passenger.getCurrentStop();
        Bus desireBus = stoppedBusses.get(desireBusIndex);
        Lock lockBus = desireBus.getLock();
        lockBus.lock();
        try {
            Condition conditionBus = desireBus.getCondition();
            currentStop.removePassengerFromLine(passenger);
            logger.log(Level.INFO, passenger + " was removed from :" + currentStop);
            logger.log(Level.INFO, "List of passengers:" + currentStop.getPassengerLine());
            if (!desireBus.getPassengers().contains(passenger)) {
                desireBus.addPassengerToBus(passenger);

            } else {
                currentStop.removePassengerFromLine(passenger);
                logger.log(Level.ERROR, passenger + " already in bus ");
                logger.log(Level.ERROR, desireBus.getPassengers());
                logger.log(Level.ERROR, Thread.currentThread().getName());
            }
            passenger.setCurrentBus(desireBus);
            conditionBus.signalAll();
        } finally {
            lockBus.unlock();
        }
    }


    private void transferPassengerToStop() {
        Bus currentBus = passenger.getCurrentBus();
        BusStop currentStop = currentBus.getCurrentStop();

        Lock lockBusStop = currentStop.getLock();
        Condition conditionBusStop = currentStop.getCondition();

        Lock lockBus = currentBus.getLock();
        Condition conditionBus = currentBus.getCondition();

        lockBusStop.lock();
        lockBus.lock();
        try {
            currentBus.removePassenger(passenger);
            currentStop.addPassengerToLine(passenger);
            passenger.setCurrentStop(currentStop);
            passenger.setArrivedAtDestination(true);
            logger.log(Level.INFO, passenger + " got off at the bus stop " + currentStop);

            conditionBusStop.signalAll();
            conditionBus.signalAll();
        } finally {
            lockBusStop.unlock();
            lockBus.unlock();
        }
    }
}