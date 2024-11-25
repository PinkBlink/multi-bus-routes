package org.multi.routes;

import org.multi.routes.action.NavigateManager;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ThreadsTests {
    private BusStop busStop1 = new BusStop("1", 2);
    private BusStop busStop2 = new BusStop("2", 2);
    private BusStop busStop3 = new BusStop("3", 2);
    private BusStop busStop4 = new BusStop("4", 2);
    private BusStop busStop5 = new BusStop("5", 2);
    private BusStop busStop6 = new BusStop("6", 2);
    private BusStop busStop7 = new BusStop("7", 2);
    private BusStop busStop8 = new BusStop("8", 2);
    private BusStop busStop9 = new BusStop("9", 2);
    private BusStop busStop10 = new BusStop("10", 2);

    private BusRoute route1 = new BusRoute(1, Arrays.asList(busStop1, busStop2, busStop3));
    private BusRoute route2 = new BusRoute(2, Arrays.asList(busStop3, busStop5));
    private BusRoute route3 = new BusRoute(3, Arrays.asList(busStop4, busStop5, busStop6));
    private BusRoute route4 = new BusRoute(4, Arrays.asList(busStop6, busStop7, busStop8));
    private BusRoute route5 = new BusRoute(5, Arrays.asList(busStop6, busStop9, busStop10));

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
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

        BusRoute route1 = new BusRoute(1, Arrays.asList(busStop1, busStop2));
        BusRoute route2 = new BusRoute(2, Arrays.asList(busStop2, busStop3, busStop5));
        BusRoute route3 = new BusRoute(3, Arrays.asList(busStop4, busStop5, busStop6));
        BusRoute route4 = new BusRoute(4, Arrays.asList(busStop6, busStop7, busStop8));
        BusRoute route5 = new BusRoute(5, Arrays.asList(busStop6, busStop9, busStop10));
        NavigateManager navigateManager = new NavigateManager(Arrays.asList(route1, route2, route3, route4,
                route5));
        Bus bus1 = new Bus(1, 2);
        Bus bus2 = new Bus(2, 2);
        Bus bus3 = new Bus(3, 2);
        Bus bus4 = new Bus(4, 2);
        bus1.setRoute(route1);
        bus2.setRoute(route2);
        bus3.setRoute(route2);
        bus4.setRoute(route2);

        Passenger passenger1 = new Passenger("1");
        passenger1.setDestination(busStop3);
        passenger1.setTransitStops(new ArrayList<>(List.of(busStop2)));
        busStop1.addPassengerToLine(passenger1);
        passenger1.setCurrentStop(busStop1);

        Passenger passenger2 = new Passenger("2");
        passenger2.setDestination(busStop3);


        Future<String> str1 = executorService.submit(bus1);
        TimeUnit.SECONDS.sleep(2);
        Future<String> str2 = executorService.submit(bus2);
        Future<String> str3 = executorService.submit(bus3);
        Future<String> str4 = executorService.submit(bus4);
        try {
            System.out.println(str1.get() + str2.get() + str3.get() + str4.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
    }
}
