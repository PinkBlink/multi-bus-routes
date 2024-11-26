package org.multi.routes;

import org.multi.routes.action.NavigateManager;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NavigateManagerTests {
    NavigateManager navigateManager;
    List<BusRoute> routeList = new ArrayList<>();
    Passenger passenger1;
    Passenger passenger2;
    Passenger passenger3;
    Passenger passenger4;
    Passenger passenger5;
    List<BusStop> expectedTransitionStops1;
    List<BusStop> expectedTransitionStops2;
    List<BusStop> expectedTransitionStops3;
    List<BusStop> expectedTransitionStops4;
    List<BusStop> expectedTransitionStops5;

    @BeforeTest
    public void setUp() {
        BusStop stop1 = new BusStop("1", 2);
        BusStop stop2 = new BusStop("2", 3);
        BusStop stop3 = new BusStop("3", 2);
        BusStop stop4 = new BusStop("4", 4);
        BusStop stop5 = new BusStop("5", 2);
        BusStop stop6 = new BusStop("6", 3);
        BusStop stop7 = new BusStop("7", 2);
        BusStop stop8 = new BusStop("8", 4);
        BusStop stop9 = new BusStop("9", 2);
        BusStop stop10 = new BusStop("10", 3);

        BusRoute route1 = new BusRoute(1, Arrays.asList(stop1, stop2, stop3));
        BusRoute route2 = new BusRoute(2, Arrays.asList(stop3, stop4, stop5));
        BusRoute route3 = new BusRoute(3, Arrays.asList(stop5, stop6, stop7));
        BusRoute route4 = new BusRoute(4, Arrays.asList(stop7, stop8, stop9));
        BusRoute route5 = new BusRoute(5, Arrays.asList(stop9, stop10));
        routeList.addAll(Arrays.asList(route1, route2, route3, route4, route5));
        navigateManager = new NavigateManager(routeList);


        passenger1 = new Passenger("1");
        passenger1.setCurrentStop(stop1);
        passenger1.setDestination(stop6);
        expectedTransitionStops1 = Arrays.asList(stop3, stop5);

        passenger2 = new Passenger("2");
        passenger2.setCurrentStop(stop2);
        passenger2.setDestination(stop7);
        expectedTransitionStops2 = Arrays.asList(stop3, stop5);

        passenger3 = new Passenger("3");
        passenger3.setCurrentStop(stop1);
        passenger3.setDestination(stop10);
        expectedTransitionStops3 = Arrays.asList(stop3, stop5, stop7, stop9);

        passenger4 = new Passenger("4");
        passenger4.setCurrentStop(stop6);
        passenger4.setDestination(stop9);
        expectedTransitionStops4 = List.of(stop7);

        passenger5 = new Passenger("5");
        passenger5.setCurrentStop(stop8);
        passenger5.setDestination(stop10);
        expectedTransitionStops5 = List.of(stop9);
    }

    @Test
    public void getTransitStops1() {
        List<BusStop> actual = navigateManager.getTransitStops(passenger1);
        Assert.assertEquals(actual, expectedTransitionStops1);
    }

    @Test
    public void getTransitStops2() {
        List<BusStop> actual = navigateManager.getTransitStops(passenger2);
        Assert.assertEquals(actual, expectedTransitionStops2);
    }

    @Test
    public void getTransitStops3() {
        List<BusStop> actual = navigateManager.getTransitStops(passenger3);
        Assert.assertEquals(actual, expectedTransitionStops3);
    }

    @Test
    public void getTransitStops4() {
        List<BusStop> actual = navigateManager.getTransitStops(passenger4);
        Assert.assertEquals(actual, expectedTransitionStops4);
    }

    @Test
    public void getTransitStops5() {
        List<BusStop> actual = navigateManager.getTransitStops(passenger5);
        Assert.assertEquals(actual, expectedTransitionStops5);
    }
}