package com.jawadhyder.reversi;

import java.util.ArrayList;
import java.util.List;

public class GridNavigator {

    private Board board;

    public static GridNavigator withBoard(Board board) {
        return new GridNavigator(board);
    }

    private GridNavigator(Board board) {
        this.board = board;
    }

    public static State flip(State state) {
        return state == State.BLACK ? State.WHITE : State.BLACK;
    }
    public static MinMax flip(MinMax minMax) {
        return minMax == MinMax.MAX ? MinMax.MIN : MinMax.MAX;
    }

    public Block move(Direction direction, Block withRespectToBlock) throws Exception {
        Integer x = withRespectToBlock.getPosX();
        Integer y = withRespectToBlock.getPosY();
        Block block = null;
        Block[][] grid = board.getGrid();
        switch (direction) {
            case TOP_LEFT:
                if (x > 0 && y > 0) {
                    x = x-1;
                    y = y-1;
                }
                else throw new Exception("Out of grid bound");
                break;
            case TOP:
                if (y > 0) {
                    y = y-1;
                }
                else throw new Exception("Out of grid bound");
                break;
            case TOP_RIGHT:
                if (x < 7 && y > 0) {
                    x = x+1;
                    y = y-1;
                }
                else throw new Exception("Out of grid bound");
                break;
            case LEFT:
                if (x > 0) {
                    x = x-1;
                }
                else throw new Exception("Out of grid bound");
                break;
            case RIGHT:
                if (x < 7) {
                    x = x+1;
                }
                else throw new Exception("Out of grid bound");
                break;
            case BOTTOM_LEFT:
                if (x > 0 && y < 7) {
                    x = x-1;
                    y = y+1;
                }
                else throw new Exception("Out of grid bound");
                break;
            case BOTTOM:
                if (y < 7) {
                    y = y+1;
                }
                else throw new Exception("Out of grid bound");
                break;
            case BOTTOM_RIGHT:
                if (x < 7 && y < 7) {
                    x = x+1;
                    y = y+1;
                }
                else throw new Exception("Out of grid bound");
                break;
        }
        block = grid[y][x];
        return (block != null) ? block : new Block(null, x, y);
    }

    public LegalMove getLegalMove(Direction direction, Block withRespectToBlock) {
        List<Block> blocksToFlip = new ArrayList<>();
        State oppositeState = flip(withRespectToBlock.getState());
        Block nextBlock = withRespectToBlock;

        while (true) {
            try {
                nextBlock = GridNavigator.withBoard(board).move(direction, nextBlock);
            } catch (Exception e) {
                break;
            }
            if (nextBlock.getState() == null || !nextBlock.getState().equals(oppositeState))
                break;
            blocksToFlip.add(nextBlock);
        }
        if (nextBlock.getState() == null && !blocksToFlip.isEmpty()) { // if nextBlock.getState() is null here that means empty slot for sandwich
            nextBlock.setState(withRespectToBlock.getState());
            LegalMove legalMove = new LegalMove(withRespectToBlock, nextBlock, direction);
            legalMove.addBlocksToFlip(blocksToFlip);
            return legalMove;
        }
        else
            return  null;
    }

    public LegalMoveCollection getAllLegalMoves() {
        LegalMoveCollection legalMoves = new LegalMoveCollection();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Block block = board.getGrid()[y][x];
                if (block != null && block.getState() == this.board.getTurn()) { // this is player's block
                    for (Direction direction: Direction.values()) {
                        LegalMove legalMove = GridNavigator.withBoard(board).getLegalMove(direction, block);
                        if (legalMove != null) {
                            legalMoves.add(legalMove);
                        }
                    }
                }
            }
        }
        return legalMoves;
    }

}
