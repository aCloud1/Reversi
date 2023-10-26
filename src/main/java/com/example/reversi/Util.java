package com.example.reversi;

import javafx.util.Pair;

public class Util {
    public static Pair<Integer, Integer> getArrayIndicesFromCoordinates(int x, int y, int CELL_WIDTH, int CELL_HEIGHT) {
        return new Pair<>(x / CELL_WIDTH, y / CELL_HEIGHT);
    }
}
