package org.multi.routes;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.impl.EntityParserImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityParserImplTests {
    private List<BusStop> expectedBusStopList;
    private List<Bus> expectedBusList;
    private Map<Passenger, List<String>> expectedPassengerMap = new HashMap<>();

    @BeforeTest
    public void setUp() {
        expectedPassengerMap = Map.of(
                new Passenger("James Anderson"), List.of("1", "9"),
                new Passenger("Emily Parker"), List.of("2", "3"),
                new Passenger("Michael Bennett"), List.of("2", "7"),
                new Passenger("Sophia Harris"), List.of("1", "3"),
                new Passenger("William Carter"), List.of("2", "8"),
                new Passenger("Olivia Morgan"), List.of("4", "9"),
                new Passenger("Alexander Scott"), List.of("1", "3"),
                new Passenger("Charlotte Thompson"), List.of("4", "10"),
                new Passenger("Benjamin Wright"), List.of("3", "6"),
                new Passenger("Amelia Collins"), List.of("4", "10")
        );
        expectedBusList = Arrays.asList(new Bus(1, 2)
                , new Bus(2, 4)
                , new Bus(3, 2)
                , new Bus(4, 2)
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
        Assert.assertEquals(EntityParserImpl.getBusesFromData(), expectedBusList);
    }

    @Test
    public void testGetBusStopsFromData() {
        Assert.assertEquals(EntityParserImpl.getBusStopsFromData(), expectedBusStopList);
    }

    @Test
    public void testGetPassengersFromData() {
        Assert.assertEquals(EntityParserImpl.getPassengersFromData(), expectedPassengerMap);
    }
}

