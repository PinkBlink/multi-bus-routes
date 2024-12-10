package org.multi.routes;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.BusService;
import org.multi.routes.service.BusStopService;
import org.multi.routes.service.impl.BusServiceImpl;
import org.multi.routes.service.impl.BusStopServiceImpl;
import org.multi.routes.ulils.Validator;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ValidatorTests {
    private BusService busService = new BusServiceImpl();
    private BusStopService busStopService = new BusStopServiceImpl();

    private String validPassenger;
    private String invalidPassenger;
    private String validBusStop;
    private String invalidBusStop;
    private String validBus;
    private String invalidBus;
    private BusStop stop1;
    private BusStop stop2;
    private Bus emptyBus;
    private Bus fullBus;
    private Passenger passenger1;
    private Passenger passenger2;


    @BeforeTest
    public void setUp() {
        validPassenger = "passenger: Michael Bennett start: 2 destination: 7";
        invalidPassenger = "passenger:Eee";
        validBusStop = "bus_stop: 1 max_buses: 2";
        invalidBusStop = "stop 1 max 4";
        validBus = "bus_number: 3 max_passengers: 1 bus_route: 2";
        invalidBus = "1 14";
        stop1 = new BusStop("1", 2);
        stop2 = new BusStop("2", 2);
        BusStop stop3 = new BusStop("3", 2);
        BusStop stop4 = new BusStop("4", 1);
        BusRoute busRoute = new BusRoute(1, Arrays.asList(stop1, stop2, stop3), new HashMap<>());
        emptyBus = new Bus(1, 2, 6, new HashSet<>());
        fullBus = new Bus(1, 1, 6, new HashSet<>());
        emptyBus.setRoute(busRoute);
        fullBus.setRoute(busRoute);
        passenger1 = new Passenger("1");
        passenger1.setCurrentStop(stop1);
        passenger1.setDestination(stop3);
        passenger2 = new Passenger("2");
        passenger1.setCurrentStop(stop1);
        passenger2.setDestination(stop4);
        busService.addPassengerToBus(fullBus, passenger1);
        busService.addPassengerToBus(fullBus, passenger2);
    }

    @Test
    public void testIsValidPassengerInput() {
        Assert.assertTrue(Validator.isValidPassengerInput(validPassenger));
        Assert.assertFalse(Validator.isValidPassengerInput(invalidPassenger));
    }

    @Test
    public void testIsValidBusInput() {
        Assert.assertTrue(Validator.isValidBusInput(validBus));
        Assert.assertFalse(Validator.isValidBusInput(invalidBus));
    }

    @Test
    public void testIsValidBusStop() {
        Assert.assertTrue(Validator.isValidBusStopInput(validBusStop));
        Assert.assertFalse(Validator.isValidBusStopInput(invalidBusStop));
    }

    @Test
    public void testIsFullStop() {
        Assert.assertFalse(Validator.isStopFull(stop2));
        busStopService.addBusToStop(stop1, emptyBus);
        busStopService.addBusToStop(stop1, fullBus);
        Assert.assertTrue(Validator.isStopFull(stop1));
    }

    @Test
    public void testWillGetDestination() {
        Assert.assertTrue(Validator.willGetDestination(passenger1, fullBus));
        Assert.assertFalse(Validator.willGetDestination(passenger2, fullBus));
    }

    @Test
    public void testIsBusFull() {
        Assert.assertTrue(Validator.isBusFull(fullBus));
        Assert.assertFalse(Validator.isBusFull(emptyBus));
    }
}