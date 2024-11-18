package org.multi.routes.action;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import static org.apache.logging.log4j.Level.INFO;

public class BusThread implements Callable<String> {
    private final Logger logger;
    private final BusStopManager currentStopManager = new BusStopManager();
    private final PassengerManager passengerManager = new PassengerManager();
    private final Bus bus;
    private final List<BusStop> stops;
    private int indexOfCurrentStop;
    private BusStop currentStop;

    public BusThread(Bus bus) {
        this.bus = bus;
        currentStop = bus.getCurrentStop();

        stops = bus.getRoute().getStops();
        indexOfCurrentStop = stops.indexOf(currentStop);
        logger = LogManager.getLogger(bus);
    }

    public void ride() {
        currentStop = stops.get(indexOfCurrentStop);
        currentStopManager.setBusStop(currentStop);
        bus.setCurrentStop(currentStop);
        logger.log(INFO, bus + " going from " + currentStop + " passengers -> " + bus.getPassengers());
        currentStopManager.sendBus(bus);
        indexOfCurrentStop++;
    }

    public void stop() {
        currentStop = stops.get(indexOfCurrentStop);
        currentStopManager.setBusStop(currentStop);
        bus.setCurrentStop(currentStop);
        currentStopManager.takeBus(bus);
        logger.log(INFO, "The bus " + bus + " stopped at " + currentStop + " passengers -> " + bus.getPassengers());
    }

    public void disembarkPassengers() {
        List<Passenger> passengers = new ArrayList<>(bus.getPassengers());
        for (Passenger passenger : passengers) {
            passengerManager.setPassenger(passenger);
            passengerManager.enterInBusStop();
        }

    }

    public void boardingPassengers() {
        List<Passenger> passengers = new ArrayList<>(currentStop.getPassengerLine());
        logger.log(INFO, bus + " start to boarding passengers from " + currentStop);
        logger.log(INFO, passengers);
        for (Passenger passenger : passengers) {
            passengerManager.setPassenger(passenger);
            passengerManager.enterInBus();
        }
    }

    @Override
    public String call() {
        while (indexOfCurrentStop < stops.size()) {
            Lock lockCurrentStop = currentStop.getLock();
            Condition conditionCurrentStop = currentStop.getCondition();

            lockCurrentStop.lock();
            try {
                while (currentStopManager.isLoading()) {
                    conditionCurrentStop.await();
                }
                currentStopManager.setLoading(true);
                stop();
                disembarkPassengers();
                TimeUnit.MICROSECONDS.sleep(13);
                boardingPassengers();
                TimeUnit.MICROSECONDS.sleep(13);
                ride();
                currentStopManager.setLoading(false);
                conditionCurrentStop.signalAll();
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, e.getMessage());
            }finally {
                lockCurrentStop.unlock();
            }
        }
        return "\n------\n" + bus + " HAS COMPLETED" + "\n------\n" + bus.getPassengers() + "\n" + bus.getCurrentStop() + "\n------\n";
    }
}