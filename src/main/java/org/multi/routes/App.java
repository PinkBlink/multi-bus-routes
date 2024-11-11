package org.multi.routes;

import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;
import org.multi.routes.entity.Passenger;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {

        BusStop stop1 = new BusStop("1", 2);
        BusStop stop2 = new BusStop("2", 2);
        BusStop stop3 = new BusStop("3", 2);

        Passenger passenger1 = new Passenger("1", stop3);


        BusRoute route1 = new BusRoute(1, Arrays.asList(stop1, stop2, stop3));

        Bus bus1 = new Bus(1, route1, 2);


    }
}
