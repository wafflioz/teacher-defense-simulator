package com.apcsa;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    private static final int TILE_SIZE = 64;

    public static Pane pane;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);

        pane = new Pane(canvas);
        stage.setScene(new Scene(pane, 800, 600));
        stage.setTitle("Teacher Defense");
        stage.show();
        
        TestCombat.runIt();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
