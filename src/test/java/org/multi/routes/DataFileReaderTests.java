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
        expectedPassengersList = Arrays.asList("passenger: 1", "passenger: 2", "passenger: 3");
        expectedBusesList = Arrays.asList("number: 1", "number: 2", "number: 3");
        expectedBusStopsList = Arrays.asList("stop: 1", "stop: 2", "stop: 3");
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
