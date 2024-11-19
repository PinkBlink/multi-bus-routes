package org.multi.routes.ulils;

import org.multi.routes.constans.TextConstants;

public class TextUtils {
    public static int getBusNumberFromString(String string) {
        String cleanNumberString = string.replace(TextConstants.PASSENGER_REGEX, "");
        return Integer.parseInt(cleanNumberString);
    }
}
