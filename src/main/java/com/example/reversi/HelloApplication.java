package com.example.reversi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public int WIDTH = 800;
    public int HEIGHT = 800;
    public int ROW_COUNT = 8;
    public int COL_COUNT = 8;
    public final int ROW_WIDTH = WIDTH / ROW_COUNT;
    public final int COL_WIDTH = HEIGHT / COL_COUNT;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Reversi");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}