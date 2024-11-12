package org.multi.routes;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.action.BusThread;
import org.multi.routes.entity.Bus;
import org.multi.routes.entity.BusRoute;
import org.multi.routes.entity.BusStop;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {
    private final static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        BusStop stop1 = new BusStop("1", 2);
        BusStop stop2 = new BusStop("2", 1);
        BusStop stop3 = new BusStop("3", 2);
        BusStop stop4 = new BusStop("4", 3);
        BusStop stop5 = new BusStop("5", 1);
        BusStop stop6 = new BusStop("6", 2);


        BusRoute route1 = new BusRoute(1, Arrays.asList(stop1, stop2, stop3, stop4, stop5, stop6));

        Bus bus1 = new Bus(1, route1, 2);
        Bus bus2 = new Bus(2, route1, 2);
        Bus bus3 = new Bus(3, route1, 3);
        Bus bus4 = new Bus(4, route1, 3);
        Bus bus5 = new Bus(5, route1, 3);
        Bus bus6 = new Bus(6, route1, 3);

        BusThread busThread1 = new BusThread(bus1);
        BusThread busThread2 = new BusThread(bus2);
        BusThread busThread3 = new BusThread(bus3);
        BusThread busThread4 = new BusThread(bus4);
        BusThread busThread5 = new BusThread(bus5);
        BusThread busThread6 = new BusThread(bus6);

        try {
            Future<String> busString1 = executorService.submit(busThread1);
            Future<String> busString2 = executorService.submit(busThread2);
            Future<String> busString3 = executorService.submit(busThread3);
            Future<String> busString4 = executorService.submit(busThread4);
            Future<String> busString5 = executorService.submit(busThread5);
            Future<String> busString6 = executorService.submit(busThread6);

            logger.log(Level.INFO, busString1.get());
            logger.log(Level.INFO, busString2.get());
            logger.log(Level.INFO, busString3.get());
            logger.log(Level.INFO, busString4.get());
            logger.log(Level.INFO, busString5.get());
            logger.log(Level.INFO, busString6.get());
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }
}