package org.multi.routes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.model.*;
import org.multi.routes.repository.impl.BusRepositoryImpl;
import org.multi.routes.repository.impl.BusRouteRepositoryImpl;
import org.multi.routes.repository.impl.BusStopsRepositoryImpl;
import org.multi.routes.repository.impl.PassengerRepositoryImpl;
import org.multi.routes.service.*;
import org.multi.routes.service.impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.apache.logging.log4j.Level.*;

public class Controller {
    private final static Logger logger = LogManager.getLogger(Controller.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        DataEntityInitializerImpl dataEntityInitializerImpl = DataEntityInitializerImpl.getInstance();
        BusStopService busStopService = new BusStopServiceImpl();
        BusService busService = new BusServiceImpl();
        BusRouteService busRouteService = new BusRouteServiceImpl();
        PassengerService passengerService = new PassengerServiceImpl();

        busStopService.setBusStopRepository(new BusStopsRepositoryImpl(dataEntityInitializerImpl));
        busService.setBusRepository(new BusRepositoryImpl(dataEntityInitializerImpl));
        busRouteService.setBusRouteRepository(new BusRouteRepositoryImpl(dataEntityInitializerImpl));
        passengerService.setPassengerRepository(new PassengerRepositoryImpl(dataEntityInitializerImpl));

        List<Bus> buses = busService.getBuses();
        List<Passenger> passengers = passengerService.getPassengers();
        List<BusStop> stops = busStopService.getBusStops();
        List<BusRoute> busRoutes = busRouteService.getBusRoutes();
        busRoutes.forEach(r -> logger.log(INFO, r));
        List<Future<String>> futures = new ArrayList<>();
        for (Bus bus : buses) {
            futures.add(executorService.submit(bus));
        }
        for (Future<String> future : futures) {
            logger.log(INFO, future.get());
        }
        executorService.shutdown();

        passengers.forEach(p -> logger.log(INFO, p + " is arrived: " + p.isArrivedAtDestination()));

        List<Passenger> notArrivedPassengers = passengers.stream().filter(p -> !p.isArrivedAtDestination()).toList();
        notArrivedPassengers.forEach(p -> logger.log(ERROR, p + " Current stop" + p.getCurrentStop()
                + "\nDestination: " + p.getDestination()));

        buses.forEach(bus -> logger.log(INFO, bus + " passengers " + bus.getPassengers()));
        stops.forEach(stop -> logger.log(INFO, stop + " " + stop.getPassengerLine()));
        busService.getBuses().forEach(entity -> logger.log(INFO, entity.getId()));
        busStopService.getBusStops().forEach(entity -> logger.log(INFO, entity.getId()));
        busRouteService.getBusRoutes().forEach(entity -> logger.log(INFO, entity.getId()));
        passengerService.getPassengers().forEach(entity -> logger.log(INFO, entity.getId()));
    }
}