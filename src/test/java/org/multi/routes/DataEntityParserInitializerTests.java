package org.multi.routes;

import org.multi.routes.model.Bus;
import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.DataEntityParser;
import org.multi.routes.service.impl.DataEntityInitializerImpl;
import org.multi.routes.service.impl.DataEntityParserImpl;
import org.multi.routes.ulils.LogisticUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;

public class DataEntityParserInitializerTests {
    private DataEntityParser dataEntityParser = new DataEntityParserImpl();
    private DataEntityInitializerImpl dataEntityInitializerImpl = DataEntityInitializerImpl.getInstance();
    private List<BusStop> expectedBusStopList;
    private List<Bus> expectedBusList;
    private List<BusRoute> expectedBusRouteList;
    private List<Passenger> expectedPassengers;
    private List<BusStop> actualBusStops = dataEntityInitializerImpl.getBusStops();
    private List<BusRoute> actualBusRoutes = dataEntityInitializerImpl.getBusRoutes();
    private List<Bus> actualBuses = dataEntityInitializerImpl.getBuses();
    private List<Passenger> actualPassengers = dataEntityInitializerImpl.getPassengers();

    @BeforeTest
    public void setUp() {
        expectedBusList = Arrays.asList(
                new Bus(1, 2, 6, new HashSet<>())
                , new Bus(2, 4, 6, new HashSet<>())
                , new Bus(3, 2, 6, new HashSet<>())
                , new Bus(4, 2, 6, new HashSet<>())
                , new Bus(5, 2, 6, new HashSet<>()));

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
        expectedPassengers = Arrays.asList(
                new Passenger("James Anderson")
                , new Passenger("Emily Parker")
                , new Passenger("Michael Bennett")
                , new Passenger("Sophia Harris")
                , new Passenger("William Carter")
                , new Passenger("Olivia Morgan")
                , new Passenger("Alexander Scott")
                , new Passenger("Charlotte Thompson")
                , new Passenger("Benjamin Wright")
                , new Passenger("Amelia Collins")
        );
        expectedBusRouteList = Arrays.asList(
                new BusRoute(1,
                        Arrays.asList(
                                new BusStop("1", 2),
                                new BusStop("2", 3),
                                new BusStop("3", 1))
                        , new HashMap<>()),
                new BusRoute(2
                        , Arrays.asList(
                        new BusStop("3", 1),
                        new BusStop("5", 3))
                        , new HashMap<>()),
                new BusRoute(3,
                        Arrays.asList(
                                new BusStop("4", 1),
                                new BusStop("5", 3),
                                new BusStop("6", 4))
                        , new HashMap<>()),
                new BusRoute(4,
                        Arrays.asList(
                                new BusStop("6", 4),
                                new BusStop("7", 4),
                                new BusStop("8", 4))
                        , new HashMap<>()),
                new BusRoute(5,
                        Arrays.asList(
                                new BusStop("6", 4),
                                new BusStop("9", 2),
                                new BusStop("10", 2))
                        , new HashMap<>())
        );
        LogisticUtils.createMap(expectedBusRouteList);
    }

    @Test
    public void testGetBusesFromData() {
        Assert.assertEquals(actualBuses, expectedBusList);
    }

    @Test
    public void testGetBusStopsFromData() {
        Assert.assertEquals(actualBusStops, expectedBusStopList);
    }

    @Test
    public void testGetPassengersFromData() {
        Assert.assertEquals(actualPassengers.stream().map(Passenger::getName).toList()
                , expectedPassengers.stream().map(Passenger::getName).toList());
    }

    @Test
    public void testGetRoutesFromData() {
        Assert.assertEquals(actualBusRoutes,expectedBusRouteList);
    }
}