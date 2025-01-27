package io.github.tbzrunner.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    // ANSI color codes for console output
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    public static void log(String tag, String message) {
        String timestamp = formatter.format(new Date());
        System.out.println(BLUE + "[" + timestamp + "]" + RESET + " " + GREEN + tag + RESET + ": " + message);
    }

    public static void warn(String tag, String message) {
        String timestamp = formatter.format(new Date());
        System.out.println(YELLOW + "[" + timestamp + "]" + RESET + " " + YELLOW + tag + RESET + ": " + message);
    }

    public static void error(String tag, String message) {
        String timestamp = formatter.format(new Date());
        System.err.println(RED + "[" + timestamp + "]" + RESET + " " + RED + tag + RESET + ": " + message);
    }

    public static void debug(String tag, String message) {
        String timestamp = formatter.format(new Date());
        System.out.println(BLUE + "[" + timestamp + "]" + RESET + " " + BLUE + tag + RESET + ": " + message);
    }
}
