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
    Thread game_thread;
    Board board;
    Renderer renderer;
    InputHandler input_handler;
    FPS fps;
    LinkedList<Player> players;

    private boolean game_over;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        renderer = new Renderer(WIDTH, HEIGHT, CELL_WIDTH, CELL_HEIGHT, this);

        board = new Board(
            ROW_COUNT,
            COL_COUNT,
            new int[][]{
                { 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1 },
                { 2, 1, 1, 1, 1, 1, 1, 0 }
            }
        );

        players = new LinkedList<>();
        players.add(new Player("BLUE", Cell.PLAYER2.getValue(), board));
        players.add(new Player("RED", Cell.PLAYER1.getValue(), board));

        game_over = false;

        fps = FPS.getInstance();
        input_handler = new InputHandler();
        this.addMouseListener(input_handler);
    }

    public void startGameThread() {
        game_thread = new Thread(this);
        game_thread.start();
    }


    @Override
    public void run() {
        fps.init();

        while(game_thread != null) {
            update();
            repaint();
            fps.tick();
        }
    }

    public void update() {
        if(board.noEmptyCellsLeft() || !playerWithValidMovesExist()) {
            game_over = true;
            game_thread = null;
        }

        // check if valid moves exist
        players.getFirst().setSkippingTurn(!board.validMovesExist(players.getFirst().getDiskType()));
        if(players.getFirst().isSkippingTurn()) {
            changeTurns();
            return;
        }

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
            players.getFirst().setScore(board.calculatePlayerDisks(Cell.PLAYER1.getValue()));
        }
        input_handler.inputHandled();
    }

    private boolean playerWithValidMovesExist() {
        for(Player p : players)
            if(!p.isSkippingTurn())
                return true;
        return false;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!game_over)
            renderer.drawBoard(g, board, players.getFirst().getDiskType());
        else
            renderer.drawText(g, String.format(ScoreBoard.getWinnerText(players)));

        g.dispose();
    }

    public void changeTurns() {
        // move the current player to the end of queue
        players.addLast(players.pop());
    }

}
