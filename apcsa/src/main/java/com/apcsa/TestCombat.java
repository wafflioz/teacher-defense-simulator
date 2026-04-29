package com.apcsa;

import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;
import com.apcsa.combat.enemies.FulkSmallestMinion;
import com.apcsa.combat.towers.Signore;

import java.util.ArrayList;

public class TestCombat {

    public static void main(String[] args) {

        new Signore(4, 0);
        new FulkSmallestMinion(3, 0);

        ArrayList<Enemy> enemies = GameWorld.enemies;
        ArrayList<Tower> towers = GameWorld.towers;

        long lastTime = System.nanoTime();

        for (int frame = 0; frame < 200; frame++) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            for (Enemy enemy : enemies) {
                enemy.update(deltaTime);
            }

            for (Tower tower : towers) {
                tower.update(deltaTime, enemies);
            }

            for (Enemy enemy : enemies) {
                System.out.println("Enemy X: " + enemy.getX());
                System.out.println("Enemy HP: " + enemy.getHp());
            }

            for (Tower tower : towers) {
                System.out.println("Tower state: " + tower.getState());
            }

            System.out.println();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}