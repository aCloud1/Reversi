package com.example.reversi;

public class ScoreBoard {
    Board board;
    int player1_score;
    int player2_score;

    public ScoreBoard(Board board) {
        this.board = board;
    }

    public void update() {
        player1_score = board.calculatePlayerDisks(GamePanel.Cell.PLAYER1.getValue());
        player2_score = board.calculatePlayerDisks(GamePanel.Cell.PLAYER2.getValue());
    }

    @Override
    public String toString() {
        return String.format("<%d  |  %d>\n", player1_score, player2_score);
    }
}
