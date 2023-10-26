package com.example.reversi;

public class Board {
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

    public Board() {
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCell(int x, int y) { return board[y][x]; }
    public void setCell(int x, int y, int value) { board[y][x] = value; }

//    public bool isAdjacentToOpponent() {
//    }
//
//    public getCellsSurroundingOpponent() {
//
//    }
}
