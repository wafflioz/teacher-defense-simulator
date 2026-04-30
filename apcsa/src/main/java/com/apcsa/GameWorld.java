package com.apcsa;

import java.util.*;
import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;

public class GameWorld {

    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Tower> towers = new ArrayList<>();
    
    public static void startGameLoop() {
        Thread gameThread = new Thread(() -> runGameLoop());
        gameThread.start();
    }

    private static void runGameLoop() {
        long lastTime = System.nanoTime();
        int frame = 0;

        while (true) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            Iterator<Enemy> enemyIt = enemies.iterator();

            while (enemyIt.hasNext()) {
                Enemy enemy = enemyIt.next();

                if (enemy.isDead()) {
                    enemyIt.remove();
                } else {
                    enemy.update(deltaTime);
                }
            }

            Iterator<Tower> towerIt = towers.iterator();

            while (towerIt.hasNext()) {
                Tower tower = towerIt.next();

                if (tower.isRemoved()) {
                    towerIt.remove();
                } else {
                    tower.update(deltaTime, enemies);
                }
            }

            // print logs
            System.out.println("Frame " + frame);

            for (Enemy enemy : enemies) {
                System.out.println("Enemy X: " + enemy.getX());
                System.out.println("Enemy HP: " + enemy.getHp());
            }

            for (Tower tower : towers) {
                System.out.println("Tower state: " + tower.getState());
            }

            System.out.println();

            // delay, while loop runs every 60 frames
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break;
            }

            frame++;
        }
    }
}