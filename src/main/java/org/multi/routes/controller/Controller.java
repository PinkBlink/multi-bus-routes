package org.multi.routes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.model.*;
import org.multi.routes.repository.FileDataRepository;
import org.multi.routes.service.DataEntityParser;
import org.multi.routes.service.impl.DataEntityParserImpl;

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
        DataEntityParser dataEntityParser = new DataEntityParserImpl();
        FileDataRepository fileDataRepository = FileDataRepository.getInstance(dataEntityParser);

        List<Bus> buses = fileDataRepository.getBusesFromData();
        List<Passenger> passengers = fileDataRepository.getPassengersFromData();
        List<BusStop> stops = fileDataRepository.getBusStopsFromData();
        List<BusRoute> busRoutes = fileDataRepository.getBusRoutesFromData();
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
        fileDataRepository.getBusesFromData().forEach(entity -> logger.log(INFO, entity.getId()));
        fileDataRepository.getBusStopsFromData().forEach(entity -> logger.log(INFO, entity.getId()));
        fileDataRepository.getBusRoutesFromData().forEach(entity -> logger.log(INFO, entity.getId()));
        fileDataRepository.getPassengersFromData().forEach(entity -> logger.log(INFO, entity.getId()));
    }
}