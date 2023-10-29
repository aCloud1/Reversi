package com.example.reversi;

import java.util.LinkedList;

public class ScoreBoard {
    Board board;

    public ScoreBoard(Board board) {
        this.board = board;
    }

    public static String getWinnerText(LinkedList<Player> players) {
        Player winner = players.getFirst();
        for(Player p : players) {
            if(p.getScore() > winner.getScore())
                winner = p;
        }

        return String.format("Winner: %s", winner);
    }
}
