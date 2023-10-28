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

import static com.example.reversi.Util.getArrayIndicesFromCoordinates;

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

    Board board;


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

        board = new Board(ROW_COUNT, COL_COUNT);
        drawBoard();

//        Player playerRED = new Player(Cell.PLAYER1.getValue(), board);
//        Player playerBLUE = new Player(Cell.PLAYER2.getValue(), board);


        // this method is input handling. todo: extract other methods from it
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // input logic
                System.out.printf("coord: x=%d, y=%d", (int)event.getX(), (int)event.getY());
                Pair<Integer, Integer> selected_cell = Util.getArrayIndicesFromCoordinates((int)event.getX(), (int)event.getY(), CELL_WIDTH, CELL_HEIGHT);
                int selected_x = selected_cell.getKey();
                int selected_y = selected_cell.getValue();
                System.out.printf("\tcell: [x=%d, y=%d]%n", selected_x, selected_y);
                if(board.getCell(selected_x, selected_y) != Cell.EMPTY.getValue())
                    return;

                // game logic
                if(player1_turn)
                    // player class logic
                    handlePlayer1Turn(selected_x, selected_y);  // playerRED.handle();
                else
                    handlePlayer2Turn(selected_x, selected_y);

                // renderer logic
                drawBoard();
                System.out.println();
            }
        });
    }

    public void handlePlayer1Turn(int x, int y) {
        Vector<Pair<Integer, Integer>> enemy_cells;

        if(!board.isAdjacentToOpponent(Cell.PLAYER2.getValue(), x, y))
            return;

        enemy_cells = board.getCellsSurroundingOpponent(Cell.PLAYER1.getValue(), Cell.PLAYER2.getValue(), x, y);
        if(enemy_cells.isEmpty()) {
            System.out.println("EMPTY");
            return;
        }

        // change opponents disks to your own
        for(Pair<Integer, Integer> p : enemy_cells)
            board.setCell(p.getKey(), p.getValue(), Cell.PLAYER1.getValue());

        board.setCell(x, y, Cell.PLAYER1.getValue());

        // its next players turn only after these previous checks
        // game logic
        player1_turn = !player1_turn;
    }

    public void handlePlayer2Turn(int x, int y) {
        Vector<Pair<Integer, Integer>> enemy_cells;
        if(!board.isAdjacentToOpponent(Cell.PLAYER1.getValue(), x, y))
            return;

        enemy_cells = board.getCellsSurroundingOpponent(Cell.PLAYER2.getValue(), Cell.PLAYER1.getValue(), x, y);
        if(enemy_cells.isEmpty()) {
            System.out.println("EMPTY");
            return;
        }

        for(Pair<Integer, Integer> p : enemy_cells)
            board.setCell(p.getKey(), p.getValue(), Cell.PLAYER2.getValue());

        board.setCell(x, y, Cell.PLAYER2.getValue());

        // game logic
        player1_turn = !player1_turn;
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
                    if(board.isAdjacentToOpponent(Cell.PLAYER2.value, x, y))
                        if(!(board.getCellsSurroundingOpponent(Cell.PLAYER1.getValue(), Cell.PLAYER2.getValue(), x, y).isEmpty()))
                            gc.setFill(new Color(1.0f, 0.0f, 0.0f, 0.15f));
                }
                else {
                    if(board.isAdjacentToOpponent(Cell.PLAYER1.value, x, y))
                        if(!(board.getCellsSurroundingOpponent(Cell.PLAYER2.getValue(), Cell.PLAYER1.getValue(), x, y).isEmpty()))
                            gc.setFill(new Color(0.0f, 0.0f, 1.0f, 0.15f));
                }

                // draw disks
                if(board.getCell(x, y) == Cell.PLAYER1.getValue()) {
                    gc.setFill(Color.RED);
                }
                else if(board.getCell(x, y) == Cell.PLAYER2.getValue()) {
                    gc.setFill(Color.BLUE);
                }
                gc.fillOval(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}