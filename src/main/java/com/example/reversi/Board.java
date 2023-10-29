package com.example.reversi;

import javafx.util.Pair;

import java.util.Vector;

public class Board {
    final int ROW_COUNT;
    final int COL_COUNT;
    int[][] board;

    public Board(int row_count, int col_count) {
        this.ROW_COUNT = row_count;
        this.COL_COUNT = col_count;
        this.board = new int[][]{
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 1, 2, 0, 0, 0 },
            { 0, 0, 0, 2, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
        };
    }

    public Board(int row_count, int col_count, int[][] board) {
        this.ROW_COUNT = row_count;
        this.COL_COUNT = col_count;
        this.board = board;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCell(int x, int y) { return board[y][x]; }
    public void setCell(int x, int y, int value) { board[y][x] = value; }

    public Vector<Pair<Integer, Integer>> getCellsSurroundingOpponent(int self, int x, int y) {
        Vector<Pair<Integer, Integer>> cells = new Vector<>();

        cells.addAll(checkHorizontal(self, x, y));
        cells.addAll(checkVertical(self, x, y));

        // diagonal
        cells.addAll(checkDiagonal(self, x, y));

        return cells;
    }

    public Vector<Pair<Integer, Integer>> checkHorizontal(int self, int clicked_x, int clicked_y) {
        Vector<Pair<Integer, Integer>> cells = new Vector<>();
        Vector<Pair<Integer, Integer>> temp = new Vector<>();
        int encountered_self = 0;
        boolean encountered_opponent = false;


        for(int i = clicked_x; i >= 0; i--) {
            if(encountered_self > 1)
                break;
            else if(getCell(i, clicked_y) == self || i == clicked_x) {
                encountered_self++;
                if(encountered_opponent) {
                    cells.addAll(temp);
                    temp.clear();
                }
            }
            else if(getCell(i, clicked_y) != Cell.EMPTY.getValue()) {
                encountered_opponent = true;
                temp.add(new Pair<>(i, clicked_y));
            }
            else break;
        }

        temp.clear();
        encountered_self = 0;
        encountered_opponent = false;

        for(int i = clicked_x; i < ROW_COUNT; i++) {
            if(encountered_self > 1)
                break;
            else if(getCell(i, clicked_y) == self || i == clicked_x) {
                encountered_self++;
                if(encountered_opponent) {
                    cells.addAll(temp);
                    temp.clear();
                }
            }
            else if(getCell(i, clicked_y) != Cell.EMPTY.getValue()) {
                encountered_opponent = true;
                temp.add(new Pair<>(i, clicked_y));
            }
            else break;
        }

        return cells;
    }

    public Vector<Pair<Integer, Integer>> checkVertical(int self, int clicked_x, int clicked_y) {
        Vector<Pair<Integer, Integer>> cells = new Vector<>();
        Vector<Pair<Integer, Integer>> temp = new Vector<>();
        int encountered_self = 0;
        boolean encountered_opponent = false;


        for(int i = clicked_y; i >= 0; i--) {
            if(encountered_self > 1)
                break;
            else if(getCell(clicked_x, i) == self || i == clicked_y) {
                encountered_self++;
                if(encountered_opponent) {
                    cells.addAll(temp);
                    temp.clear();
                }
            }
            else if(getCell(clicked_x, i) != Cell.EMPTY.getValue()) {
                encountered_opponent = true;
                temp.add(new Pair<>(clicked_x, i));
            }
            else break;
        }

        temp.clear();
        encountered_self = 0;
        encountered_opponent = false;

        for(int i = clicked_y; i < ROW_COUNT; i++) {
            if(encountered_self > 1)
                break;
            else if(getCell(clicked_x, i) == self || i == clicked_y) {
                encountered_self++;
                if(encountered_opponent) {
                    cells.addAll(temp);
                    temp.clear();
                }
            }
            else if(getCell(clicked_x, i) != Cell.EMPTY.getValue()) {
                encountered_opponent = true;
                temp.add(new Pair<>(clicked_x, i));
            }
            else break;
        }

        return cells;
    }

    public Vector<Pair<Integer, Integer>> checkDiagonal(int self, int clicked_x, int clicked_y) {
        Vector<Pair<Integer, Integer>> cells = new Vector<>();
        Vector<Pair<Integer, Integer>> temp = new Vector<>();
        int encountered_self = 0;
        boolean encountered_opponent = false;
        int i, j;


        //region left up
        i = clicked_x;
        j = clicked_y;
        while(i >= 0 && j >= 0) {
            if(encountered_self > 1)
                break;
            else if(getCell(i, j) == self || (i == clicked_x && j == clicked_y)) {  // the clicked check here is probably not needed, because we will never come back to this cell. DONT DELETE WITHOUT CHECKING FIRST
                encountered_self++;
                if(encountered_opponent) {
                    cells.addAll(temp);
                    temp.clear();
                }
                i--;
                j--;
            }
            else if(getCell(i, j) != Cell.EMPTY.getValue()) {
                encountered_opponent = true;
                temp.add(new Pair<>(i, j));
                i--;
                j--;
            }
            else break;
        }
        //endregion

        //region right down
        temp.clear();
        encountered_self = 0;
        encountered_opponent = false;
        i = clicked_x;
        j = clicked_y;
        while(i < COL_COUNT && j < ROW_COUNT) {
            if(encountered_self > 1)
                break;
            else if(getCell(i, j) == self || (i == clicked_x && j == clicked_y)) {
                encountered_self++;
                if(encountered_opponent) {
                    cells.addAll(temp);
                    temp.clear();
                }
                i++;
                j++;
            }
            else if(getCell(i, j) != Cell.EMPTY.getValue()) {
                encountered_opponent = true;
                temp.add(new Pair<>(i, j));
                i++;
                j++;
            }
            else break;
        }
        //endregion

        //region left down
        temp.clear();
        encountered_self = 0;
        encountered_opponent = false;
        i = clicked_x;
        j = clicked_y;
        while(i >= 0 && j < ROW_COUNT) {
            if(encountered_self > 1)
                break;
            else if(getCell(i, j) == self || (i == clicked_x && j == clicked_y)) {
                encountered_self++;
                if(encountered_opponent) {
                    cells.addAll(temp);
                    temp.clear();
                }
                i--;
                j++;
            }
            else if(getCell(i, j) != Cell.EMPTY.getValue()) {
                encountered_opponent = true;
                temp.add(new Pair<>(i, j));
                i--;
                j++;
            }
            else break;
        }
        //endregion

        //region right up
        temp.clear();
        encountered_self = 0;
        encountered_opponent = false;
        i = clicked_x;
        j = clicked_y;
        while(i < COL_COUNT && j >= 0) {
            if(encountered_self > 1)
                break;
            else if(getCell(i, j) == self || (i == clicked_x && j == clicked_y)) {
                encountered_self++;
                if(encountered_opponent) {
                    cells.addAll(temp);
                    temp.clear();
                }
                i++;
                j--;
            }
            else if(getCell(i, j) != Cell.EMPTY.getValue()) {
                encountered_opponent = true;
                temp.add(new Pair<>(i, j));
                i++;
                j--;
            }
            else break;
        }
        //endregion

        return cells;
    }

    public Board getValidMoves(int player) {
        Board valid_moves = new Board(ROW_COUNT, COL_COUNT);

        for(int x = 0; x < ROW_COUNT; x++)
            for(int y = 0; y < COL_COUNT; y++)
                if(isAdjacentToOpponent(player, x, y))
                    if(!(getCellsSurroundingOpponent(player, x, y).isEmpty()))
                        valid_moves.setCell(x, y, 9);

        return valid_moves;
    }

    public boolean isAdjacentToOpponent(int self, int x, int y) {
        boolean is_adjacent = false;
        // up left
        if(y - 1 >= 0 && x - 1 >= 0 && getCell(x - 1, y - 1) != self && getCell(x - 1, y - 1) != Cell.EMPTY.getValue())
            is_adjacent = true;

        // up
        if(y - 1 >= 0 && getCell(x, y - 1) != self && getCell(x, y - 1) != Cell.EMPTY.getValue())
            is_adjacent = true;

        // up right
        if(y - 1 >= 0 && x + 1 < COL_COUNT && getCell(x + 1, y - 1) != self && getCell(x + 1, y - 1) != Cell.EMPTY.getValue())
            is_adjacent = true;

        // right
        if(x + 1 < COL_COUNT && getCell(x + 1, y) != self && getCell(x + 1, y) != Cell.EMPTY.getValue())
            is_adjacent = true;

        // right down
        if(y + 1 < ROW_COUNT && x + 1 < COL_COUNT && getCell(x + 1, y + 1) != self && getCell(x + 1, y + 1) != Cell.EMPTY.getValue())
            is_adjacent = true;

        // down
        if(y + 1 < ROW_COUNT && getCell(x, y + 1) != self && getCell(x, y + 1) != Cell.EMPTY.getValue())
            is_adjacent = true;

        // down left
        if(y + 1 < ROW_COUNT && x - 1 >= 0 && getCell(x - 1, y + 1) != self && getCell(x - 1, y + 1) != Cell.EMPTY.getValue())
            is_adjacent = true;

        // left
        if(x - 1 >= 0 && getCell(x - 1, y) != self && getCell(x - 1, y) != Cell.EMPTY.getValue())
            is_adjacent = true;

        return is_adjacent;
    }

    public int calculatePlayerDisks(int player) {
        int score = 0;
        for(int x = 0; x < ROW_COUNT; x++)
            for(int y = 0; y < COL_COUNT; y++)
                if(getCell(x, y) == player)
                    score++;

        return score;
    }

    public boolean validMovesExist(int player) {
        Board b = getValidMoves(player);

        for(int x = 0; x < ROW_COUNT; x++)
            for(int y = 0; y < COL_COUNT; y++)
                if(b.getCell(x, y) == 9)
                    return true;

        return false;
    }

    public boolean noEmptyCellsLeft() {
        for(int x = 0; x < ROW_COUNT; x++)
            for(int y = 0; y < COL_COUNT; y++)
                if(getCell(x, y) == Cell.EMPTY.getValue())
                    return false;
        return true;
    }
}
