package com.example.reversi;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application {
    public final int WIDTH = 400;
    public final int HEIGHT = 400;
    public final int ROW_COUNT = 8;
    public final int COL_COUNT = 8;
    public final int CELL_WIDTH = WIDTH / ROW_COUNT;
    public final int CELL_HEIGHT = HEIGHT / COL_COUNT;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Game");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        // draw board
        for(int i = 0; i < ROW_COUNT; i++)
        {
            for(int j = 0; j < COL_COUNT; j++)
            {
                if((i+j) % 2 == 0)
                    gc.setFill(Color.GRAY);
                else
                    gc.setFill(Color.DARKGRAY);
                gc.fillRect(i * CELL_WIDTH, j * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}