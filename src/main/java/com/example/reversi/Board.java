package com.example.reversi;

import javafx.util.Pair;

import java.util.ArrayList;

public class Board {
    final int ROW_COUNT;
    final int COL_COUNT;
    private int[][] board;

    public Board() {
        this.ROW_COUNT = 8;
        this.COL_COUNT = 8;
        this.board = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 2, 0, 0, 0},
                {0, 0, 0, 2, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        };
    }

    public Board(int row_count, int col_count) {
        this.ROW_COUNT = row_count;
        this.COL_COUNT = col_count;
        this.board = new int[row_count][col_count];
    }

    public Board(int[][] board) {
        this.ROW_COUNT = board.length;
        int temp = 0;
        if(board.length != 0)
            temp = board[0].length;
        this.COL_COUNT = temp;
        this.board = board;
    }

    public int getCell(int x, int y) {
        return board[x][y];
    }

    public void setCell(int x, int y, int value) {
        board[x][y] = value;
    }

    public Pair<Integer, Integer> getDimensions() {
        return new Pair<>(ROW_COUNT, COL_COUNT);
    }

    public ArrayList<Point> getCellsSurroundingOpponent(int self, Point p) {
        ArrayList<Point> cells = new ArrayList<>();
        int x = p.getX();
        int y = p.getY();

        cells.addAll(checkHorizontally(self, x, y));
        cells.addAll(checkVertically(self, x, y));
        cells.addAll(checkDiagonally(self, x, y));

        return cells;
    }

    public ArrayList<Point> checkHorizontally(int self, int clicked_x, int clicked_y) {
        ArrayList<Point> cells = new ArrayList<>();
        ArrayList<Point> temp = new ArrayList<>();

        for(int y = clicked_y + 1; y < COL_COUNT; y++)
            if(handleStep(clicked_x, y, self, temp))
                break;
        concat(cells, temp);

        for(int y = clicked_y - 1; y >= 0; y--)
            if(handleStep(clicked_x, y, self, temp))
                break;
        concat(cells, temp);

        return cells;
    }

    public ArrayList<Point> checkVertically(int self, int clicked_x, int clicked_y) {
        ArrayList<Point> cells = new ArrayList<>();
        ArrayList<Point> temp = new ArrayList<>();

        for (int x = clicked_x + 1; x < ROW_COUNT; x++)
            if(handleStep(x, clicked_y, self, temp))
                break;
        concat(cells, temp);

        for (int x = clicked_x - 1; x >= 0; x--)
            if(handleStep(x, clicked_y, self, temp))
                break;
        concat(cells, temp);

        return cells;
    }

    public ArrayList<Point> checkDiagonally(int self, int clicked_x, int clicked_y) {
        ArrayList<Point> cells = new ArrayList<>();
        ArrayList<Point> temp = new ArrayList<>();

        for(int i = clicked_x - 1, j = clicked_y - 1; i >= 0 && j >= 0; i--, j--)
            if(handleStep(i, j, self, temp))
                break;
        concat(cells, temp);

        for(int i = clicked_x + 1, j = clicked_y + 1; i < ROW_COUNT && j < COL_COUNT; i++, j++)
            if(handleStep(i, j, self, temp))
                break;
        concat(cells, temp);


        for(int i = clicked_x - 1, j = clicked_y + 1; i >= 0 && j < COL_COUNT; i--, j++)
            if(handleStep(i, j, self, temp))
                break;
        concat(cells, temp);

        for(int i = clicked_x + 1, j = clicked_y - 1; i < ROW_COUNT && j >= 0; i++, j--)
            if(handleStep(i, j, self, temp))
                break;
        concat(cells, temp);

        return cells;
    }

    private void concat(ArrayList<Point> cells, ArrayList<Point> temp) {
        cells.addAll(temp);
        temp.clear();
    }

    private boolean handleStep(int x, int y, int self, ArrayList<Point> cells) {
        int cell_value = getCell(x, y);
        if(cell_value == Cell.EMPTY.getValue()) {
            cells.clear();
            return true;
        }
        else if(cell_value == self) {
            return true;
        }
        else {
            cells.add(new Point(x, y));
            return false;
        }
    }

    public Board getValidMoves(int player_id) {
        Board valid_moves = new Board(ROW_COUNT, COL_COUNT);
        for (int x = 0; x < ROW_COUNT; x++)
            for (int y = 0; y < COL_COUNT; y++)
                if (isMoveValid(x, y, player_id))
                    valid_moves.setCell(x, y, 9);

        return valid_moves;
    }

    public boolean isMoveValid(int x, int y, int player_id) {
        if (getCell(x, y) != Cell.EMPTY.getValue())
            return false;

        Point point = new Point(x, y);
        if (!isAdjacentToOpponent(player_id, point))
            return false;

        if (getCellsSurroundingOpponent(player_id, point).isEmpty())
            return false;

        return true;
    }

    public boolean isAdjacentToOpponent(int self, Point point) {
        ArrayList<Point> adjacent_cell_coords = getAdjacentCells(point);
        for (Point p : adjacent_cell_coords)
            if (coordinateExists(p) && isCoordinateValid(p, self))
                return true;

        return false;
    }

    private ArrayList<Point> getAdjacentCells(Point p) {
        ArrayList<Point> adjacent_cells = new ArrayList<>();
        int x = p.getX();
        int y = p.getY();

        adjacent_cells.add(new Point(x - 1, y - 1));
        adjacent_cells.add(new Point(x, y - 1));
        adjacent_cells.add(new Point(x + 1, y - 1));

        adjacent_cells.add(new Point(x - 1, y));
        adjacent_cells.add(new Point(x + 1, y));

        adjacent_cells.add(new Point(x - 1, y + 1));
        adjacent_cells.add(new Point(x, y + 1));
        adjacent_cells.add(new Point(x + 1, y + 1));

        return adjacent_cells;
    }

    private boolean coordinateExists(Point p) {
        if (p.getX() < 0 || p.getX() >= ROW_COUNT)
            return false;

        if (p.getY() < 0 || p.getY() >= COL_COUNT)
            return false;

        return true;
    }

    private boolean isCoordinateValid(Point p, int self) {
        int value = getCell(p.getX(), p.getY());
        return value != self && value != Cell.EMPTY.getValue();
    }

    public int calculatePlayerDisks(int player) {
        int score = 0;
        for (int x = 0; x < ROW_COUNT; x++)
            for (int y = 0; y < COL_COUNT; y++)
                if (getCell(x, y) == player)
                    score++;

        return score;
    }

    public boolean validMovesExist(int player) {
        Board b = getValidMoves(player);
        for (int x = 0; x < ROW_COUNT; x++)
            for (int y = 0; y < COL_COUNT; y++)
                if (b.getCell(x, y) == 9)
                    return true;

        return false;
    }

    public boolean noEmptyCellsLeft() {
        for (int x = 0; x < ROW_COUNT; x++)
            for (int y = 0; y < COL_COUNT; y++)
                if (cellIsEmpty(x, y))
                    return false;

        return true;
    }

    private boolean cellIsEmpty(int x, int y) {
        return getCell(x, y) == Cell.EMPTY.getValue();
    }

    public void changeCellsTo(int disk_type, ArrayList<Point> coords) {
        for (Point p : coords)
            setCell(p.getX(), p.getY(), disk_type);
    }
}
