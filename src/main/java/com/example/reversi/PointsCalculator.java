package com.example.reversi;

import java.util.LinkedList;

public class PointsCalculator {
    private Board board;
    private static PointsCalculator points_calculator;

    private PointsCalculator(Board board) {
        this.board = board;
    }

    public static PointsCalculator getInstance(Board board) {
        if(points_calculator == null)
            points_calculator = new PointsCalculator(board);

        return points_calculator;
    }

    public void calculatePoints(Player player) {
        int disks = board.calculatePlayerDisks(player.getDiskType());
        player.setScore(disks);
    }

    public static String getWinnerText(LinkedList<Player> players) {
        if(isDraw(players))
            return "Draw!";

        Player winner = players.getFirst();
        for(Player p : players) {
            if(p.getScore() > winner.getScore())
                winner = p;
        }

        return String.format("Winner: %s", winner);
    }

    private static boolean isDraw(LinkedList<Player> players) {
        int score = players.getFirst().getScore();
        return players.stream().map(player ->
            player.getScore() == score
        ).reduce((a, b) -> a & b).orElse(false);
    }
}
