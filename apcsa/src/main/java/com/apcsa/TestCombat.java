package com.apcsa;

import com.apcsa.combat.enemies.FulkSmallestMinion;
import com.apcsa.combat.towers.Signore;

public class TestCombat {

    public static void main(String[] args) {

        new Signore(4, 0);
        new FulkSmallestMinion(3, 0);

        GameWorld.runGameLoop();

    }
}