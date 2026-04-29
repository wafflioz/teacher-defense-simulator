package com.apcsa.combat;

import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;
import com.apcsa.combat.enemies.FulkSmallestMinion;
import com.apcsa.combat.towers.Signore;

import java.util.ArrayList;

public class TestCombat { //class to test what Neelesh made

    public static void main(String[] args) {
        Tower tower = new Signore(4, 0);
        Enemy enemy = new FulkSmallestMinion(3, 0);

        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);

        long lastTime = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            enemy.update(deltaTime);
            tower.update(deltaTime, enemies);

            System.out.println("Frame " + i);
            System.out.println("Delta time: " + deltaTime);
            System.out.println("Enemy X: " + enemy.getX());
            System.out.println("Tower state: " + tower.getState());
            System.out.println("Enemy HP: " + enemy.getHp());
            System.out.println();

            try {
                Thread.sleep(16); // about 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}