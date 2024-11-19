package org.multi.routes.constans;

import java.nio.file.Path;

public class PathConstants {
    public static final String STRING_PATH_TO_DATA_FOLDER = "data/";
    public static final Path PATH_TO_PASSENGERS = Path.of(STRING_PATH_TO_DATA_FOLDER + "passengers.txt");
    public static final Path PATH_TO_BUSES = Path.of(STRING_PATH_TO_DATA_FOLDER + "buses.txt");
    public static final Path PATH_TO_BUS_STOPS = Path.of(STRING_PATH_TO_DATA_FOLDER + "bus_stops.txt");
}