package org.multi.routes;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.action.BusThread;
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
        BusStop busStop1 = new BusStop("stop1", 1);
        BusStop busStop2 = new BusStop("stop2", 2);
        BusStop busStop3 = new BusStop("stop3", 1);
        BusStop busStop4 = new BusStop("stop4", 2);

        Passenger passenger1 = new Passenger("passenger1", busStop1, busStop4);
        Passenger passenger2 = new Passenger("passenger2", busStop1, busStop4);
        Passenger passenger3 = new Passenger("passenger3", busStop1, busStop4);
        Passenger passenger4 = new Passenger("passenger4", busStop1, busStop4);


        BusRoute route = new BusRoute(1, Arrays.asList(busStop1, busStop2, busStop3, busStop4));

        Bus bus1 = new Bus(1, route, 1, busStop1);
        Bus bus2 = new Bus(2, route, 1, busStop1);
        Bus bus3 = new Bus(3, route, 2, busStop1);

        Future<String> stringFuture1 = executorService.submit(bus1);
        Future<String> stringFuture2 = executorService.submit(bus2);
        Future<String> stringFuture3 = executorService.submit(bus3);

        logger.log(INFO, stringFuture1.get());
        logger.log(INFO, stringFuture2.get());
        logger.log(INFO, stringFuture3.get());
        logger.log(INFO, bus1.getCurrentStop()+"<-- bus1 current stop");
        logger.log(INFO, bus1.getCurrentStop()+"<-- bus2 current stop");
        logger.log(INFO, bus1.getCurrentStop()+"<-- bus3 current stop");
        logger.log(INFO,"stop 1 busses/passengers : " + busStop1.getPassengerLine() + " / "+ busStop1.getStoppedBuses());
        logger.log(INFO,"stop 2 busses/passengers : " + busStop2.getPassengerLine() + " / "+ busStop2.getStoppedBuses());
        logger.log(INFO,"stop 3 busses/passengers : " + busStop3.getPassengerLine() + " / "+ busStop3.getStoppedBuses());
        logger.log(INFO,"stop 4 busses/passengers : " + busStop4.getPassengerLine() + " / "+ busStop4.getStoppedBuses());
    }
}