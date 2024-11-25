package org.multi.routes.action;

import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.*;

public class NavigateManager {
    private HashMap<BusStop,List<BusRoute>> allPossibleTransitStopsToRoutes = new HashMap<>();
    private List<BusRoute> routes;

    public NavigateManager(List<BusRoute> routes) {
        this.routes = routes;
    }

    private List<BusStop> getCommonBusStop(BusRoute first, BusRoute second) {
        List<BusStop> firstStops = first.getStops();
        List<BusStop> secondStops = second.getStops();
        return firstStops.stream()
                .filter(secondStops::contains)
                .toList();
    }

    private void fillPossibleTransitStopsToRoutes(){
        List<BusStop> transitStops = getAllPossibleTransitStops();
        for(BusStop stop: transitStops){
            List<BusRoute> routesWithTransitStop = getAllRoutesWithStop(stop);
            allPossibleTransitStopsToRoutes.put(stop,routesWithTransitStop);
        }
    }

    private List<BusRoute> getAllRoutesWithStop(BusStop stop){
        return routes.stream().filter(r-> r.contain(stop)).toList();
    }


    private List<BusRoute> getPotentialDesireRoutes(BusStop stop) {
        return routes.stream().filter(r -> r.contain(stop) && !r.getStops().getFirst().equals(stop)).toList();
    }


    private void getTransitRoute(Passenger passenger) {
        BusStop currentStop = passenger.getCurrentStop();
        BusStop destination = passenger.getDestination();
        BusRoute currentRoute = getCurrentPassengerRote(currentStop);
        List<BusRoute> potentialDesireRoutes = getPotentialDesireRoutes(destination);

        BusStop potentialPosition = passenger.getCurrentStop();
        BusRoute potentialPositionRoute = getCurrentPassengerRote(potentialPosition);


        List<BusStop> transitStops = new ArrayList<>();

        while(!potentialDesireRoutes.contains(potentialPositionRoute)){

        }
    }

    private BusRoute getCurrentPassengerRote(BusStop stop) {
        return routes.stream()
                .filter(r -> r.contain(stop) && !r.getStops().getLast().equals(stop))
                .toList().getLast();
    }

    private List<BusStop> getAllPossibleTransitStops() {
        List<BusStop> transits = new ArrayList<>();
        for (int i = 0; i < routes.size() - 1; i++) {
            BusRoute currentStops = routes.get(i);
            BusRoute nextStops = routes.get(i + 1);
            List<BusStop> commonStops = getCommonBusStop(currentStops, nextStops);
            for (BusStop stop : commonStops) {
                if (!transits.contains(stop)) {
                    transits.add(stop);
                }
            }
        }
        return transits;
    }

    public static void main(String[] args) {
        BusStop busStop1 = new BusStop("1", 2);
        BusStop busStop2 = new BusStop("2", 2);
        BusStop busStop3 = new BusStop("3", 2);
        BusStop busStop4 = new BusStop("4", 2);
        BusStop busStop5 = new BusStop("5", 2);
        BusStop busStop6 = new BusStop("6", 2);
        BusStop busStop7 = new BusStop("7", 2);
        BusStop busStop8 = new BusStop("8", 2);
        BusStop busStop9 = new BusStop("9", 2);
        BusStop busStop10 = new BusStop("10", 2);

        BusRoute route1 = new BusRoute(1, Arrays.asList(busStop1, busStop2, busStop3));
        BusRoute route2 = new BusRoute(2, Arrays.asList(busStop3, busStop5));
        BusRoute route3 = new BusRoute(3, Arrays.asList(busStop4, busStop5, busStop6));
        BusRoute route4 = new BusRoute(4, Arrays.asList(busStop6, busStop7, busStop8));
        BusRoute route5 = new BusRoute(5, Arrays.asList(busStop6, busStop9, busStop10));

        Passenger passenger = new Passenger("pink");
        passenger.setDestination(busStop3);
        busStop1.addPassengerToLine(passenger);
        passenger.setCurrentStop(busStop1);

        NavigateManager navigateManager = new NavigateManager(Arrays.asList(route1, route2, route3, route4,
                route5));
//        System.out.println(navigateManager.getAllPossibleTransitStops());
//        System.out.println("Should be : " + busStop3 + ", " + busStop5 + ", " + busStop6);
        System.out.println(navigateManager.getCurrentPassengerRote(busStop3));
    }
}
