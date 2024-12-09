package org.multi.routes.service.impl;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.Navigator;
import org.multi.routes.ulils.LogisticUtils;
import org.multi.routes.ulils.Validator;

import java.util.*;

public class NavigatorImpl implements Navigator {
    private final List<BusRoute> routes;

    public NavigatorImpl(List<BusRoute> routes) {
        this.routes = routes;
    }

    @Override
    public List<BusStop> getTransitStops(Passenger passenger) {
        if (!Validator.isNeedTransition(passenger, routes)) {
            return new ArrayList<>();
        }
        BusRoute desireRoute = LogisticUtils.getPotentialDesireRoutes(passenger.getDestination(), routes).getFirst();
        BusRoute passengerRoute = LogisticUtils.getCurrentPassengerRoute(passenger.getCurrentStop(), routes);
        return getTransitionStops(passengerRoute, desireRoute);
    }

    private List<BusStop> getTransitionStops(BusRoute start, BusRoute destination) {
        if (start.equals(destination)) {
            return new ArrayList<>();
        }
        Queue<BusRoute> routeQueue = new ArrayDeque<>();
        Queue<List<BusStop>> transitStops = new ArrayDeque<>();
        Set<BusRoute> visitedRoutes = new HashSet<>();

        routeQueue.add(start);
        transitStops.add(new ArrayList<>());
        visitedRoutes.add(start);

        while (!routeQueue.isEmpty()) {
            BusRoute currentRoute = routeQueue.poll();
            List<BusStop> currentTransit = transitStops.poll();

            for (Map.Entry<BusRoute, BusStop> nextAccessibleRoute : currentRoute.getNextAccessibleRoutes().entrySet()) {
                BusRoute nextRoute = nextAccessibleRoute.getKey();
                BusStop transitStop = nextAccessibleRoute.getValue();
                if (visitedRoutes.contains(nextRoute)) {
                    continue;
                }
                List<BusStop> newStops = new ArrayList<>(currentTransit);
                newStops.add(transitStop);
                if (nextRoute.equals(destination)) {
                    return newStops;
                }
                routeQueue.add(nextRoute);
                transitStops.add(newStops);
                visitedRoutes.add(nextRoute);
            }
        }
        return new ArrayList<>();
    }
}