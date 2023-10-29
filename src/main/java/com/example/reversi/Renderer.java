package com.example.reversi;

import javafx.util.Pair;
import java.awt.*;
import java.util.Hashtable;

public class Renderer {
    int CELL_WIDTH, CELL_HEIGHT;
    GamePanel game;

    Hashtable<Integer, Color> player_to_color, player_to_ghost_color;
    public Renderer(int cell_width, int cell_height, GamePanel game) {
        this.CELL_WIDTH = cell_width;
        this.CELL_HEIGHT = cell_height;
        this.game = game;

        player_to_color = new Hashtable<>();
        player_to_color.put(Cell.PLAYER1.getValue(), new Color(1.0f, 0.0f, 0.0f, 1.0f));
        player_to_color.put(Cell.PLAYER2.getValue(), new Color(0.0f, 0.0f, 1.0f, 1.0f));

        player_to_ghost_color = new Hashtable<>();
        player_to_ghost_color.put(Cell.PLAYER1.getValue(), new Color(1.0f, 0.0f, 0.0f, 0.15f));
        player_to_ghost_color.put(Cell.PLAYER2.getValue(), new Color(0.0f, 0.0f, 1.0f, 0.15f));
    }

    public void drawBoard(Graphics g, Board board, Board valid_moves, int player_color) {
        for(int x = 0; x < board.ROW_COUNT; x++)
        {
            for(int y = 0; y < board.COL_COUNT; y++)
            {
                // draw board
                if((x+y) % 2 == 0)  g.setColor(Color.GRAY);
                else                g.setColor(Color.DARK_GRAY);
                g.fillRect(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);

                if(valid_moves.getCell(x, y) == 9)
                    g.setColor(player_to_ghost_color.get(player_color));

                // draw disks
                for(Player p : game.players) {
                    if(board.getCell(x, y) == p.color) {
                        g.setColor(player_to_color.get(p.color));
                        break;
                    }
                }

                g.fillOval(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    public Pair<Integer, Integer> getArrayIndicesFromCoordinates(int x, int y) {
        return new Pair<>(x / CELL_WIDTH, y / CELL_HEIGHT);
    }

}
