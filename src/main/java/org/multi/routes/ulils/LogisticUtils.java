package org.multi.routes.ulils;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.BusStop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogisticUtils {
    public static void createMap(List<BusRoute> routes) {
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

    public static List<BusStop> getMergedStops(BusStop... busStops) {
        return new ArrayList<>(Arrays.asList(busStops));
    }

    public static List<BusRoute> getPotentialDesireRoutes(BusStop stop, List<BusRoute> routes) {
        return routes.stream().filter(r -> r.containsStop(stop) && !r.getStops().getFirst().equals(stop)).toList();
    }

    private static List<BusStop> getTransitStopForNextRoute(BusRoute current, BusRoute next) {
        List<BusStop> currentStops = current.getStops();
        List<BusStop> nextStops = next.getStops();
        return currentStops.stream()
                .filter(s -> nextStops.contains(s)
                        && !currentStops.getFirst().equals(s)
                        && !nextStops.getLast().equals(s)).toList();
    }

    public static BusRoute getCurrentPassengerRoute(BusStop stop, List<BusRoute> routes) {
        return routes.stream()
                .filter(r -> r.containsStop(stop) && !r.getStops().getLast().equals(stop))
                .toList().getLast();
    }
}