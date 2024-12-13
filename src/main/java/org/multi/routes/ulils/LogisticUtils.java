package org.multi.routes.ulils;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;
import org.multi.routes.service.BusRouteService;
import org.multi.routes.service.impl.BusRouteServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class LogisticUtils {
    private static BusRouteService busRouteService = new BusRouteServiceImpl();

    public static List<BusRoute> createMap(List<BusRoute> routes) {
        for (int i = 0; i < routes.size(); i++) {
            BusRoute currentRoute = routes.get(i);
            for (int j = 0; j < routes.size(); j++) {
                if (i == j) {
                    continue;
                }
                BusRoute nextRoute = routes.get(j);
                List<BusStop> possibleTransitStops = getTransitStopForNextRoute(currentRoute, nextRoute);
                if (!possibleTransitStops.isEmpty()) {
                    busRouteService.addNextRoute(currentRoute, nextRoute, possibleTransitStops.getFirst());
                }
            }
        }
        return routes;
    }

    public static List<BusRoute> getPotentialDesireRoutes(BusStop stop, List<BusRoute> routes) {
        return routes.stream().filter(r -> busRouteService.containsStop(r, stop) && !r.getStops().getFirst().equals(stop)).toList();
    }

    private static List<BusStop> getTransitStopForNextRoute(BusRoute current, BusRoute next) {
        List<BusStop> currentStops = current.getStops();
        List<BusStop> nextStops = next.getStops();
        return currentStops.stream()
                .filter(s -> nextStops.contains(s)
                        && !currentStops.getFirst().equals(s)
                        && !nextStops.getLast().equals(s)).toList();
    }

    public static BusRoute getCurrentPassengerRoute(BusRouteService busRouteService, BusStop stop, List<BusRoute> routes) {
        return routes.stream()
                .filter(route -> busRouteService.containsStop(route, stop) && !route.getStops().getLast().equals(stop))
                .toList().getLast();
    }

    public static BusStop findBusStopByName(String name, List<BusStop> stops) {
        return stops.stream()
                .filter(stop -> stop.getStopName().equals(name))
                .findFirst().get();
    }

    public static List<BusStop> findBusesByNames(List<BusStop> stops, String... names) {
        List<BusStop> result = new ArrayList<>();
        for (String name : names) {
            result.add(findBusStopByName(name, stops));
        }
        return result;
    }

    public static BusRoute findRouteByNumber(int routeNumber, List<BusRoute> routes) {
        return routes.stream().filter(route -> route.getRouteNumber() == routeNumber)
                .findFirst().get();
    }
}