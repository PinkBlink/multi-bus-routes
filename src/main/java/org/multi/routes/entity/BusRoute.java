package org.multi.routes.entity;

import java.util.List;

public class BusRoute {
    private final int routeNumber;
    private final List<BusStop> route;

    public BusRoute(int routeNumber, List<BusStop> route) {
        this.routeNumber = routeNumber;
        this.route = route;
    }

    public List<BusStop> getRoute() {
        return route;
    }
}
