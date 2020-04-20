package com.jawadhyder.reversi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class LegalMoveCollection extends ArrayList<LegalMove> {
    public boolean containsPosition(Integer posX, Integer posY) {
        return this.stream().anyMatch((item) -> {
            return item.getTo().getPosX().equals(posX) && item.getTo().getPosY().equals(posY);
        });
    }
    public LegalMove atPosition(Integer posX, Integer posY) {
        return this.stream().filter((item) -> {
            return item.getTo().getPosX().equals(posX) && item.getTo().getPosY().equals(posY);
        }).collect(Collectors.toList()).get(0);
    }

    public LegalMoveCollection endingAt(Block block) {
        LegalMoveCollection legalMoves = new LegalMoveCollection();
        for (LegalMove legalMove: this) {
            if (legalMove.getTo().equals(block))
                legalMoves.add(legalMove);
        }
        return legalMoves;
    }

    public LegalMove havingMaxComputedWeight() {
        if (isEmpty())
            return null;
        LegalMove max = null;
        for (LegalMove legalMove: this) {
            if (max == null)
                max = legalMove;
            else {
                max = legalMove.getComputedWeight() > max.getComputedWeight() ? legalMove : max;
            }
        }
        return max;
    }

    public LegalMove havingMinComputedWeight() {
        if (isEmpty())
            return null;
        LegalMove min = null;
        for (LegalMove legalMove: this) {
            if (min == null)
                min = legalMove;
            else {
                min = legalMove.getComputedWeight() < min.getComputedWeight() ? legalMove : min;
            }
        }
        return min;
    }
}