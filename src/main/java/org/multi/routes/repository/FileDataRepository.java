package org.multi.routes.repository;

import org.multi.routes.model.BusRoute;
import org.multi.routes.model.Passenger;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.service.DataEntityParser;
import org.multi.routes.ulils.LogisticUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class FileDataRepository {
    private final static ReentrantLock lock = new ReentrantLock();
    private static FileDataRepository instance;
    private List<Bus> busesFromData;
    private List<BusStop> busStopsFromData;

    private List<BusRoute> busRoutesFromData;
    private List<Passenger> passengersFromData;
    private static AtomicBoolean isInstanceCreated = new AtomicBoolean(false);

    private FileDataRepository(DataEntityParser dataEntityParser) {
        busStopsFromData = dataEntityParser.getBusStopsFromData();
        busRoutesFromData = dataEntityParser.getBusRoutesFromData(busStopsFromData);
        LogisticUtils.createMap(busRoutesFromData);
        busesFromData = dataEntityParser.getBusesFromData(busRoutesFromData);
        passengersFromData = dataEntityParser.getPassengersFromData(busStopsFromData, busRoutesFromData);
    }

    public static FileDataRepository getInstance(DataEntityParser dataEntityParser) {
        if (!isInstanceCreated.get()) {
            lock.lock();
            try {
                if (!isInstanceCreated.get()) {
                    instance = new FileDataRepository(dataEntityParser);
                    isInstanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public List<Bus> getBusesFromData() {
        return busesFromData;
    }

    public List<BusStop> getBusStopsFromData() {
        return busStopsFromData;
    }

    public List<BusRoute> getBusRoutesFromData() {
        return busRoutesFromData;
    }

    public List<Passenger> getPassengersFromData() {
        return passengersFromData;
    }
}