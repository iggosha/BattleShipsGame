package org.igor;

public class CoordinateConstants {
    // Консольные цвета

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";


    // Обозначения клеток
    public static final int BATTLEFIELD_SIZE = 10;
    public static final int EMPTY_CELL = 0;
    public static final int SHIP_CELL = 11;
    public static final int DESTROYED_CELL = -1;
    public static final int SHOT_CELL = 8;
}
