package org.multi.routes.action;

import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.multi.routes.ulils.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NavigateManager {
    private List<BusRoute> routes;

    public NavigateManager(List<BusRoute> routes) {
        this.routes = routes;
    }

    public List<BusStop> getTransferStops(Passenger passenger) {
        if (Validator.isCurrentStopOnOneRouteWithDestination(passenger, routes)) {
            return new ArrayList<>(Collections.singletonList(passenger.getDestination()));
        }

        return null;
    }

    private List<BusStop> getCommonBusStop(BusRoute first, BusRoute second) {
        List<BusStop> firstStops = first.getStops();
        List<BusStop> secondStops = second.getStops();
        return firstStops.stream()
                .filter(secondStops::contains)
                .toList();
    }

    public static void main(String[] args) {
        BusStop busStop1 = new BusStop("1", 2);
        BusStop busStop2 = new BusStop("2", 2);
        BusStop busStop3 = new BusStop("3", 2);
        BusStop busStop4 = new BusStop("4", 2);

        BusRoute route1 = new BusRoute(1, Arrays.asList(busStop1, busStop2, busStop3));
        BusRoute route2 = new BusRoute(2, Arrays.asList( busStop4));
        BusRoute route3 = new BusRoute(3, Arrays.asList(busStop2, busStop3));

        Passenger passenger = new Passenger("pink");
        passenger.setDestination(busStop3);
        busStop1.addPassengerToLine(passenger);
        passenger.setCurrentStop(busStop1);

        NavigateManager navigateManager = new NavigateManager(Arrays.asList(route1, route2, route3));
        System.out.println(navigateManager.getCommonBusStop(route1, route2));
    }
}
