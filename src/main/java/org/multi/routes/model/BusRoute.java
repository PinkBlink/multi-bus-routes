package org.multi.routes.model;

import org.multi.routes.ulils.IdGenerator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BusRoute {
    private int id;
    private int routeNumber;
    private List<BusStop> route;

    private final Map<BusRoute, BusStop> nextAccessibleRoutes;

    public BusRoute(int routeNumber, List<BusStop> route, Map<BusRoute, BusStop> nextAccessibleRoutes) {
        this.routeNumber = routeNumber;
        this.route = route;
        this.nextAccessibleRoutes = nextAccessibleRoutes;
        this.id = IdGenerator.getNewId();
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public Map<BusRoute, BusStop> getNextAccessibleRoutes() {
        return nextAccessibleRoutes;
    }


    public List<BusStop> getStops() {
        return route;
    }

    public int getId() {
        return id;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public void setRoute(List<BusStop> route) {
        this.route = route;
    }

    public void setId(int id) {
        this.id = id;
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
        return Objects.hash(routeNumber, route, id);
    }

    @Override
    public String toString() {
        return "BusRoute:" + routeNumber + " stops: " + route;
    }
}