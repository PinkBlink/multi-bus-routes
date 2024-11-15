package org.multi.routes;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.action.BusThread;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BusThreadTests {
    private final Logger logger = LogManager.getLogger(this);
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    BusStop stop1 = new BusStop("1", 2);
    BusStop stop2 = new BusStop("2", 1);
    BusStop stop3 = new BusStop("3", 2);
    BusStop stop4 = new BusStop("4", 3);
    BusStop stop5 = new BusStop("5", 1);
    BusStop stop6 = new BusStop("6", 2);

    Passenger passenger1 = new Passenger("passenger 1", stop1, stop2);
    Passenger passenger2 = new Passenger("passenger 2", stop1, stop3);
    Passenger passenger3 = new Passenger("passenger 3", stop1, stop4);
    Passenger passenger4 = new Passenger("passenger 4", stop1, stop4);
    Passenger passenger5 = new Passenger("passenger 5", stop1, stop5);
    Passenger passenger6 = new Passenger("passenger 6", stop1, stop6);


    BusRoute route1 = new BusRoute(1, Arrays.asList(stop1, stop2, stop3, stop4, stop5, stop6));

    Bus bus1 = new Bus(1, route1, 2, stop1);
    Bus bus2 = new Bus(2, route1, 2, stop1);
    Bus bus3 = new Bus(3, route1, 1, stop1);
    Bus bus4 = new Bus(4, route1, 1, stop1);
    Bus bus5 = new Bus(5, route1, 1, stop1);
    Bus bus6 = new Bus(6, route1, 3, stop1);

    BusThread busThread1 = new BusThread(bus1);
    BusThread busThread2 = new BusThread(bus2);
    BusThread busThread3 = new BusThread(bus3);
    BusThread busThread4 = new BusThread(bus4);
    BusThread busThread5 = new BusThread(bus5);
    BusThread busThread6 = new BusThread(bus6);

    @Test
    public void arrivalPassengersTest() throws ExecutionException, InterruptedException {

        Future<String> busString1 = executorService.submit(busThread1);
        Future<String> busString2 = executorService.submit(busThread2);
        Future<String> busString3 = executorService.submit(busThread3);
        Future<String> busString4 = executorService.submit(busThread4);
        Future<String> busString5 = executorService.submit(busThread5);
        Future<String> busString6 = executorService.submit(busThread6);

        logger.log(Level.INFO, busString1.get().toUpperCase());
        logger.log(Level.INFO, busString2.get().toUpperCase());
        logger.log(Level.INFO, busString3.get().toUpperCase());
        logger.log(Level.INFO, busString4.get().toUpperCase());
        logger.log(Level.INFO, busString5.get().toUpperCase());
        logger.log(Level.INFO, busString6.get().toUpperCase());

        List<Passenger> expectedPassengersStop1 = new ArrayList<>();
        List<Passenger> expectedPassengersStop2 = Collections.singletonList(passenger1);
        List<Passenger> expectedPassengersStop3 = Collections.singletonList(passenger2);
        List<Passenger> expectedPassengersStop4 = Arrays.asList(passenger3, passenger4);
        List<Passenger> expectedPassengersStop5 = Collections.singletonList(passenger5);
        List<Passenger> expectedPassengersStop6 = Collections.singletonList(passenger6);

        Assert.assertEquals(stop1.getPassengerLine(), expectedPassengersStop1);
        Assert.assertEquals(stop2.getPassengerLine(), expectedPassengersStop2);
        Assert.assertEquals(stop3.getPassengerLine(), expectedPassengersStop3);
        Assert.assertEquals(stop4.getPassengerLine(), expectedPassengersStop4);
        Assert.assertEquals(stop5.getPassengerLine(), expectedPassengersStop5);
        Assert.assertEquals(stop6.getPassengerLine(), expectedPassengersStop6);

    }
}
