package org.multi.routes.constans;

public class TextConstants {
    public static final String PASSENGER_BEGIN_STRING = "passenger: ";
    public static final String BUS_NUMBER_STRING = "bus_number: ";
    public static final String MAX_PASSENGERS_CAPACITY_STRING = " max_passengers: ";
    public static final String BUS_STOP_STRING = "bus_stop: ";
    public static final String MAX_BUSES_CAPACITY_STRING = " max_buses: ";
    public static final String PASSENGER_REGEX = "^" + PASSENGER_BEGIN_STRING + "[a-zA-Z]{3,} [a-zA-Z]{3,}$";
    public static final String BUS_REGEX = "^" + BUS_NUMBER_STRING + "[0-9]+" + MAX_PASSENGERS_CAPACITY_STRING + "[0-9]+$";
    public static final String BUS_STOP_REGEX = "^" + BUS_STOP_STRING + "[\\w]+" + MAX_BUSES_CAPACITY_STRING + "[0-9]+$";
}
