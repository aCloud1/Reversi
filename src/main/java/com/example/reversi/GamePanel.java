package com.example.reversi;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;

public class GamePanel extends JPanel {
    public final int WIDTH = 400;
    public final int HEIGHT = 400;

    Game game;
    Renderer renderer;
    InputHandler input_handler;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.input_handler = new InputHandler();

        renderer = new Renderer(WIDTH, HEIGHT, () -> repaint());
        game = new Game(input_handler, renderer);

        this.addMouseListener(input_handler);
    }

    public void start() {
        game.startGameThread();
    }

    public void paintComponent(Graphics g) {
        System.out.println("SCREEN UPDATED");
        super.paintComponent(g);
        if (!game.isOver())
            renderer.drawBoard(g, game.getBoard(), game.getPlayers());
        else
            renderer.drawText(g, PointsCalculator.getWinnerText(game.getPlayers()));

        g.dispose();
    }
}
