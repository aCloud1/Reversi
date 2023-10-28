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
    GraphicsContext graphics_context;
    Scene scene;
    Random random;

    boolean valid_moves_left = true;
    boolean player1_turn = true;

    Board board;
    Renderer renderer;
    Player playerRED, playerBLUE;


    @Override
    public void start(Stage stage) {
        random = new Random();
        board = new Board(
            ROW_COUNT,
            COL_COUNT,
            new int[][]{
                { 2, 0, 0, 2, 0, 0, 0, 0 },
                { 2, 0, 0, 2, 0, 2, 0, 0 },
                { 2, 0, 0, 2, 0, 2, 0, 1 },
                { 2, 0, 0, 2, 0, 2, 0, 1 },
                { 2, 0, 0, 2, 0, 2, 0, 1 },
                { 2, 0, 0, 0, 0, 2, 0, 1 },
                { 0, 0, 0, 0, 0, 2, 0, 1 },
                { 1, 2, 0, 0, 0, 2, 0, 1 },
            }
        );

        // vvv   this is renderer logic
        stage.setTitle("Reversi");
        root = new Group();
        canvas = new Canvas(WIDTH, HEIGHT);
        graphics_context = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        // ^^^

        renderer = new Renderer(this, graphics_context);

        drawBoard();

//        playerRED = new Player(Cell.PLAYER1.getValue(), board);
//        playerBLUE = new Player(Cell.PLAYER2.getValue(), board);


        // this method is input handling. todo: extract other methods from it
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // input logic
                System.out.printf("coord: x=%d, y=%d", (int)event.getX(), (int)event.getY());
                Pair<Integer, Integer> selected_cell = Renderer.getArrayIndicesFromCoordinates((int)event.getX(), (int)event.getY(), CELL_WIDTH, CELL_HEIGHT);
                int selected_x = selected_cell.getKey();
                int selected_y = selected_cell.getValue();
                System.out.printf("\tcell: [x=%d, y=%d]%n", selected_x, selected_y);
                if(board.getCell(selected_x, selected_y) != Cell.EMPTY.getValue())
                    return;

                // GameLogic class logic
                if(is1stPlayerTurn())
                    handlePlayerTurn(Cell.PLAYER1.getValue(), Cell.PLAYER2.getValue(), selected_x, selected_y);  // playerRED.handle();
                else
                    handlePlayerTurn(Cell.PLAYER2.getValue(), Cell.PLAYER1.getValue(), selected_x, selected_y);

                // renderer logic
                drawBoard();
                System.out.println();
            }
        });
    }

    public void handlePlayerTurn(int self, int opponent, int x, int y) {
        Vector<Pair<Integer, Integer>> enemy_cells;
        // game logic. This whole method should probably NOT be in Player class
        if(!board.isAdjacentToOpponent(opponent, x, y))
            return;

        enemy_cells = board.getCellsSurroundingOpponent(self, opponent, x, y);
        if(enemy_cells.isEmpty()) {
            System.out.println("EMPTY");
            return;
        }

        // change opponents disks to your own. Board logic
        for(Pair<Integer, Integer> p : enemy_cells)
            board.setCell(p.getKey(), p.getValue(), self);
        board.setCell(x, y, self);  // put disk on the selected cell

        // its next players turn only after these previous checks
        // game logic
        changeTurns();
    }

    public boolean validMovesLeft() { return valid_moves_left; }
    public void setValidMovesLeft(boolean value) { valid_moves_left = value; }
    public boolean is1stPlayerTurn() { return player1_turn; }
    public void changeTurns() { player1_turn = !player1_turn; }

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
        Board valid_moves;
        Color ghost_color;
        if(is1stPlayerTurn()) {
            ghost_color = new Color(1.0f, 0.0f, 0.0f, 0.15f);
            valid_moves = getValidMoves(board, Cell.PLAYER1.getValue(), Cell.PLAYER2.getValue());
        }
        else {
            ghost_color = new Color(0.0f, 0.0f, 1.0f, 0.15f);
            valid_moves = getValidMoves(board, Cell.PLAYER2.getValue(), Cell.PLAYER1.getValue());
        }

        for(int x = 0; x < ROW_COUNT; x++)
        {
            for(int y = 0; y < COL_COUNT; y++)
            {
                // draw board
                if((x+y) % 2 == 0)  graphics_context.setFill(Color.GRAY);
                else                graphics_context.setFill(Color.DARKGRAY);
                graphics_context.fillRect(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);

                if(valid_moves.getCell(x, y) == 9)
                    graphics_context.setFill(ghost_color);

                // draw disks
                if(board.getCell(x, y) == Cell.PLAYER1.getValue()) {
                    graphics_context.setFill(Color.RED);
                }
                else if(board.getCell(x, y) == Cell.PLAYER2.getValue()) {
                    graphics_context.setFill(Color.BLUE);
                }
                graphics_context.fillOval(x * CELL_WIDTH, y * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    public Board getValidMoves(Board board, int self, int opponent) {
        Board valid_moves = new Board(ROW_COUNT, COL_COUNT);

        for(int x = 0; x < ROW_COUNT; x++)
            for(int y = 0; y < COL_COUNT; y++)
                if(board.isAdjacentToOpponent(opponent, x, y))
                    if(!(board.getCellsSurroundingOpponent(self, opponent, x, y).isEmpty()))
                        valid_moves.setCell(x, y, 9);

        return valid_moves;
    }

    public static void main(String[] args) {
        launch();
    }
}