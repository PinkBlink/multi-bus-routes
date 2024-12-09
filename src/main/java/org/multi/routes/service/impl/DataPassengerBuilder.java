package org.multi.routes.service.impl;

import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.Navigator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataPassengerBuilder {
    private  Map<Passenger, List<String>> passengersMap;
    private Navigator navigateManager;
    private final List<Passenger> createdPassengers;
    public Passenger DataPassengerBuilder(Map<Passenger,List<String>>passengersMap){
        this.passengersMap = passengersMap;
    }

    public DataPassengerBuilder() {
        passengersMap = DataEntityParserImpl.getPassengersFromData();
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
            passenger.setDestination((destination));
            passenger.setCurrentStop(currentStop);
            currentStop.addPassengerToLine(passenger);
            List<BusStop> transitStops = navigateManager.getTransitStops(passenger);
            passenger.setTransitStops(transitStops);
            createdPassengers.add(passenger);
        }
    }
}