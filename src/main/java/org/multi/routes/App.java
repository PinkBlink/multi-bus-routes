package org.multi.routes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.apache.logging.log4j.Level.INFO;

public class App {
    private final static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        BusStop busStop1 = new BusStop("stop1", 2);
        BusStop busStop2 = new BusStop("stop2", 2);
        BusStop busStop3 = new BusStop("stop3", 3);
        BusStop busStop4 = new BusStop("stop4", 2);


        Passenger passenger1 = new Passenger("passenger1");
        passenger1.setCurrentStop(busStop1);
        passenger1.setDestination(busStop2);
        busStop1.addPassengerToLine(passenger1);

        Passenger passenger2 = new Passenger("passenger2");
        passenger2.setCurrentStop(busStop1);
        passenger2.setDestination(busStop3);
        busStop1.addPassengerToLine(passenger2);


        Passenger passenger3 = new Passenger("passenger3");
        passenger3.setCurrentStop(busStop1);
        passenger3.setDestination(busStop4);
        busStop1.addPassengerToLine(passenger3);


        Passenger passenger4 = new Passenger("passenger4");
        passenger4.setCurrentStop(busStop1);
        passenger4.setDestination(busStop4);
        busStop1.addPassengerToLine(passenger4);



        BusRoute route = new BusRoute(1, Arrays.asList(busStop1, busStop2, busStop3, busStop4));

        Bus bus1 = new Bus(1, 2);
        Bus bus2 = new Bus(2, 2);
        Bus bus3 = new Bus(3, 2);
        bus1.setRoute(route);
        bus2.setRoute(route);
        bus3.setRoute(route);

        Future<String> stringFuture1 = executorService.submit(bus1);
        Future<String> stringFuture2 = executorService.submit(bus2);
        Future<String> stringFuture3 = executorService.submit(bus3);

        logger.log(INFO, stringFuture1.get());
        logger.log(INFO, stringFuture2.get());
        logger.log(INFO, stringFuture3.get());
        logger.log(INFO, bus1.getCurrentStop() + "<-- bus1 current stop");
        logger.log(INFO, bus1.getCurrentStop() + "<-- bus2 current stop");
        logger.log(INFO, bus1.getCurrentStop() + "<-- bus3 current stop");
        logger.log(INFO, "stop 1 busses/passengers : " + busStop1.getPassengerLine() + " / " + busStop1.getStoppedBuses());
        logger.log(INFO, "stop 2 busses/passengers : " + busStop2.getPassengerLine() + " / " + busStop2.getStoppedBuses());
        logger.log(INFO, "stop 3 busses/passengers : " + busStop3.getPassengerLine() + " / " + busStop3.getStoppedBuses());
        logger.log(INFO, "stop 4 busses/passengers : " + busStop4.getPassengerLine() + " / " + busStop4.getStoppedBuses());

        executorService.shutdown();
    }
}