package org.multi.routes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BusRoute {
    private final int routeNumber;
    private final List<BusStop> route;

    private final Map<BusRoute, BusStop> nextAccessibleRoutes = new HashMap<>();

    public BusRoute(int routeNumber, List<BusStop> route) {
        this.routeNumber = routeNumber;
        this.route = route;
    }

    public Map<BusRoute, BusStop> getNextAccessibleRoutes() {
        return nextAccessibleRoutes;
    }


    public List<BusStop> getStops() {
        return route;
    }

    public void addNextRoute(BusRoute route, BusStop transitStop) {
        nextAccessibleRoutes.put(route, transitStop);
    }

    public boolean containsStop(BusStop stop) {
        return route.contains(stop);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusRoute route1)) {
            return false;
        }
        return routeNumber == route1.routeNumber
                && Objects.equals(route, route1.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeNumber, route);
    }

    @Override
    public String toString() {
        return "BusRoute:" + routeNumber + " stops: " + route;
    }
}