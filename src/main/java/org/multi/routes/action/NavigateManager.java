package org.multi.routes.action;

import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;
import org.multi.routes.ulils.LogisticUtils;
import org.multi.routes.ulils.Validator;

import java.util.*;

public class NavigateManager {
    private final List<BusRoute> routes;

    public NavigateManager(List<BusRoute> routes) {
        this.routes = routes;
    }

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
        Set<BusRoute> visited = new HashSet<>();

        routeQueue.add(start);
        transitStops.add(new ArrayList<>());
        visited.add(start);

        while (!routeQueue.isEmpty()) {
            BusRoute currentRoute = routeQueue.poll();
            List<BusStop> currentTransit = transitStops.poll();

            for (Map.Entry<BusRoute, BusStop> next : currentRoute.getNextAccessibleRoutes().entrySet()) {
                BusRoute nextRoute = next.getKey();
                BusStop transitStop = next.getValue();
                if (visited.contains(nextRoute)) {
                    continue;
                }
                List<BusStop> newStops = new ArrayList<>(currentTransit);
                newStops.add(transitStop);
                if (nextRoute.equals(destination)) {
                    return newStops;
                }
                routeQueue.add(nextRoute);
                transitStops.add(newStops);
                visited.add(nextRoute);
            }
        }
        return new ArrayList<>();
    }
}