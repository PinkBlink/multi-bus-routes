package org.multi.routes.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BusStop {
    private final Lock lock = new ReentrantLock();
    private final String stopName;
    private final int maxBussesCapacity;
    private List<Bus> stoppedBuses;

    private List<Passenger> passengerLine = new ArrayList<>();

    public BusStop(String stopName, int maxBussesCapacity) {
        this.stopName = stopName;
        this.maxBussesCapacity = maxBussesCapacity;
        this.stoppedBuses = new ArrayList<>(maxBussesCapacity);
    }

    public void takeBus(Bus bus) {
        try {
            while (stoppedBuses.size() == maxBussesCapacity) {
                lock.lock();
            }
            stoppedBuses.add(bus);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public void busLeft() {
        while (stoppedBuses.size() < maxBussesCapacity) {
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusStop busStop)) {
            return false;
        }
        return maxBussesCapacity == busStop.maxBussesCapacity
                && Objects.equals(stopName, busStop.stopName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stopName, maxBussesCapacity);
    }

    @Override
    public String toString() {
        return "BusStop:" +
                "stopName='" + stopName +
                "\nmaxBussesCapacity=" + maxBussesCapacity +
                "\nstoppedBuses=" + stoppedBuses;
    }
}
