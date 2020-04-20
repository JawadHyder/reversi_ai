package com.jawadhyder.reversi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LegalMove {
    private Block from; // Starting point of move i.e our existing bock
    private Block to; // New empty block to acquire
    private List<Block> blocksToFlip = new ArrayList<>();
    private Direction direction;
    private Integer computedWeight;

    public LegalMove(Block from, Block to, Direction direction) {
        this.from = from;
        this.to = to;
        this.direction = direction;
    }

    public Block getFrom() {
        return from;
    }

    public void setFrom(Block from) {
        this.from = from;
    }

    public Block getTo() {
        return to;
    }

    public void setTo(Block to) {
        this.to = to;
    }

    public List<Block> getBlocksToFlip() {
        return blocksToFlip;
    }

    public void addBlockToFlip(Block block) {
        this.blocksToFlip.add(block);
    }

    public void addBlocksToFlip(List<Block> blocks) {
        this.blocksToFlip.addAll(blocks);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Integer getComputedWeight() {
        return computedWeight;
    }

    public void setComputedWeight(Integer computedWeight) {
        this.computedWeight = computedWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegalMove legalMove = (LegalMove) o;
        return Objects.equals(from, legalMove.from) &&
                Objects.equals(to, legalMove.to) &&
                Objects.equals(blocksToFlip, legalMove.blocksToFlip) &&
                direction == legalMove.direction &&
                Objects.equals(computedWeight, legalMove.computedWeight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, blocksToFlip, direction, computedWeight);
    }

    @Override
    public String toString() {
        return "LM["+to+" W:"+computedWeight+"]";
//                "from=" + from +
//                ", to=" + to +
//                ", blocksToFlip=" + blocksToFlip +
//                ", direction=" + direction +
//                '}';
    }

    public LegalMove clone() {
        return new LegalMove(this.getFrom().clone(), this.getTo().clone(), this.getDirection());
    }
}
