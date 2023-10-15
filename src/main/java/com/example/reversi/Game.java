package com.example.reversi;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Game extends Application {
    public final int WIDTH = 400;
    public final int HEIGHT = 400;
    public final int ROW_COUNT = 8;
    public final int COL_COUNT = 8;
    public final int CELL_WIDTH = WIDTH / ROW_COUNT;
    public final int CELL_HEIGHT = HEIGHT / COL_COUNT;

    Group root;
    Canvas canvas;
    GraphicsContext gc;
    Scene scene;
    Random random;

    boolean player1_turn = true;
    int[][] board = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 1, 2, 0, 0, 0 },
            { 0, 0, 0, 2, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
    };


    @Override
    public void start(Stage stage) {
        random = new Random();
        stage.setTitle("Reversi");
        root = new Group();
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


        drawBoard();

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.printf("coord: x=%d, y=%d", (int)event.getX(), (int)event.getY());
                Pair<Integer, Integer> selected_cell = getArrayIndicesFromCoordinates((int)event.getX(), (int)event.getY());
                int selected_x = selected_cell.getKey();
                int selected_y = selected_cell.getValue();
                System.out.printf("\tcell: [x=%d, y=%d]%n", selected_x, selected_y);
                if(board[selected_y][selected_x] != Cell.EMPTY.getValue())
                    return;

                Vector<Pair<Integer, Integer>> enemy_cells;
                if(player1_turn)
                {
                    if(!isAdjacentToOpponent(Cell.PLAYER2.getValue(), selected_x, selected_y))
                        return;

//                    enemy_cells = getCellsSurroundingOpponent(Cell.PLAYER1.getValue(), Cell.PLAYER2.getValue(), selected_x, selected_y);
//                    if(enemy_cells.isEmpty()) {
//                        System.out.println("EMPTY");
//                        return;
//                    }
//                    System.out.println(enemy_cells);
                    // changing opponents disks to your own
                    // ...

                    board[selected_y][selected_x] = Cell.PLAYER1.getValue();
                }
                else
                {
                    if(!isAdjacentToOpponent(Cell.PLAYER1.getValue(), selected_x, selected_y))
                        return;

//                    enemy_cells = getCellsSurroundingOpponent(Cell.PLAYER2.getValue(), Cell.PLAYER1.getValue(), selected_x, selected_y);
//                    if(enemy_cells.isEmpty())
//                        return;

                    // changing opponents disks to your own
                    // ...

                    board[selected_y][selected_x] = Cell.PLAYER2.getValue();
                }

                player1_turn = !player1_turn;
                drawBoard();
            }
        });
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

    public void drawBoard() {
        for(int x = 0; x < ROW_COUNT; x++)
        {
            for(int y = 0; y < COL_COUNT; y++)
            {
                // draw board
                if((x+y) % 2 == 0)  gc.setFill(Color.GRAY);
                else                gc.setFill(Color.DARKGRAY);
                gc.fillRect(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);

                // draw ghosts
                if(player1_turn) {
                    if(isAdjacentToOpponent(Cell.PLAYER2.value, x, y))
//                        if(!(getCellsSurroundingOpponent(Cell.PLAYER1.getValue(), Cell.PLAYER2.getValue(), y, x).isEmpty()))
                            gc.setFill(new Color(1.0f, 0.0f, 0.0f, 0.15f));
                }
                else {
                    if(isAdjacentToOpponent(Cell.PLAYER1.value, x, y))
//                        if(!(getCellsSurroundingOpponent(Cell.PLAYER2.getValue(), Cell.PLAYER1.getValue(), y, x).isEmpty()))
                            gc.setFill(new Color(0.0f, 0.0f, 1.0f, 0.15f));
                }

                // draw disks
                if(board[y][x] == Cell.PLAYER1.getValue()) {
                    gc.setFill(Color.RED);
                }
                else if(board[y][x] == Cell.PLAYER2.getValue()) {
                    gc.setFill(Color.BLUE);
                }
                gc.fillOval(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    public boolean isAdjacentToOpponent(int opponent, int x, int y) {
        boolean is_adjacent = false;
        // up left
        if(y - 1 >= 0 && x - 1 >= 0 && board[y - 1][x - 1] == opponent)
            is_adjacent = true;

        // up
        if(y - 1 >= 0 && board[y - 1][x] == opponent)
            is_adjacent = true;

        // up right
        if(y - 1 >= 0 && x + 1 < COL_COUNT && board[y - 1][x + 1] == opponent)
            is_adjacent = true;

        // right
        if(x + 1 < COL_COUNT && board[y][x + 1] == opponent)
            is_adjacent = true;

        // right down
        if(y + 1 < ROW_COUNT && x + 1 < COL_COUNT && board[y + 1][x + 1] == opponent)
            is_adjacent = true;

        // down
        if(y + 1 < ROW_COUNT && board[y + 1][x] == opponent)
            is_adjacent = true;

        // down left
        if(y + 1 < ROW_COUNT && x - 1 >= 0 && board[y + 1][x - 1] == opponent)
            is_adjacent = true;

        // left
        if(x - 1 >= 0 && board[y][x - 1] == opponent)
            is_adjacent = true;

        return is_adjacent;
    }

    public Vector<Pair<Integer, Integer>> getCellsSurroundingOpponent(int self, int opponent, int x, int y) {
        Vector<Pair<Integer, Integer>> cells = new Vector<>();
        Vector<Pair<Integer, Integer>> temp = new Vector<>();

        // left
        for(int i = x; i >= 0; i--)
        {
            if(board[y][i] == opponent)
            {
                temp.add(new Pair<>(y, i));
                System.out.println(i + " " + y);
            }
            else
            {
                if(board[y][i] == self)
                    cells.addAll(temp);
                break;
            }
        }
        System.out.println("TO THE LEFT IS: " + cells.size());
        temp.clear();
/*
        // right
        for(int i = x; i < COL_COUNT; i++) {
            if(board[y][i] == opponent) {
//                System.out.println(y + " " + i + " = " + opponent);
            }
        }

        // up
        for(int i = y; i >= 0; i--) {
            if(board[i][x] == opponent)
        }

        // down
        for(int i = y; i < ROW_COUNT; i++) {
            if(board[i][x] == opponent)
        }
*/
        return cells;
    }

    public Pair<Integer, Integer> getArrayIndicesFromCoordinates(int x, int y) {
        return new Pair<>(x / CELL_WIDTH, y / CELL_HEIGHT);
    }

    public static void main(String[] args) {
        launch();
    }
}