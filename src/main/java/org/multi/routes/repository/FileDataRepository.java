package org.multi.routes.repository;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.Passenger;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.service.impl.DataEntityInitializerImpl;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class FileDataRepository {
    private final static ReentrantLock lock = new ReentrantLock();
    private static FileDataRepository instance;
    private DataEntityInitializerImpl dataEntityInitializerImpl;
    private static AtomicBoolean isInstanceCreated = new AtomicBoolean(false);

    private FileDataRepository(DataEntityInitializerImpl dataEntityInitializerImpl) {
        this.dataEntityInitializerImpl = dataEntityInitializerImpl;
    }

    public static FileDataRepository getInstance(DataEntityInitializerImpl dataEntityInitializerImpl) {
        if (!isInstanceCreated.get()) {
            lock.lock();
            try {
                if (!isInstanceCreated.get()) {
                    instance = new FileDataRepository(dataEntityInitializerImpl);
                    isInstanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public List<Bus> getBusesFromData() {
        return dataEntityInitializerImpl.getBuses();
    }

    public List<BusStop> getBusStopsFromData() {
        return dataEntityInitializerImpl.getBusStops();
    }

    public List<BusRoute> getBusRoutesFromData() {
        return dataEntityInitializerImpl.getBusRoutes();
    }

    public List<Passenger> getPassengersFromData() {
        return dataEntityInitializerImpl.getPassengers();
    }
}