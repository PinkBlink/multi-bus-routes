package org.multi.routes.repository;

import org.multi.routes.model.Passenger;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.service.DataEntityParser;
import org.multi.routes.service.impl.DataEntityParserImpl;
import org.multi.routes.service.impl.LogisticManager;
import org.multi.routes.ulils.LogisticUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class FileDataRepository {
    private static ReentrantLock lock = new ReentrantLock();
    private static FileDataRepository instance;
    private List<Bus> busesFromData;
    private List<BusStop> busStopsFromData;
    private List<Passenger> passengersFromData;
    private AtomicBoolean isCreate = new AtomicBoolean(false);

    private FileDataRepository() {
        DataEntityParser dataEntityParser = new DataEntityParserImpl();
        busesFromData = dataEntityParser.getBusesFromData();
        busStopsFromData = dataEntityParser.getBusStopsFromData();

    }

    public FileDataRepository getInstance() {
        if (!isCreate.get()) {
            lock.lock();
            try {
                instance = new FileDataRepository();
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private Passenger setCurrentStopAndDestination(Passenger passenger, String currentStopName, String destinationStopName, List<BusStop> stops) {
        BusStop currentBusStop = LogisticUtils.findBusStopByName(stops,currentStopName);
        BusStop destinationStop = LogisticUtils.findBusStopByName(stops,destinationStopName);
        passenger.setCurrentStop(currentBusStop);
        passenger.setDestination(destinationStop);
    }
}
