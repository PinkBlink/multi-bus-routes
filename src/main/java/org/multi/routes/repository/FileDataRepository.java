package org.multi.routes.repository;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.Passenger;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.service.impl.DataEntityInitializer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class FileDataRepository {
    private final static ReentrantLock lock = new ReentrantLock();
    private static FileDataRepository instance;
    private DataEntityInitializer dataEntityInitializer;
    private static AtomicBoolean isInstanceCreated = new AtomicBoolean(false);

    private FileDataRepository(DataEntityInitializer dataEntityInitializer) {
        this.dataEntityInitializer = dataEntityInitializer;
    }

    public static FileDataRepository getInstance(DataEntityInitializer dataEntityInitializer) {
        if (!isInstanceCreated.get()) {
            lock.lock();
            try {
                if (!isInstanceCreated.get()) {
                    instance = new FileDataRepository(dataEntityInitializer);
                    isInstanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public List<Bus> getBusesFromData() {
        return dataEntityInitializer.getBuses();
    }

    public List<BusStop> getBusStopsFromData() {
        return dataEntityInitializer.getBusStops();
    }

    public List<BusRoute> getBusRoutesFromData() {
        return dataEntityInitializer.getBusRoutes();
    }

    public List<Passenger> getPassengersFromData() {
        return dataEntityInitializer.getPassengers();
    }
}