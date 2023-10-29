package com.example.reversi;

import javafx.util.Pair;
import java.awt.*;

public class Renderer {
    int CELL_WIDTH, CELL_HEIGHT;

    public Renderer(int cell_width, int cell_height) {
        this.CELL_WIDTH = cell_width;
        this.CELL_HEIGHT = cell_height;
    }

    public void drawBoard(Graphics g, Board board, Board valid_moves, boolean first_players_turn) {
        Color ghost_color;
        if(first_players_turn) ghost_color = new Color(1.0f, 0.0f, 0.0f, 0.15f);
        else ghost_color = new Color(0.0f, 0.0f, 1.0f, 0.15f);

        for(int x = 0; x < board.ROW_COUNT; x++)
        {
            for(int y = 0; y < board.COL_COUNT; y++)
            {
                // draw board
                if((x+y) % 2 == 0)  g.setColor(Color.GRAY);
                else                g.setColor(Color.DARK_GRAY);
                g.fillRect(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);

                if(valid_moves.getCell(x, y) == 9)
                    g.setColor(ghost_color);

                // draw disks
                if(board.getCell(x, y) == GamePanel.Cell.PLAYER1.getValue()) {
                    g.setColor(Color.RED);
                }
                else if(board.getCell(x, y) == GamePanel.Cell.PLAYER2.getValue()) {
                    g.setColor(Color.BLUE);
                }
                g.fillOval(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    public Pair<Integer, Integer> getArrayIndicesFromCoordinates(int x, int y) {
        return new Pair<>(x / CELL_WIDTH, y / CELL_HEIGHT);
    }

}
