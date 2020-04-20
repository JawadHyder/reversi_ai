package com.jawadhyder.reversi;

import java.util.*;

public class Weightage {
//    private static final String BUFFER = "buffer";
//    private static final String EDGES = "edges";
//
//    Map<Integer, Map<String, List<Integer>>> corners = new HashMap<>();
//    {
//        Map<String, List<Integer>> first = new HashMap<>();
//        first.put(BUFFER, Arrays.asList(2, 9, 10));
//        first.put(EDGES, Arrays.asList(3, 4, 5, 6));
//        corners.put(1, first);
//
//        Map<String, List<Integer>> second = new HashMap<>();
//        first.put(BUFFER, Arrays.asList(7, 15, 16));
//        first.put(EDGES, Arrays.asList(17, 25, 33, 41));
//        corners.put(8, second);
//
//        Map<String, List<Integer>> third = new HashMap<>();
//        first.put(BUFFER, Arrays.asList(49, 50, 58));
//        first.put(EDGES, Arrays.asList(59, 60, 61, 62));
//        corners.put(57, third);
//
//        Map<String, List<Integer>> forth = new HashMap<>();
//        first.put(BUFFER, Arrays.asList(55, 56, 63));
//        first.put(EDGES, Arrays.asList(24, 32, 40, 48));
//        corners.put(64, forth);
//    }
//
//    Integer cornerWeight = 10;
//    Integer interiorWeight = 8; // Interiors are Blocks which are not touching an open block
//    Integer exteriorWeight = 3; // Exteriors are Blocks at the touching any open block
//    Integer edgesWeight = 5;
//    Integer bufferFilledWeight = 9; // if corners is ours
//    Integer bufferEmptyWeigh = -10; // if corner is empty or opponent's
//
//    private boolean isCorner(Integer index) {
//        return corners.containsKey(index);
//    }

    private static Integer[][] reward = new Integer[8][8];
    static {
        reward[0][0] = 120;
        reward[0][1] = -20;
        reward[0][2] = 20;
        reward[0][3] = 5;
        reward[0][4] = 5;
        reward[0][5] = 20;
        reward[0][6] = -20;
        reward[0][7] = 120;
        reward[1][0] = -20;
        reward[1][1] = -40;
        reward[1][2] = -5;
        reward[1][3] = -5;
        reward[1][4] = -5;
        reward[1][5] = -5;
        reward[1][6] = -40;
        reward[1][7] = -20;
        reward[2][0] = 20;
        reward[2][1] = -5;
        reward[2][2] = 15;
        reward[2][3] = 3;
        reward[2][4] = 3;
        reward[2][5] = 15;
        reward[2][6] = -5;
        reward[2][7] = 20;
        reward[3][0] = 5;
        reward[3][1] = -5;
        reward[3][2] = 3;
        reward[3][3] = 3;
        reward[3][4] = 3;
        reward[3][5] = 3;
        reward[3][6] = -5;
        reward[3][7] = 5;
        reward[4][0] = 5;
        reward[4][1] = -5;
        reward[4][2] = 3;
        reward[4][3] = 3;
        reward[4][4] = 3;
        reward[4][5] = 3;
        reward[4][6] = -5;
        reward[4][7] = 5;
        reward[5][0] = 20;
        reward[5][1] = -5;
        reward[5][2] = 15;
        reward[5][3] = 3;
        reward[5][4] = 3;
        reward[5][5] = 15;
        reward[5][6] = -5;
        reward[5][7] = 20;
        reward[6][0] = -20;
        reward[6][1] = -40;
        reward[6][2] = -5;
        reward[6][3] = -5;
        reward[6][4] = -5;
        reward[6][5] = -5;
        reward[6][6] = -40;
        reward[6][7] = -20;
        reward[7][0] = 120;
        reward[7][1] = -20;
        reward[7][2] = 20;
        reward[7][3] = 5;
        reward[7][4] = 5;
        reward[7][5] = 20;
        reward[7][6] = -20;
        reward[7][7] = 120;
    }

    public static Integer getScore(LegalMove legalMove) {
        return reward[legalMove.getTo().getPosY()][legalMove.getTo().getPosX()];
    }

//    stable disk
}
