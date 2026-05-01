package com.apcsa.combat;

import com.apcsa.combat.Tower;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Animations {

    public static final int FRAME_WIDTH = 512;
    public static final int FRAME_HEIGHT = 512;

    public static String[] getTowerFrame(Tower tower) {
        
        String towerName = tower.getName();
        int frame = tower.getAnimationFrame();
        String state = tower.getState().toString();

        String[] ret = new String[3];

        ret[0] = towerName; //name
        ret[1] = state + ".png";  //state
        ret[2] = "" + frame * FRAME_WIDTH; //frame

        return ret;
    }

    public static void updateAnimations(Tower tower) {
        String[] data = getTowerFrame(tower);

        String path = "/fxml/sprites/" + data[0] + "/" + data[1];

        Image img = new Image(Animations.class.getResourceAsStream(path));

        ImageView view = new ImageView(img);

        view.setViewport(
            new Rectangle2D(
                Integer.parseInt(data[2]), //x
                0,                    //y   
                FRAME_WIDTH,
                FRAME_HEIGHT
            )
        );
        
    }
}