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
import org.multi.routes.ulils.LogisticUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.apache.logging.log4j.Level.INFO;

public class ThreadsTests {
    private final Logger logger = LogManager.getLogger(this);

    @Test
    public void threadsFromFilesTest() throws ExecutionException, InterruptedException {
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

    @Test
    public void customThreadsTest200Times() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 200; i++) {
            BusStop stopA = new BusStop("A", 2);
            BusStop stopB = new BusStop("B", 2);
            BusStop stopC = new BusStop("C", 2);
            BusStop stopD = new BusStop("D", 2);
            BusStop stopE = new BusStop("E", 2);
            BusStop stopF = new BusStop("F", 2);
            BusStop stopG = new BusStop("G", 2);
            BusStop stopH = new BusStop("H", 2);
            BusRoute route1 = new BusRoute(1, Arrays.asList(
                    stopA, stopB, stopC, stopD, stopE
            ));
            BusRoute route2 = new BusRoute(2, Arrays.asList(
                    stopE, stopF, stopG, stopH
            ));
            LogisticUtils.createMap(Arrays.asList(route1, route2));
            NavigateManager navigateManager = new NavigateManager(Arrays.asList(route1, route2));
            Bus bus1 = new Bus(1, 2);
            Bus bus2 = new Bus(2, 2);
            Bus bus3 = new Bus(3, 2);
            Bus bus4 = new Bus(4, 2);
            Bus bus5 = new Bus(5, 2);
            Bus bus6 = new Bus(6, 2);
            bus1.setRoute(route1);
            bus2.setRoute(route1);
            bus3.setRoute(route1);
            bus4.setRoute(route2);
            bus5.setRoute(route2);
            bus6.setRoute(route2);
            List<Bus> buses = new ArrayList<>(Arrays.asList(bus1, bus2, bus3, bus4, bus5, bus6));
            Passenger passenger1 = new Passenger("1");
            Passenger passenger2 = new Passenger("2");
            Passenger passenger3 = new Passenger("3");
            Passenger passenger5 = new Passenger("4");
            Passenger passenger4 = new Passenger("5");
            Passenger passenger6 = new Passenger("6");
            Passenger passenger7 = new Passenger("7");
            List<Passenger> passengers = new ArrayList<>(
                    Arrays.asList(passenger1, passenger2, passenger3, passenger4, passenger5, passenger6, passenger7));
            fillPassengersTrip(passenger1, stopA, stopG, navigateManager);
            fillPassengersTrip(passenger2, stopA, stopB, navigateManager);
            fillPassengersTrip(passenger3, stopA, stopE, navigateManager);
            fillPassengersTrip(passenger4, stopA, stopH, navigateManager);
            fillPassengersTrip(passenger5, stopB, stopH, navigateManager);
            fillPassengersTrip(passenger6, stopE, stopH, navigateManager);
            fillPassengersTrip(passenger7, stopE, stopG, navigateManager);
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            List<Future<String>> futures = new ArrayList<>();
            for (Bus bus : buses) {
                futures.add(executorService.submit(bus));
            }
            for (Future<String> stringFuture : futures) {
                logger.log(INFO, stringFuture.get());
            }
            for (Passenger passenger : passengers) {
                logger.log(INFO, passenger + " get destination : " + passenger.isArrivedAtDestination());
                Assert.assertTrue(passenger.isArrivedAtDestination());
            }
        }
    }

    private void fillPassengersTrip(Passenger passenger, BusStop currentStop, BusStop destination
            , NavigateManager navigateManager) {
        passenger.setDestination(destination);
        passenger.setCurrentStop(currentStop);
        currentStop.addPassengerToLine(passenger);
        List<BusStop> transitStops = navigateManager.getTransitStops(passenger);
        passenger.setTransitStops(transitStops);
    }
}