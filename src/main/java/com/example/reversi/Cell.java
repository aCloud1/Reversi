package com.example.reversi;

public enum Cell {
    EMPTY(0),
    PLAYER1(1),
    PLAYER2(2),
    GHOST(9);

    private final int value;

    Cell(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
