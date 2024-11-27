package org.multi.routes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.action.LogisticManager;
import org.multi.routes.action.NavigateManager;
import org.multi.routes.action.PassengerManager;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.LongFunction;

import static org.apache.logging.log4j.Level.INFO;

public class ThreadsTests {
    private Logger logger = LogManager.getLogger(this);


    @Test
    public void test1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        LogisticManager logisticManager = LogisticManager.getInstance();
        PassengerManager passengerManager = new PassengerManager();
        List<Passenger> passengers = passengerManager.getPassengers();
        List<Bus> buses = logisticManager.getBuses();
        List<Future<String>> futures = new ArrayList<>();
        for (Bus bus : buses) {
            futures.add(executorService.submit(bus));
        }
        for (Future<String> future : futures) {
            logger.log(INFO, future.get());
        }
        List<Passenger> notArrivedPassengers = passengers.stream().filter(p -> !p.isArrivedAtDestination()).toList();
        boolean success = notArrivedPassengers.isEmpty();
        if (!success) {
            notArrivedPassengers.forEach(p -> logger.log(INFO,
                    "Not arrived: \n" + p + "\ncurrent stop: " + p.getCurrentStop()
                            + "\n destination: " + p.getDestination()));
        }
        Assert.assertTrue(success);
    }
}
