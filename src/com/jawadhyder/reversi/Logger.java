package com.jawadhyder.reversi;

import java.util.Collections;
import java.util.List;

public class Logger {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    private static String lineStart = "---------- Start ---------";
    private static String lineStartWithKey = "---------- Start -----($)-----";
    private static String lineEnd = "---------- END ----------";
    public static void info(String key, Object o) {
        System.out.println(lineStartWithKey.replace("$", key));
        System.out.println(o);
        System.out.println(lineEnd);
    }

    public static void info(String message) {
//        System.out.println(lineStart);
        System.out.println(message);
//        System.out.println(lineEnd);
    }

    public static void printBoard(Board board) {
        System.out.println(lineStartWithKey.replace("$", "BOARD"));
        Block[][] grid = board.getGrid();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                String s = ANSI_GREEN + "[" + ANSI_RESET;
                Block block = grid[y][x];
                if (block == null) {
                    s += subScript(y) + "." + subScript(x);
                } else if (block.getState().equals(State.BLACK)){
                    s += ANSI_BLACK + subScript(y) + "█" + subScript(x) + ANSI_RESET;
                } else {
                    s += ANSI_WHITE + subScript(y) + "█" + subScript(x) + ANSI_RESET;
                }
                s += ANSI_GREEN + "] " + ANSI_RESET + "| ";
                System.out.print(s);
            }
            System.out.println("");
            System.out.println("----------------------------------------------------------------");
        }
        System.out.println(lineEnd);
    }

    private static String subScript(Integer i) {
        switch (i) {
            case 0:
                return "₀";
            case 1:
                return "₁";
            case 2:
                return "₂";
            case 3:
                return "₃";
            case 4:
                return "₄";
            case 5:
                return "₅";
            case 6:
                return "₆";
            case 7:
                return "₇";
            case 8:
                return "₈";
            default:
                return "";
        }
    }

    public static void error(Exception e) {
        e.printStackTrace();
    }

    public static String indentation(Integer depth) {
        StringBuilder str = new StringBuilder();
        for (int i=0; i<=depth; i++) {
            str.append("        ");
        }
        return str.toString();
    }
}
