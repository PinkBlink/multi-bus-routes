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

    }

    private BusStop getCommonBusStop(BusRoute first, BusRoute second) {
        List<BusStop> firstStops = first.getStops();
        List<BusStop> secondStops = second.getStops();
        List<BusStop> result = Stream.of(firstStops, secondStops).filter((BusStop s1, BusStop s2) -> s1.equals(s2)).collect(Collectors.toList());
        return result.getLast();
    }
}
