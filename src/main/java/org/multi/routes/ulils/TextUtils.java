package org.multi.routes.ulils;

import org.multi.routes.constans.TextConstants;

import java.util.Arrays;

public class TextUtils {
    public static int getBusNumberFromString(String string) {
        String cleanNumberString = string.replace(TextConstants.PASSENGER_REGEX, "");
        return Integer.parseInt(cleanNumberString);
    }

    public static void main(String[] args) {
        String test = "bus_number: 5 max_passengers: 2";
        test = test.replace(TextConstants.BUS_NUMBER_STRING, "").replace(TextConstants.MAX_PASSENGERS_CAPACITY_STRING, " ");
        System.out.println(Arrays.toString(test.split(" ")));
    }

    public static int getNumberFromString(String string){
        return 0;
    }
}
