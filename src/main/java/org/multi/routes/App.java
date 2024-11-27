package org.multi.routes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.action.LogisticManager;
import org.multi.routes.action.PassengerManager;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.INFO;

public class App {
    private final static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        LogisticManager logisticManager = LogisticManager.getInstance();
        PassengerManager passengerManager = new PassengerManager();
        List<Passenger> passengers = passengerManager.getPassengers();
        List<Bus> buses = logisticManager.getBuses();
        List<BusStop> stops = logisticManager.getStops();

        List<Future<String>> futures = new ArrayList<>();

        for (Bus bus : buses) {
            futures.add(executorService.submit(bus));
        }
        for (Future<String> future : futures) {
            logger.log(INFO, future.get());
        }
        executorService.shutdown();
        passengers.forEach(p -> logger.log(INFO, p + " is arrived: " + p.isArrivedAtDestination()));
        List<Passenger> notArrivedPassengers = passengers.stream().filter(p -> !p.isArrivedAtDestination()).toList();
        notArrivedPassengers.forEach(p -> logger.log(ERROR, p + " Current stop" + p.getCurrentStop()
                + "\nCurrent bus: " + p.getCurrentBus()
                + "\nDestination: " + p.getDestination()));
        buses.forEach(bus -> logger.log(INFO, bus + " passengers " + bus.getPassengers()));
        stops.forEach(stop -> logger.log(INFO, stop + " " + stop.getPassengerLine()));
    }
}