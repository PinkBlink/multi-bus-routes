package org.multi.routes;

import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.multi.routes.ulils.Validator;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ValidatorTests {
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
        validPassenger = "passenger: Sophia Harris";
        invalidPassenger = "passenger:Eee";
        validBusStop = "bus_stop: 1 max_buses: 2";
        invalidBusStop = "stop 1 max 4";
        validBus = "bus_number: 3 max_passengers: 1";
        invalidBus = "1 14";
        stop1 = new BusStop("1", 2);
        stop2 = new BusStop("2", 2);
        BusStop stop3 = new BusStop("3", 2);
        BusStop stop4 = new BusStop("4", 1);
        BusRoute busRoute = new BusRoute(1, Arrays.asList(stop1, stop2, stop3));
        emptyBus = new Bus(1, busRoute, 2, stop1);
        fullBus = new Bus(1, busRoute, 1, stop1);
        passenger1 = new Passenger("1");
        passenger1.setCurrentStop(stop1);
        passenger1.setDestination(stop3);
        passenger2 = new Passenger("2");
        passenger1.setCurrentStop(stop1);
        passenger2.setDestination(stop4);
        fullBus.addPassengerToBus(passenger1);
        fullBus.addPassengerToBus(passenger2);
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
        stop1.addBusToStop(emptyBus);
        stop1.addBusToStop(fullBus);
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