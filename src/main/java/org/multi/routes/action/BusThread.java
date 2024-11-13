package org.multi.routes.action;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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
        logger.log(Level.INFO, bus + " going from " + currentStop);
        bus.setStop(false);
        currentStopManager.sendBus(bus);
        indexOfCurrentStop++;
    }

    public void stop() {
        currentStop = stops.get(indexOfCurrentStop);
        currentStopManager.setBusStop(currentStop);
        bus.setCurrentStop(currentStop);
        currentStopManager.takeBus(bus);
        bus.setStop(true);
        logger.log(Level.INFO, "The bus " + bus + " stopped at " + currentStop);
    }

    public void disembarkPassengers() {
        if (bus.getPassengers().isEmpty()) {
            logger.log(Level.INFO, bus + " is empty.");
        } else {
            List<Passenger> passengers = bus.getPassengers();
            for (Passenger passenger : passengers) {
                passengerManager.setPassenger(passenger);
                passengerManager.enterInBusStop();
            }
        }
    }

    public void boardingPassengers() {
        if (currentStop.getPassengerLine().isEmpty()) {
            logger.log(Level.INFO, currentStop + " is empty.");
        } else {
            List<Passenger> passengers = currentStop.getPassengerLine();
            for (Passenger passenger : passengers) {
                passengerManager.setPassenger(passenger);
                passengerManager.enterInBus();
            }
        }
    }

    @Override
    public String call() {
        while (indexOfCurrentStop < stops.size()) {
            try {
                stop();
                disembarkPassengers();
                TimeUnit.SECONDS.sleep(2);
                boardingPassengers();
                TimeUnit.SECONDS.sleep(2);
                ride();
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
        }
        return "Bus " + bus + " has completed its route";
    }
}