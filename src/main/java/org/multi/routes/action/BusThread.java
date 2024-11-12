package org.multi.routes.action;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class BusThread implements Callable<String> {
    private final Logger logger;
    private final Bus bus;
    private final List<BusStop> stops;
    private final BusStopManager currentStopManager = new BusStopManager();
    private int indexOfCurrentStop = 0;
    private BusStop currentStop;

    public BusThread(Bus bus) {
        this.bus = bus;

        stops = bus.getRoute().getStops();
        logger = LogManager.getLogger(bus);
    }

    public void ride() {
        currentStop = stops.get(indexOfCurrentStop);
        currentStopManager.setBusStop(currentStop);
        logger.log(Level.INFO, bus + " going from " + currentStop);
        currentStopManager.sendBus(bus);
        indexOfCurrentStop++;
    }

    public void stop() {
        currentStop = stops.get(indexOfCurrentStop);
        currentStopManager.setBusStop(currentStop);
        currentStopManager.takeBus(bus);
        logger.log(Level.INFO, "The bus " + bus + " stopped at " + currentStop);
    }

    @Override
    public String call() {
        while (indexOfCurrentStop < stops.size()) {
            try {
                stop();
                TimeUnit.SECONDS.sleep(4);
                ride();
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, e.getMessage());
            }
        }
        return "Bus " + bus + " has completed its route";
    }
}