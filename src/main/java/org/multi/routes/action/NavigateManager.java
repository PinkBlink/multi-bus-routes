package org.multi.routes.action;

import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.*;

public class NavigateManager {
    private final List<BusRoute> routes;

    public NavigateManager(List<BusRoute> routes) {
        this.routes = routes;
        createMap();
    }

    public List<BusStop> getTransitStops(Passenger passenger) {
        BusRoute desireRoute = getPotentialDesireRoutes(passenger.getDestination()).getFirst();
        BusRoute passengerRoute = getCurrentPassengerRoute(passenger.getCurrentStop());
        return getTransitionStops(passengerRoute, desireRoute);
    }

    private void createMap() {
        for (int i = 0; i < routes.size(); i++) {
            BusRoute currentRoute = routes.get(i);
            for (int j = 0; j < routes.size(); j++) {
                if (i == j) {
                    continue;
                }
                BusRoute nextRoute = routes.get(j);
                List<BusStop> possibleTransitStops = getTransitStopForNextRoute(currentRoute, nextRoute);
                if (!possibleTransitStops.isEmpty()) {
                    currentRoute.addNextRoute(nextRoute, possibleTransitStops.getFirst());
                }
            }
        }
    }

    private List<BusStop> getTransitStopForNextRoute(BusRoute current, BusRoute next) {
        List<BusStop> currentStops = current.getStops();
        List<BusStop> nextStops = next.getStops();
        return currentStops.stream()
                .filter(s -> nextStops.contains(s)
                        && !currentStops.getFirst().equals(s)
                        && !nextStops.getLast().equals(s)).toList();
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

    private List<BusRoute> getPotentialDesireRoutes(BusStop stop) {
        return routes.stream().filter(r -> r.containsStop(stop) && !r.getStops().getFirst().equals(stop)).toList();
    }

    private BusRoute getCurrentPassengerRoute(BusStop stop) {
        return routes.stream()
                .filter(r -> r.containsStop(stop) && !r.getStops().getLast().equals(stop))
                .toList().getLast();
    }
}