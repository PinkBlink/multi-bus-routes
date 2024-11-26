package org.multi.routes;

import org.multi.routes.action.LogisticManager;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogisticManagerTests {
    private final LogisticManager logisticManager = LogisticManager.getInstance();
    private final List<Bus> expectedBuses = new ArrayList<>();
    private final List<BusRoute> expectedRoutes = new ArrayList<>();

    @BeforeTest
    public void setUp() {
        BusStop stop1 = new BusStop("1", 2);
        BusStop stop2 = new BusStop("2", 3);
        BusStop stop3 = new BusStop("3", 1);
        BusStop stop4 = new BusStop("4", 1);
        BusStop stop5 = new BusStop("5", 3);
        BusStop stop6 = new BusStop("6", 4);
        BusStop stop7 = new BusStop("7", 4);
        BusStop stop8 = new BusStop("8", 4);
        BusStop stop9 = new BusStop("9", 2);
        BusStop stop10 = new BusStop("10", 2);
        BusRoute route1 = new BusRoute(1, Arrays.asList(stop1, stop2, stop3, stop4, stop5, stop6));
        BusRoute route2 = new BusRoute(2, Arrays.asList(stop7, stop3, stop4, stop8));
        BusRoute route3 = new BusRoute(3, Arrays.asList(stop8, stop4, stop5, stop9, stop10));
        Bus bus1 = new Bus(1, 2);
        Bus bus2 = new Bus(2, 4);
        Bus bus3 = new Bus(3, 1);
        Bus bus4 = new Bus(4, 1);
        Bus bus5 = new Bus(5, 2);
        bus1.setRoute(route1);
        bus2.setRoute(route1);
        bus3.setRoute(route2);
        bus4.setRoute(route2);
        bus4.setRoute(route3);
        expectedBuses.addAll(Arrays.asList(bus1, bus2, bus3, bus4, bus5));
        expectedRoutes.addAll(Arrays.asList(route1, route2, route3));
    }

    @Test
    public void getInstanceTest() {
        LogisticManager logisticManagerCopy = LogisticManager.getInstance();
        Assert.assertEquals(logisticManagerCopy, logisticManager);
    }

    @Test
    public void getBusesTest() {
        List<Bus> actualBuses = logisticManager.getBuses();
        Assert.assertEquals(actualBuses, expectedBuses);
    }

    @Test
    public void getRoutesTest() {
        List<BusRoute> actualRoutes = logisticManager.getRoutes();
        System.out.println(logisticManager.getRoutes());
        Assert.assertEquals(actualRoutes, expectedRoutes);
    }
}