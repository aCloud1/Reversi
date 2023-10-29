package com.example.reversi;
import javafx.util.Pair;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class GamePanel extends JPanel implements Runnable {
    public final int WIDTH = 400;
    public final int HEIGHT = 400;
    public final int ROW_COUNT = 8;
    public final int COL_COUNT = 8;
    public final int CELL_WIDTH = WIDTH / ROW_COUNT;
    public final int CELL_HEIGHT = HEIGHT / COL_COUNT;
    boolean valid_moves_left = true;
    boolean player1_turn = true;
    Thread game_thread;
    Board board;
    ScoreBoard score;
    Renderer renderer;

    InputHandler input_handler;

    LinkedList<Player> players;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        renderer = new Renderer(CELL_WIDTH, CELL_HEIGHT);

        board = new Board(
            ROW_COUNT,
            COL_COUNT,
            new int[][]{
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 2, 2, 0, 0, 0 },
                { 0, 0, 0, 0, 2, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
            }
        );

        players = new LinkedList<>();
        players.add(new Player(Cell.PLAYER1.value));
        players.add(new Player(Cell.PLAYER2.value));
        players.get(0).turn = true;

        score = new ScoreBoard(board);

        input_handler = new InputHandler();
        this.addMouseListener(input_handler);
    }

    public void startGameThread() {
        game_thread = new Thread(this);
        game_thread.start();
    }


    // the game loop
    @Override
    public void run() {
        while(game_thread != null) {
            update();
            repaint();
        }
    }

    public void update() {
        if(!input_handler.hasInput())
            return;

        // convert to array indices
        Pair<Integer, Integer> pos = renderer.getArrayIndicesFromCoordinates(input_handler.getX(), input_handler.getY());
        int x = pos.getKey();
        int y = pos.getValue();


        if(board.getCell(x, y) != GamePanel.Cell.EMPTY.getValue())
            return;

        if(is1stPlayerTurn())
            handlePlayerTurn(Cell.PLAYER1.getValue(), x, y);
        else
            handlePlayerTurn(Cell.PLAYER2.getValue(), x, y);

        input_handler.inputHandled();
        score.update();
        System.out.println(score.toString());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Board valid_moves;
        if(is1stPlayerTurn()) {
            valid_moves = getValidMoves(board, Cell.PLAYER1.value);
        } else {
            valid_moves = getValidMoves(board, Cell.PLAYER2.value);
        }

        renderer.drawBoard(g, board, valid_moves, is1stPlayerTurn());
        g.dispose();
    }


    public void handlePlayerTurn(int self, int x, int y) {
        Vector<Pair<Integer, Integer>> enemy_cells;
        // game logic. This whole method should probably NOT be in Player class
        if(!board.isAdjacentToOpponent(self, x, y))
            return;

        enemy_cells = board.getCellsSurroundingOpponent(self, x, y);
        if(enemy_cells.isEmpty()) {
            System.out.println("EMPTY");
            return;
        }

        // change opponents disks to your own. Board logic
        for(Pair<Integer, Integer> p : enemy_cells)
            board.setCell(p.getKey(), p.getValue(), self);
        board.setCell(x, y, self);  // put disk on the selected cell

        // its next players turn only after these previous checks
        // game logic
        changeTurns();
    }

    public boolean validMovesLeft() { return valid_moves_left; }
    public void setValidMovesLeft(boolean value) { valid_moves_left = value; }
    public boolean is1stPlayerTurn() { return player1_turn; }
    public void changeTurns() {
        player1_turn = !player1_turn;
    }

    public enum Cell {
        EMPTY(0),
        PLAYER1(1),
        PLAYER2(2);

        private final int value;

        Cell(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public Board getValidMoves(Board board, int self) {
        Board valid_moves = new Board(ROW_COUNT, COL_COUNT);

        for(int x = 0; x < ROW_COUNT; x++)
            for(int y = 0; y < COL_COUNT; y++)
                if(board.isAdjacentToOpponent(self, x, y))
                    if(!(board.getCellsSurroundingOpponent(self, x, y).isEmpty()))
                        valid_moves.setCell(x, y, 9);

        return valid_moves;
    }
}
