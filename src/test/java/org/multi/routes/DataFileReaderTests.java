package org.multi.routes;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.multi.routes.exception.NoFileException;
import org.multi.routes.ulils.DataFileReader;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


public class DataFileReaderTests {
    private List<String> expectedPassengersList;
    private List<String> expectedBusesList;

    private List<String> expectedBusStopsList;

    private final Logger logger = (Logger) LogManager.getLogger(DataFileReaderTests.class);

    @BeforeTest
    public void setUp() {
        expectedPassengersList = Arrays.asList("passenger: James Anderson start: 1 destination: 9",
                "passenger: Emily Parker start: 2 destination: 3",
                "passenger: Michael Bennett start: 2 destination: 7",
                "passenger: Sophia Harris start: 1 destination: 3",
                "passenger: William Carter start: 2 destination: 8",
                "passenger: Olivia Morgan start: 4 destination: 9",
                "passenger: Alexander Scott start: 1 destination: 3",
                "passenger: Charlotte Thompson start: 4 destination: 10",
                "passenger: Benjamin Wright start: 3 destination: 6",
                "passenger: Amelia Collins start: 4 destination: 10");

        expectedBusesList = Arrays.asList("bus_number: 1 max_passengers: 2",
                "bus_number: 2 max_passengers: 4",
                "bus_number: 3 max_passengers: 2",
                "bus_number: 4 max_passengers: 2",
                "bus_number: 5 max_passengers: 2");

        expectedBusStopsList = Arrays.asList("bus_stop: 1 max_buses: 2",
                "bus_stop: 2 max_buses: 3",
                "bus_stop: 3 max_buses: 1",
                "bus_stop: 4 max_buses: 1",
                "bus_stop: 5 max_buses: 3",
                "bus_stop: 6 max_buses: 4",
                "bus_stop: 7 max_buses: 4",
                "bus_stop: 8 max_buses: 4",
                "bus_stop: 9 max_buses: 2",
                "bus_stop: 10 max_buses: 2");
    }

    @Test
    public void getPassengersListTest() {
        try {
            List<String> actual = DataFileReader.getPassengersList();
            Assert.assertEquals(actual, expectedPassengersList);
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    @Test
    public void getBusesListTest() {
        try {
            List<String> actual = DataFileReader.getBusesList();
            Assert.assertEquals(actual, expectedBusesList);
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }

    @Test
    public void getBusStopsTest() {
        try {
            List<String> actual = DataFileReader.getBusStopsList();
            Assert.assertEquals(actual, expectedBusStopsList);
        } catch (NoFileException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
