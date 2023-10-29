package com.example.reversi;

import javafx.util.Pair;

import java.util.Vector;

public class Player {
    int color;
    Board board;
    boolean valid_moves;

    public Player(int disk_color, Board board) {
        this.color = disk_color;
        this.board = board;
        this.valid_moves = true;
    }

    public boolean handle(int x, int y) {
        Vector<Pair<Integer, Integer>> enemy_cells;
        // game logic. This whole method should probably NOT be in Player class
        if(!board.isAdjacentToOpponent(color, x, y))
            return false;

        enemy_cells = board.getCellsSurroundingOpponent(color, x, y);
        if(enemy_cells.isEmpty()) {
            System.out.println("EMPTY");
            return false;
        }

        // change opponents disks to your own. Board logic
        for(Pair<Integer, Integer> p : enemy_cells)
            board.setCell(p.getKey(), p.getValue(), color);
        board.setCell(x, y, color);  // put disk on the selected cell

        return true;
    }
}
