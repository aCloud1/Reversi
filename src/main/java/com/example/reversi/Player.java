package com.example.reversi;

import javafx.util.Pair;

import java.util.Vector;

public class Player {
    String name;
    int disk_type;
    Board board;
    boolean skipping_turn;
    int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player(String name, int disk_color, Board board) {
        this.name = name;
        this.disk_type = disk_color;
        this.board = board;
        this.skipping_turn = false;
        this.score = 0;
    }

    public int getDiskType() {
        return disk_type;
    }

    public boolean isSkippingTurn() {
        return skipping_turn;
    }

    public void setSkippingTurn(boolean value) {
        this.skipping_turn = value;
    }

    public boolean handle(int x, int y) {
        Vector<Pair<Integer, Integer>> enemy_cells;
        // game logic. This whole method should probably NOT be in Player class
        if(!board.isAdjacentToOpponent(disk_type, x, y))
            return false;

        enemy_cells = board.getCellsSurroundingOpponent(disk_type, x, y);
        if(enemy_cells.isEmpty()) {
            System.out.println("EMPTY");
            return false;
        }

        // change opponents disks to your own. Board logic
        for(Pair<Integer, Integer> p : enemy_cells)
            board.setCell(p.getKey(), p.getValue(), disk_type);
        board.setCell(x, y, disk_type);  // put disk on the selected cell

        return true;
    }

    @Override
    public String toString() {
        return String.format("%s - %d points!", name, getScore());
    }
}
