package com.jawadhyder.reversi;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private LegalMove legalMove;
    private Integer score;
    private Integer selectedChildScore;
    private List<Node> children = new ArrayList<>();

    public LegalMove getLegalMove() {
        return legalMove;
    }

    public void setLegalMove(LegalMove legalMove) {
        this.legalMove = legalMove;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Integer getSelectedChildScore() {
        return selectedChildScore;
    }

    public void setSelectedChildScore(Integer selectedChildScore) {
        this.selectedChildScore = selectedChildScore;
    }

    public void addChild(Node node) {
        this.children.add(node);
    }

    public StringBuilder print(StringBuilder sb) {
        sb.append(" { ");
        sb.append("text: { ");
        sb.append(" name: \"Mark Hill\", ");
        sb.append(" title: \"Mark Hill\", ");
        sb.append(" contact: \"Mark Hill\", ");
        sb.append(" }, ");
        if (this.children != null && !this.children.isEmpty()) {
            sb.append(" children: [ ");
            for (Node node: children) {
                node.print(sb);
            }
            sb.append(" ] ");
        }
        sb.append(" }, ");

        return sb;
    }
}
