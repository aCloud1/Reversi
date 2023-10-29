package com.example.reversi;

import javafx.util.Pair;

import java.util.Vector;

public class Player {
    int color;
    boolean turn = false;
    //    Board board;
//
    public Player(int disk_color) {
        this.color = disk_color;
    }

    public Player(int disk_color, Board board) {
        this.color = disk_color;
//        this.board = board;
    }

//    public void handle(int x, int y) {
//        Vector<Pair<Integer, Integer>> enemy_cells;
//
//        if(!board.isAdjacentToOpponent(Game.Cell.PLAYER2.getValue(), x, y))
//            return;
//
//        enemy_cells = getCellsSurroundingOpponent(Game.Cell.PLAYER1.getValue(), Game.Cell.PLAYER2.getValue(), x, y);
//        if(enemy_cells.isEmpty()) {
//            System.out.println("EMPTY");
//            return;
//        }
//
//        // change opponents disks to your own
//        for(Pair<Integer, Integer> p : enemy_cells)
//            board.setCell(p.getKey(), p.getValue(), Game.Cell.PLAYER1.getValue());
//
//        board.setCell(x, y, Game.Cell.PLAYER1.getValue());
//    }
}
