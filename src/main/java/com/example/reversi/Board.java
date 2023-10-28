package com.example.reversi;

import javafx.util.Pair;

import java.util.Vector;

public class Board {
    final int ROW_COUNT;
    final int COL_COUNT;
    int[][] board = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 1, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 2, 0, 0, 0 },
            { 0, 0, 0, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 0, 2, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 2, 0, 0, 0, 0, 0, 0 },
    };

    public Board(int row_count, int col_count) {
        this.ROW_COUNT = row_count;
        this.COL_COUNT = col_count;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCell(int x, int y) { return board[y][x]; }
    public void setCell(int x, int y, int value) { board[y][x] = value; }

    public Vector<Pair<Integer, Integer>> getCellsSurroundingOpponent(int self, int opponent, int x, int y) {
        Vector<Pair<Integer, Integer>> cells = new Vector<>();
        Vector<Pair<Integer, Integer>> temp = new Vector<>();

        // horizontal
        boolean encountered_self = false;
        boolean encountered_opponent = false;
        for(int i = 0; i < COL_COUNT; i++) {
            if(getCell(i, y) == self && i != x) {
                encountered_self = true;
                if(encountered_opponent) {
                    encountered_opponent = false;
                }
            }
            else if(i == x) {
                if(encountered_opponent) {
                    cells.addAll(temp);
                    temp.clear();
                }
            }
            else if(getCell(i, y) == opponent) {
                encountered_opponent = true;
                if(encountered_self)
                    temp.add(new Pair<>(i, y));
            }
            else {
                encountered_self = false;
                encountered_opponent = false;
                temp.clear();
            }
        }

        // vertical
        temp.clear();
        for(int i = 0; i < ROW_COUNT; i++) {
            if(getCell(x, i) == self && i != y) {
                encountered_self = true;
                if(encountered_opponent) {
                    encountered_opponent = false;
                    cells.addAll(temp);
                    temp.clear();
                }
            } else if (i == y) {
                if(encountered_opponent) {
                    cells.addAll(temp);
                    temp.clear();
                }
            } else if (getCell(x, i) == opponent) {
                encountered_opponent = true;
                if (encountered_self)
                    temp.add(new Pair<>(x, i));
            } else {
                encountered_self = false;
                encountered_opponent = false;
                temp.clear();
            }
        }

        return cells;
    }

    public boolean isAdjacentToOpponent(int opponent, int x, int y) {
        boolean is_adjacent = false;
        // up left
        if(y - 1 >= 0 && x - 1 >= 0 && getCell(x - 1, y - 1) == opponent)
            is_adjacent = true;

        // up
        if(y - 1 >= 0 && getCell(x, y - 1) == opponent)
            is_adjacent = true;

        // up right
        if(y - 1 >= 0 && x + 1 < COL_COUNT && getCell(x + 1, y - 1) == opponent)
            is_adjacent = true;

        // right
        if(x + 1 < COL_COUNT && getCell(x + 1, y) == opponent)
            is_adjacent = true;

        // right down
        if(y + 1 < ROW_COUNT && x + 1 < COL_COUNT && getCell(x + 1, y + 1) == opponent)
            is_adjacent = true;

        // down
        if(y + 1 < ROW_COUNT && getCell(x, y + 1) == opponent)
            is_adjacent = true;

        // down left
        if(y + 1 < ROW_COUNT && x - 1 >= 0 && getCell(x - 1, y + 1) == opponent)
            is_adjacent = true;

        // left
        if(x - 1 >= 0 && getCell(x - 1, y) == opponent)
            is_adjacent = true;

        return is_adjacent;
    }
}
