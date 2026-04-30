package com.apcsa.combat;

import com.apcsa.combat.Tower;
import javafx.geometry.Rectangle2D;

public class Animations {

    public static final int FRAME_WIDTH = 64;
    public static final int FRAME_HEIGHT = 64;

    public static Rectangle2D getTowerFrame(Tower tower) {
        int col = tower.getAnimationFrame();
        int row = getStateRow(tower.getState());

        return new Rectangle2D(
            col * FRAME_WIDTH,
            row * FRAME_HEIGHT,
            FRAME_WIDTH,
            FRAME_HEIGHT
        );
    }

    private static int getStateRow(Tower.TowerState state) {
        switch (state) {
            case IDLE:
                return 0;
            case COOLDOWN:
                return 1;
            case SHOOTING:
                return 2;
            default:
                return 0;
        }
    }
}