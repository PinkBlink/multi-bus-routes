package org.multi.routes.ulils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.multi.routes.ulils.constans.PathConstants;
import org.multi.routes.exception.NoFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataFileReader {
    private static final Logger logger = LogManager.getLogger(DataFileReader.class);
    private static final String ERROR_MESSAGE = "Problem with file or with path: ";

    public static List<String> getPassengersList() throws NoFileException {
        try {
            return readFromFile(PathConstants.PATH_TO_PASSENGERS);
        } catch (IOException e) {
            String errorMessage = ERROR_MESSAGE + PathConstants.PATH_TO_PASSENGERS;
            logger.log(Level.ERROR, errorMessage);
            throw new NoFileException(errorMessage);
        }
    }

    public static List<String> getBusesList() throws NoFileException {
        try {
            return readFromFile(PathConstants.PATH_TO_BUSES);
        } catch (IOException e) {
            String errorMessage = ERROR_MESSAGE + PathConstants.PATH_TO_BUSES;
            logger.log(Level.ERROR, errorMessage);
            throw new NoFileException(errorMessage);
        }
    }

    public static List<String> getBusStopsList() throws NoFileException {
        try {
            return readFromFile(PathConstants.PATH_TO_BUS_STOPS);
        } catch (IOException e) {
            String errorMessage = ERROR_MESSAGE + PathConstants.PATH_TO_BUS_STOPS;
            logger.log(Level.ERROR, errorMessage);
            throw new NoFileException(errorMessage);
        }
    }

    private static List<String> readFromFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}