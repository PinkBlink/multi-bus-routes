package org.multi.routes.ulils;

public class IdGenerator {
    private static int idCounter = 1;

    public static int getNewId() {
        return idCounter++;
    }
}
