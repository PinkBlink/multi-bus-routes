package org.multi.routes;

import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.multi.routes.ulils.DataParser;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class DataParserTests {
    private List<BusStop> expectedBusStopList;
    private List<Bus> expectedBusList;
    private List<Passenger> expectedPassengerList;

    @BeforeTest
    public void setUp() {
        expectedPassengerList = Arrays.asList(new Passenger("James Anderson")
                , new Passenger("Emily Parker")
                , new Passenger("Michael Bennett")
                , new Passenger("Sophia Harris")
                , new Passenger("William Carter")
                , new Passenger("Olivia Morgan")
                , new Passenger("Alexander Scott")
                , new Passenger("Charlotte Thompson")
                , new Passenger("Benjamin Wright")
                , new Passenger("Amelia Collins"));

        expectedBusList = Arrays.asList(new Bus(1, 2)
                , new Bus(2, 4)
                , new Bus(3, 1)
                , new Bus(4, 1)
                , new Bus(5, 2));

        expectedBusStopList = Arrays.asList(new BusStop("1", 2)
                , new BusStop("2", 3)
                , new BusStop("3", 1)
                , new BusStop("4", 1)
                , new BusStop("5", 3)
                , new BusStop("6", 4)
                , new BusStop("7", 4)
                , new BusStop("8", 4)
                , new BusStop("9", 2)
                , new BusStop("10", 2));
    }

    @Test
    public void testGetBusesFromData() {
        Assert.assertEquals(DataParser.getBusesFromData(), expectedBusList);
    }

    @Test
    public void testGetBusStopsFromData() {
        Assert.assertEquals(DataParser.getBusStopsFromData(), expectedBusStopList);
    }

    @Test
    public void testGetPassengersFromData() {
        Assert.assertEquals(DataParser.getPassengersFromData(), expectedPassengerList);
    }
}

