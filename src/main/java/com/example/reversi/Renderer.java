package com.example.reversi;

import javafx.util.Pair;

import java.awt.*;
import java.util.Hashtable;
import java.util.LinkedList;

public class Renderer {
    int WINDOW_WIDTH, WINDOW_HEIGHT;

    private float ghost_opacity = 0.3f;
    private Color board_gray1, board_gray2;
    IGameRenderer game_renderer;

    Hashtable<Integer, Color> player_to_color, player_to_ghost_color;

    public Renderer(int window_width, int window_height, IGameRenderer game_renderer) {
        this.WINDOW_WIDTH = window_width;
        this.WINDOW_HEIGHT = window_height;
        this.game_renderer = game_renderer;

        player_to_color = new Hashtable<>();
        player_to_color.put(Cell.PLAYER1.getValue(), new Color(1.0f, 0.0f, 0.0f, 1.0f));
        player_to_color.put(Cell.PLAYER2.getValue(), new Color(0.0f, 0.0f, 1.0f, 1.0f));

        player_to_ghost_color = new Hashtable<>();
        player_to_ghost_color.put(Cell.PLAYER1.getValue(), new Color(1.0f, 0.0f, 0.0f, ghost_opacity));
        player_to_ghost_color.put(Cell.PLAYER2.getValue(), new Color(0.0f, 0.0f, 1.0f, ghost_opacity));

        board_gray1 = new Color(0.5f, 0.5f, 0.5f, 1.0f);
        board_gray2 = new Color(0.35f, 0.35f, 0.35f, 1.0f);
    }

    public void renderGame() {
        game_renderer.renderGame();
    }

    public void drawBoard(Graphics g, Board board, LinkedList<Player> players) {
        int player_color = players.getFirst().getDiskType();
        Board valid_moves = board.getValidMoves(player_color);
        Pair<Integer, Integer> cell_dimensions = getCellDimensions(board.getDimensions());

        for (int x = 0; x < board.ROW_COUNT; x++) {
            for (int y = 0; y < board.COL_COUNT; y++) {
                drawBoardCell(g, x, y, cell_dimensions);
                drawPlayerDiskGhost(g, valid_moves.getCell(x, y), player_to_ghost_color.get(player_color));
                drawPlayerDisk(g, x, y, players, board, cell_dimensions);
            }
        }
    }

    private void drawBoardCell(Graphics g, int x, int y, Pair<Integer, Integer> cell_dimensions) {
        if ((x + y) % 2 == 0) g.setColor(board_gray1);
        else g.setColor(board_gray2);
        g.fillRect(
                y * cell_dimensions.getValue(),
                x * cell_dimensions.getKey(),
                cell_dimensions.getValue(),
                cell_dimensions.getKey()
        );
    }

    private void drawPlayerDiskGhost(Graphics g, int current_cell_value, Color player_ghost_color) {
        if (current_cell_value == Cell.GHOST.getValue())
            g.setColor(player_ghost_color);
    }

    private void drawPlayerDisk(Graphics g, int x, int y, LinkedList<Player> players, Board board, Pair<Integer, Integer> cell_dimensions) {
        for (Player p : players) {
            if (board.getCell(x, y) == p.getDiskType()) {
                g.setColor(player_to_color.get(p.getDiskType()));
                break;
            }
        }
        drawOval(g, x, y, cell_dimensions);
    }

    private void drawOval(Graphics g, int x, int y, Pair<Integer, Integer> cell_dimensions) {
        g.fillOval(
                y * cell_dimensions.getValue(),
                x * cell_dimensions.getKey(),
                cell_dimensions.getValue(),
                cell_dimensions.getKey()
        );
    }

    public void drawText(Graphics g, String text) {
        Font font = new Font("Ubuntu", Font.PLAIN, 24);
        FontMetrics metrics = g.getFontMetrics(font);
        Rectangle rect = new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        g.setColor(Color.WHITE);
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(text,
                rect.x + (rect.width - metrics.stringWidth(text)) / 2,
                rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent()
        );
    }

    public Point getPointToCellFromCoordinates(Point p, Pair<Integer, Integer> board_dimensions) {
        Pair<Integer, Integer> cell_dimensions = getCellDimensions(board_dimensions);
        return new Point(
                p.getX() / cell_dimensions.getKey(),
                p.getY() / cell_dimensions.getValue()
        );
    }

    private Pair<Integer, Integer> getCellDimensions(Pair<Integer, Integer> board_dimensions) {
        return new Pair<>(
                WINDOW_WIDTH / board_dimensions.getValue(),
                WINDOW_HEIGHT / board_dimensions.getKey()
        );
    }
}
