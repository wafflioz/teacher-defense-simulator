package com.apcsa;

import com.apcsa.combat.Animations;
import com.apcsa.combat.Tower;
import com.apcsa.combat.towers.Signore;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final int TILE_SIZE = 64;

    @Override
    public void start(Stage stage) {
        Tower tower = new Signore(4, 0);
        Canvas canvas = new Canvas(800, 600);

        drawTower(canvas, tower);

        stage.setScene(new Scene(new Pane(canvas), 800, 600));
        stage.setTitle("Teacher Defense");
        stage.show();

        Timeline loop = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            tower.updateAnimation(0.1);
            drawTower(canvas, tower);
        }));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    private void drawTower(Canvas canvas, Tower tower) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        String[] frame = Animations.getTowerFrame(tower);
        Image sprite = new Image(Main.class.getResourceAsStream("/fxml/sprites/" + frame[0] + "/" + frame[1]));
        int frameX = Integer.parseInt(frame[2]);

        gc.drawImage(sprite,
            frameX, 0,
            Animations.FRAME_WIDTH, Animations.FRAME_HEIGHT,
            tower.getTileX() * TILE_SIZE, tower.getTileY() * TILE_SIZE,
            TILE_SIZE, TILE_SIZE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
