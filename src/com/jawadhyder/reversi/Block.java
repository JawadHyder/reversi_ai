package com.jawadhyder.reversi;

import java.util.Objects;

public class Block {

    private State state;
    private Integer posY;
    private Integer posX;

    public Block(State state, Integer posX, Integer posY) {
        this.state = state;
        this.posY = posY;
        this.posX = posX;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void flipState() throws Exception {
        if (this.state == null)
            throw new Exception("State cannot be flipped");
        this.state = this.state == State.BLACK ? State.WHITE : State.BLACK;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return posY.equals(block.posY) &&
                posX.equals(block.posX);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posY, posX);
    }

    @Override
    public String toString() {
        return "Block["+posY+","+posX+"]";
//                "state=" + state +
//                ", posY=" + posY +
//                ", posX=" + posX +
//                '}';
    }

    public Block clone() {
        return new Block(this.getState(), this.getPosX(), this.getPosY());
    }
}