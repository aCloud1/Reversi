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

import java.util.Random;

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
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
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
                Pair<Integer, Integer> block = getArrayIndicesFromCoordinates((int)event.getX(), (int)event.getY());
                if(board[block.getValue()][block.getKey()] != Cell.EMPTY.getValue())
                    return;

                if(player1_turn)    board[block.getValue()][block.getKey()] = Cell.PLAYER1.getValue();
                else                board[block.getValue()][block.getKey()] = Cell.PLAYER2.getValue();
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
        for(int i = 0; i < ROW_COUNT; i++)
        {
            for(int j = 0; j < COL_COUNT; j++)
            {
                // draw board
                if((i+j) % 2 == 0)  gc.setFill(Color.GRAY);
                else                gc.setFill(Color.DARKGRAY);
                gc.fillRect(i * CELL_WIDTH, j * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);

                // draw disks
                if(board[j][i] == Cell.PLAYER1.getValue()) {
                    gc.setFill(Color.RED);
                }
                else if(board[j][i] == Cell.PLAYER2.getValue()) {
                    gc.setFill(Color.BLUE);
                }
                gc.fillOval(i * CELL_WIDTH, j * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    public void computerOpponentMove() {
        Pair<Integer, Integer> temp;
        do {
            temp = new Pair<>(random.nextInt(0, 8), random.nextInt(0, 8));
        } while(board[temp.getValue()][temp.getKey()] != Cell.EMPTY.getValue());
        board[temp.getValue()][temp.getKey()] = Cell.PLAYER2.getValue();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public Pair<Integer, Integer> getArrayIndicesFromCoordinates(int x, int y) {
        return new Pair<>(x / CELL_WIDTH, y / CELL_HEIGHT);
    }

    public static void main(String[] args) {
        launch();
    }
}