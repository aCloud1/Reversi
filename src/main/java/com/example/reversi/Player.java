package com.example.reversi;

public class Player {
    private String name;
    private int disk_type;
    private boolean is_skipping_turn;
    private int score;

    public Player(String name, int disk_color) {
        this.name = name;
        this.disk_type = disk_color;
        this.is_skipping_turn = false;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public int getDiskType() {
        return disk_type;
    }

    public void setDiskType(int value) {
        this.disk_type = value;
    }

    public boolean isSkippingTurn() {
        return is_skipping_turn;
    }

    public void setIsSkippingTurn(boolean value) {
        this.is_skipping_turn = value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int value) {
        this.score = value;
    }

    @Override
    public String toString() {
        return String.format("%s - %d points!", name, getScore());
    }
}
