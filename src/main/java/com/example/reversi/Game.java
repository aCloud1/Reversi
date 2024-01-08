package com.example.reversi;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game implements Runnable {
    private Thread game_thread;
    private Board board;
    private PointsCalculator points_calculator;
    private InputHandler input_handler;
    private LinkedList<Player> players;
    private Renderer renderer;
    private boolean is_over;

    public Game(InputHandler input_handler, Renderer renderer) {
        this.input_handler = input_handler;
        this.renderer = renderer;

        board = new Board();
        points_calculator = PointsCalculator.getInstance(board);

        players = new LinkedList<>();
        players.add(new Player("RED", Cell.PLAYER1.getValue()));
        players.add(new Player("BLUE", Cell.PLAYER2.getValue()));

        is_over = false;
    }


    public void startGameThread() {
        game_thread = new Thread(this);
        game_thread.start();
    }


    @Override
    public void run() {
        while (game_thread != null)
            update();
    }

    public void render() {
        renderer.renderGame();
    }

    public void update() {
        if (gameShouldEnd()) {
            end();
            return;
        }

        Player current_player = players.getFirst();

        boolean skips_turn = determineIfPlayerHasToSkipTurn(current_player);
        current_player.setIsSkippingTurn(skips_turn);
        if (skips_turn) {
            prepareForNextPlayerTurn();
            return;
        }

        // if no key pressed
        if (!input_handler.hasInput())
            return;

        Point point = renderer.getPointToCellFromCoordinates(input_handler.getCoordinatesAsPoint(), board.getDimensions());
        input_handler.inputHandled();

        if (!board.isMoveValid(point.getX(), point.getY(), current_player.getDiskType()))
            return;

        finalizeTurn(point, current_player);
        prepareForNextPlayerTurn();
    }

    private boolean determineIfPlayerHasToSkipTurn(Player player) {
        return !board.validMovesExist(player.getDiskType());
    }

    private void prepareForNextPlayerTurn() {
        changeTurns();
        render();
    }

    private void finalizeTurn(Point point, Player player) {
        ArrayList<Point> cells = board.getCellsSurroundingOpponent(player.getDiskType(), point);
        cells.add(point);
        board.changeCellsTo(player.getDiskType(), cells);
        points_calculator.calculatePoints(player);
    }

    private boolean playerWithValidMovesExist() {
        for (Player p : players)
            if (!p.isSkippingTurn())
                return true;

        return false;
    }

    public void changeTurns() {
        players.addLast(players.pop());
    }

    private boolean gameShouldEnd() {
        return (board.noEmptyCellsLeft() || !playerWithValidMovesExist());
    }

    private void end() {
        is_over = true;
        game_thread = null;
    }

    public boolean isOver() {
        return is_over;
    }

    public Board getBoard() {
        return board;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }
}