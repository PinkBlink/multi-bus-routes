package org.multi.routes.service.impl;

import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataPassengerBuilder {
    private  Map<Passenger, List<String>> passengersMap;
    private final LogisticManager logisticManager;
    private final NavigateManager navigateManager;
    private final List<Passenger> createdPassengers;
    public Passenger DataPassengerBuilder(Map<Passenger,List<String>>passengersMap){
        this.passengersMap = passengersMap;
    }

    public DataPassengerBuilder() {
        passengersMap = DataEntityParserImpl.getPassengersFromData();
        logisticManager = LogisticManager.getInstance();
        navigateManager = new NavigateManager(logisticManager.getRoutes());
        createdPassengers = new ArrayList<>();
        setUpPassengers();
    }

    public List<Passenger> getPassengersMap() {
        return createdPassengers;
    }

    private void setUpPassengers() {
        for (Map.Entry<Passenger, List<String>> entry : passengersMap.entrySet()) {
            Passenger passenger = entry.getKey();
            List<String> passengerInfo = entry.getValue();
            BusStop currentStop = logisticManager.getStop(passengerInfo.get(0));
            BusStop destination = logisticManager.getStop(passengerInfo.get(1));
            passenger.setDestination((destination));
            passenger.setCurrentStop(currentStop);
            currentStop.addPassengerToLine(passenger);
            List<BusStop> transitStops = navigateManager.getTransitStops(passenger);
            passenger.setTransitStops(transitStops);
            createdPassengers.add(passenger);
        }
    }
}