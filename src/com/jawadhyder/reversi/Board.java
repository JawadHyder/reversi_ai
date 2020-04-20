package com.jawadhyder.reversi;

import java.util.*;

public class Board {
    private Block[][] grid = new Block[8][8];
    private State turn;
    private State aiPlayer; // State or color of AI player
    private State humanPlayer; // State or color of Human player
    private boolean gameEnded = false;
    private Integer filledBlocks = 4;
    private Integer aiScore = 2;
    private Integer humanScore = 2;
    private static final Integer TOTAL_BLOCKS = 64;
    private static final Integer MINIMAX_LOOK_AHEAD = 4;

    public Board(State turn, State aiPlayer) {
        // Initialize
        this.turn = turn;
        this.aiPlayer = aiPlayer;
        this.humanPlayer = aiPlayer == State.BLACK ? State.WHITE : State.BLACK;

        grid[3][3] = new Block(State.BLACK, 3, 3);
        grid[4][3] = new Block(State.WHITE, 3, 4);
        grid[3][4] = new Block(State.WHITE, 4, 3);
        grid[4][4] = new Block(State.BLACK, 4, 4);
    }

    public Board(Board board) {
        this.grid = new Block[8][8];

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Block block = board.getGrid()[y][x];
                this.grid[y][x] = (block == null) ? null : new Block(block.getState(), block.getPosX(), block.getPosY());
            }
        }

        this.turn = board.turn;
        this.aiPlayer = board.aiPlayer;
        this.humanPlayer = board.humanPlayer;
        this.gameEnded = board.isGameEnded();
        this.filledBlocks = board.filledBlocks;
        this.aiScore = board.aiScore;
        this.humanScore = board.humanScore;
    }

    public Block[][] getGrid() {
        return grid;
    }

    public Board aiPlayNext() {

        if (this.isGameEnded()) {
            throw new RuntimeException("Game already ended");
        }

        try {
            LegalMove legalMove = getBestLegalMove(this, SearchStrategy.MINIMAX);
            if (legalMove == null) {
                Logger.info("No possible moves. Skipping turn.");
                this.turn = GridNavigator.flip(this.turn);
                return this;
            }
            Board newBoard = this.makeMove(legalMove.getTo());
            Logger.info("Made move to: " + legalMove.getTo().getPosY() + "." + legalMove.getTo().getPosX());
            return newBoard;
        } catch (Exception e) {
            Logger.error(e);
            return this;
        }
    }

    private LegalMove getBestLegalMove(Board board, SearchStrategy searchStrategy) throws Exception {
        switch (searchStrategy) {
            case RANDOM:
                return randomSearchMove(board);
            case LOCAL_MAXIMIZATION:
                return localMaximizationSearch(board);
            case MINIMAX: {
                return minimaxSearch(board, null, 0, MinMax.MIN);
            }
            default:
                throw new Exception("Not valid search stratagy");
        }
    }

    private LegalMove minimaxSearch(Board board, LegalMove legalMove, Integer depth, MinMax minMax) throws Exception {
        if (depth.equals(MINIMAX_LOOK_AHEAD) || board.isGameEnded()) {
            legalMove.setComputedWeight(Weightage.getScore(legalMove));
            Logger.info(Logger.indentation(depth) + "Max depth reached in depth:" + depth + " with static evaluation:" + legalMove);
            return legalMove;
        }
        Logger.info(Logger.indentation(depth) + "In depth: " + depth + " of " + minMax);
        LegalMoveCollection legalMoves = GridNavigator.withBoard(board).getAllLegalMoves();
        Logger.info(Logger.indentation(depth) + "Found " + legalMoves.size() + " possible moves");
        System.out.print("+" + legalMoves.size());
        if (legalMoves.isEmpty()) {
            if (legalMove != null)
                legalMove.setComputedWeight(Weightage.getScore(legalMove));
            Logger.info(Logger.indentation(depth) + "No more possible moves in depth:" + depth + " with static evaluation:" + legalMove);
            return legalMove;
        }
        int i = 0;
        for (LegalMove childMove: legalMoves) {
            Logger.info(Logger.indentation(depth) + "Processing child move #" + i + " " + childMove + " in depth " + depth);
            Board newBoard = board.makeMove(childMove.getTo());
            LegalMove resultOfThisChildMove = minimaxSearch(newBoard, childMove, depth+1, GridNavigator.flip(minMax));
            childMove.setComputedWeight(Weightage.getScore(resultOfThisChildMove));
            Logger.info(Logger.indentation(depth) + "In depth " + depth + " child #" + childMove);
            i++;
        }

        LegalMove bestResult = minMax.equals(MinMax.MAX) ? legalMoves.havingMaxComputedWeight() : legalMoves.havingMinComputedWeight();;

        Logger.info(Logger.indentation(depth) + "Best: (depth: " + depth + ") " + bestResult);
        return bestResult;
    }

    private LegalMove localMaximizationSearch(Board board) {
        LegalMoveCollection legalMoves = GridNavigator.withBoard(board).getAllLegalMoves();
        if (legalMoves.isEmpty())
            return null;
        LegalMove selectedMove = null;
        for (LegalMove legalMove: legalMoves) {
            if (selectedMove == null)
                selectedMove = legalMove;
            selectedMove = Weightage.getScore(legalMove) > Weightage.getScore(selectedMove) ? legalMove : selectedMove;
        }
        return selectedMove;
    }

    private LegalMove randomSearchMove(Board board) {
        LegalMoveCollection legalMoves = GridNavigator.withBoard(board).getAllLegalMoves();
        if (legalMoves.isEmpty())
            return null;
        return legalMoves.get(new Random().nextInt(legalMoves.size()));
    }

    // posX and posY are starting from
    public Board humanChose(Integer posX, Integer posY) throws Exception {
        if (isGameEnded())
            throw new RuntimeException("Game already ended");
        if (!isHumanTurn())
            throw new RuntimeException("Not Human's turn");

        LegalMove legalMove = legalMoveFromHumanInput(posX, posY);
        if (legalMove == null) {
            Logger.info("No possible moves. Skipping turn.");
            this.turn = GridNavigator.flip(this.turn);
            return this;
        }
        return this.makeMove(legalMove.getTo());
    }

    private Board makeMove(Block block) throws Exception {
        LegalMoveCollection legalMoves = GridNavigator.withBoard(this).getAllLegalMoves();
        legalMoves = legalMoves.endingAt(block);
        Board board = new Board(this);
        Block[][] grid = board.getGrid();
        // Make flips
        for (LegalMove legalMove: legalMoves) {
            for(Block blockToFLip: legalMove.getBlocksToFlip()) {
                grid[blockToFLip.getPosY()][blockToFLip.getPosX()].flipState();
            }
            if (isHumanTurn()) {
                board.humanScore += legalMove.getBlocksToFlip().size();
                board.aiScore -= legalMove.getBlocksToFlip().size();
            } else {
                board.humanScore -= legalMove.getBlocksToFlip().size();
                board.aiScore += legalMove.getBlocksToFlip().size();
            }
        }
        grid[block.getPosY()][block.getPosX()] = block;
        board.grid = grid;
        if (isHumanTurn()) {
            board.humanScore ++;;
        } else {
            board.aiScore ++;
        }

        board.filledBlocks++;
        if (board.filledBlocks < Board.TOTAL_BLOCKS) {
            board.turn = GridNavigator.flip(this.turn);
        } else {
            board.gameEnded = true;
        }
        return board;
    }

    private LegalMove legalMoveFromHumanInput(Integer posX, Integer posY) throws Exception {
        if (posX > 7 || posX < 0 || posY > 7 || posY < 0)
            throw new RuntimeException("Not a valid input");

        Block newBlock = grid[posY][posX];
        if (newBlock != null) {
            throw new RuntimeException("Not a valid input");
        }
        LegalMoveCollection legalMoves = GridNavigator.withBoard(this).getAllLegalMoves();
        if (legalMoves.isEmpty())
            return null;
        if (legalMoves.containsPosition(posX, posY))
            return legalMoves.atPosition(posX, posY);
        else
            throw new Exception("Not a valid move");
    }

    public boolean isHumanTurn() {
        return this.humanPlayer == this.turn;
    }

    public boolean isGameEnded() {
        return this.gameEnded;
    }

    public State getTurn() {
        return turn;
    }

}