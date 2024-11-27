package org.multi.routes.ulils;

import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;

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

    private static List<BusStop> getTransitStopForNextRoute(BusRoute current, BusRoute next) {
        List<BusStop> currentStops = current.getStops();
        List<BusStop> nextStops = next.getStops();
        return currentStops.stream()
                .filter(s -> nextStops.contains(s)
                        && !currentStops.getFirst().equals(s)
                        && !nextStops.getLast().equals(s)).toList();
    }
}
