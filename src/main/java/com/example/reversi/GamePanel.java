package com.example.reversi;
import javafx.util.Pair;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

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

        renderer = new Renderer(CELL_WIDTH, CELL_HEIGHT, this);

        board = new Board(
            ROW_COUNT,
            COL_COUNT,
            new int[][]{
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 2, 2, 2, 0, 0 },
                { 0, 0, 0, 1, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 },
            }
        );

        players = new LinkedList<>();
        players.add(new Player(Cell.PLAYER1.getValue(), board));
        players.add(new Player(Cell.PLAYER2.getValue(), board));
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
        // if no key pressed
        if(!input_handler.hasInput())
            return;

        // convert to array indices
        Pair<Integer, Integer> pos = renderer.getArrayIndicesFromCoordinates(input_handler.getX(), input_handler.getY());
        int x = pos.getKey();
        int y = pos.getValue();

        if(board.getCell(x, y) != Cell.EMPTY.getValue())
            return;

        boolean handled = players.getFirst().handle(x, y);
        if(handled) {
            changeTurns();
            score.update();
        }
        input_handler.inputHandled();
        System.out.println(score.toString());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.drawBoard(g, board, players.getFirst().color);
        g.dispose();
    }

    public boolean validMovesLeft() { return valid_moves_left; }
    public void setValidMovesLeft(boolean value) { valid_moves_left = value; }
    public boolean is1stPlayerTurn() { return player1_turn; }
    public void changeTurns() {
        // move the current player to the end of queue
        players.addLast(players.pop());
    }

}
