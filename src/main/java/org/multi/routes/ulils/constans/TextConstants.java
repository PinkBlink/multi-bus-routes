package org.multi.routes.ulils.constans;

public class TextConstants {
    public static final String EMPTY_STRING = "";
    public static final String SEPARATOR = "/";
    public static final String SPACE = " ";
    public static final String PASSENGER_NAME_STRING = "passenger: ";
    public static final String PASSENGER_CURRENT_STOP_STRING = " start: ";
    public static final String PASSENGER_DESTINATION_STRING = " destination: ";
    public static final String BUS_NUMBER_STRING = "bus_number: ";
    public static final String MAX_PASSENGERS_CAPACITY_STRING = " max_passengers: ";
    public static final String BUS_STOP_NAME_STRING = "bus_stop: ";
    public static final String MAX_BUSES_CAPACITY_STRING = " max_buses: ";
    public static final String BUS_ROUTE_NUMBER_STRING = "bus_route: ";
    public static final String BUS_ROUTE_STOPS = " stops: ";
    public static final String PASSENGER_REGEX =
            "^" + PASSENGER_NAME_STRING + "[a-zA-Z]{3,} [a-zA-Z]{3,}"
                    + PASSENGER_CURRENT_STOP_STRING + "[\\w]+"
                    + PASSENGER_DESTINATION_STRING + "[\\w]+$";
    public static final String BUS_REGEX =
            "^" + BUS_NUMBER_STRING + "[0-9]+"
                    + MAX_PASSENGERS_CAPACITY_STRING + "[0-9]+"
                    + SPACE + BUS_ROUTE_NUMBER_STRING + "[0-9]+$";
    public static final String BUS_STOP_REGEX =
            "^" + BUS_STOP_NAME_STRING + "[\\w]+"
                    + MAX_BUSES_CAPACITY_STRING + "[0-9]+$";
    public static final String BUS_ROUTE_REGEX =
            "^" + BUS_ROUTE_NUMBER_STRING
                    + "[0-9]+" + BUS_ROUTE_STOPS + "[\\w, ]{1,} \\w+$";
}