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
        expectedPassengersList = Arrays.asList("passenger: James Anderson",
                "passenger: Emily Parker",
                "passenger: Michael Bennett",
                "passenger: Sophia Harris",
                "passenger: William Carter",
                "passenger: Olivia Morgan",
                "passenger: Alexander Scott",
                "passenger: Charlotte Thompson",
                "passenger: Benjamin Wright",
                "passenger: Amelia Collins");

        expectedBusesList = Arrays.asList("bus_number: 1 max_passengers: 2",
                "bus_number: 2 max_passengers: 4",
                "bus_number: 3 max_passengers: 1",
                "bus_number: 4 max_passengers: 1",
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
