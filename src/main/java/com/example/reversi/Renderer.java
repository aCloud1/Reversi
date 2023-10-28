package com.example.reversi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class Renderer {
    Game game;
    int CELL_WIDTH, CELL_HEIGHT;
    GraphicsContext graphics_context;
    public Renderer(Game game, GraphicsContext gc) {
        this.game = game;
        this.graphics_context = gc;
    }

    public static Pair<Integer, Integer> getArrayIndicesFromCoordinates(int x, int y, int CELL_WIDTH, int CELL_HEIGHT) {
        return new Pair<>(x / CELL_WIDTH, y / CELL_HEIGHT);
    }

}
