package org.multi.routes.action;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.multi.routes.ulils.Validator;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PassengerManager {
    private final Logger logger = LogManager.getLogger(this);
    private Passenger passenger;

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void enterInBus() {
        BusStop currentStop = passenger.getCurrentStop();


        Lock lockBusStop = currentStop.getLock();
        Condition conditionBusStop = currentStop.getCondition();

        lockBusStop.lock();

        List<Bus> stoppedBusses = passenger.getCurrentStop().getStoppedBuses();

        int desireBusIndex = Validator.getTheIndexOfTheDesiredBus(passenger, stoppedBusses);

        if (desireBusIndex != -1
                && !Validator.isBusFull(stoppedBusses.get(desireBusIndex))) {
            transferPassengerToBus(stoppedBusses, desireBusIndex);
        }
        lockBusStop.unlock();
        conditionBusStop.signalAll();
    }

    public void enterInBusStop() {
        Bus currentBus = passenger.getCurrentBus();
        BusStop currentStop = currentBus.getCurrentStop();

        if (currentStop.equals(passenger.getDestination())) {
            transferPassengerToStop();
        }
    }

    private void transferPassengerToBus(List<Bus> stoppedBusses, int desireBusIndex) {

        BusStop currentStop = passenger.getCurrentStop();
        Bus desireBus = stoppedBusses.get(desireBusIndex);

        Lock lockBus = desireBus.getLock();
        Condition conditionBus = desireBus.getCondition();

        lockBus.lock();
        try {
            desireBus.addPassengerToBus(passenger);
            currentStop.removePassengerFromLine(passenger);
            passenger.setCurrentBus(desireBus);
            passenger.setCurrentStop(null);

            logger.log(Level.INFO, passenger + " got on the bus " + desireBus);

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
            passenger.setCurrentBus(null);

            logger.log(Level.INFO, passenger + " got off at the bus stop " + currentStop);

            conditionBusStop.signalAll();
            conditionBus.signalAll();
        } finally {
            lockBusStop.unlock();
            lockBus.unlock();
        }
    }
}
