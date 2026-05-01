package com.apcsa;

import com.apcsa.combat.enemies.FulkSmallestMinion;
import com.apcsa.combat.towers.Signore;

public class TestCombat {

    public static void runIt() {

        GameWorld.startGameLoop();

        new Signore(4, 4);
        new Signore(1,2);
        new Signore(5,7);
        new FulkSmallestMinion(0, 0);

    }
}