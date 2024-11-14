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
        currentStopManager.sendBus(bus);
        indexOfCurrentStop++;
    }

    public void stop() {
        currentStop = stops.get(indexOfCurrentStop);
        currentStopManager.setBusStop(currentStop);
        bus.setCurrentStop(currentStop);
        currentStopManager.takeBus(bus);
        logger.log(Level.INFO, "The bus " + bus + " stopped at " + currentStop);
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
        for (Passenger passenger : passengers) {
            passengerManager.setPassenger(passenger);
            passengerManager.enterInBus();
        }
    }

    @Override
    public String call() {
        while (indexOfCurrentStop < stops.size()) {
//            try {
            stop();
            disembarkPassengers();
//                TimeUnit.SECONDS.sleep(2);
            boardingPassengers();
//                TimeUnit.SECONDS.sleep(2);
            ride();
//            } catch (InterruptedException e) {
//                logger.log(Level.ERROR, e.getMessage());
//            }
        }
        return "\n------\n" + bus + " HAS COMPLETED" + "\n------\n" + bus.getPassengers() + "\n" + bus.getCurrentStop() + "\n------\n";
    }
}