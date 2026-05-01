package com.apcsa.combat;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

import java.util.HashMap;

import com.apcsa.Main;

public class Animations {

    public static final int FRAME_WIDTH = 512;
    public static final int FRAME_HEIGHT = 512;
    public static final int DRAW_SIZE = 64;

    private static HashMap<Tower, ImageView> towerViews = new HashMap<>();

    public static String[] getTowerFrame(Tower tower) {
        String towerName = tower.getName();
        int frame = tower.getAnimationFrame();
        String state = tower.getState().toString();

        String[] ret = new String[3];

        ret[0] = towerName;
        ret[1] = state + ".png";
        ret[2] = "" + (frame * FRAME_WIDTH);

        return ret;
    }

    public static void updateAnimations(Tower tower) {
        String[] data = getTowerFrame(tower);

        String path = "/fxml/sprites/" + data[0] + "/" + data[1];

        Image img = new Image(Animations.class.getResourceAsStream(path));

        Platform.runLater(() -> {
            ImageView view = towerViews.get(tower);

            if (view == null) {
                view = new ImageView();
                view.setFitWidth(DRAW_SIZE);
                view.setFitHeight(DRAW_SIZE);

                towerViews.put(tower, view);
                Main.pane.getChildren().add(view);
            }

            view.setImage(img);

            view.setViewport(
                new Rectangle2D(
                    Integer.parseInt(data[2]),
                    0,
                    FRAME_WIDTH,
                    FRAME_HEIGHT
                )
            );

            view.setX(tower.getTileX() * DRAW_SIZE);
            view.setY(tower.getTileY() * DRAW_SIZE);
        });
    }
}