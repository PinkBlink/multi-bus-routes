package org.multi.routes.service;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;

public interface BusRouteService {
    public void addNextRoute(BusRoute currentRoute, BusRoute nextRoute, BusStop transitStop);

    public boolean containsStop(BusRoute route, BusStop stop);

}
