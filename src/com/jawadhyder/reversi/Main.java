package com.jawadhyder.reversi;

import java.util.Scanner;

public class Main {
    private static final Integer maxLineSize = 8;
    // Reversi 8*8 grid

    public static void main(String[] args) {

        State firstPlayer = State.WHITE;
        State aiPlayer = State.BLACK;

        Board board = new Board(firstPlayer, aiPlayer);
        Logger.printBoard(board);
        Scanner in = new Scanner(System.in);
        while (!board.isGameEnded()) {
            // Check if turn is AI's or Human's
            if (board.isHumanTurn()) {
                boolean turnPlayed = false;
                while (!turnPlayed) {
                    try {
//                    System.out.println("Input x and y position");
                        System.out.println("y: ");
                        Integer posY = in.nextInt();
                        System.out.println("x: ");
                        Integer posX = in.nextInt();
                        board = board.humanChose(posX, posY);
                        turnPlayed = true;
                    } catch (Exception e) {
                        in = new Scanner(System.in);
                        Logger.error(e);
                        System.out.println("Invalid input. Try again.");
                    }
                }
            }
            else {
                board = board.aiPlayNext();
            }
            Logger.printBoard(board);
            in.next();
            Logger.info("Turn played. Press enter.");
        }

        System.out.println("\n Player " + board.getTurn() + " wins!");
        System.out.println("\n------- Game ended -------");

    }
}