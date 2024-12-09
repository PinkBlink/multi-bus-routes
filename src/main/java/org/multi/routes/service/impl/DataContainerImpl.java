package org.multi.routes.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.exception.NoFileException;
import org.multi.routes.model.Bus;
import org.multi.routes.model.BusStop;
import org.multi.routes.model.Passenger;
import org.multi.routes.service.DataContainer;
import org.multi.routes.service.DataEntityParser;
import org.multi.routes.ulils.DataFileReader;

import java.util.List;

public class DataContainerImpl implements DataContainer {
    private Logger logger = LogManager.getLogger(this);
    private List<Passenger> passengers;
    private List<BusStop> busStops;
    private List<Bus> buses;
    private DataEntityParser dataEntityParser = new DataEntityParserImpl();

    private void fillBuses() {
        try {
            List<String> busesString = DataFileReader.getBusesList();
            buses = dataEntityParser.g
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    @Override
    public List<Bus> getBuses() {
        return null;
    }

    @Override
    public List<BusStop> getBusStops() {
        return null;
    }

    @Override
    public List<Passenger> getPassengers() {
        return null;
    }
}