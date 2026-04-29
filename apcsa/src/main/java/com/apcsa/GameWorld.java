package com.apcsa;

import java.util.*;
import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;
import com.apcsa.combat.enemies.FulkSmallestMinion;
import com.apcsa.combat.towers.Signore;

public class GameWorld {

    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Tower> towers = new ArrayList<>();
    
    public static void runGameLoop() {

        long lastTime = System.nanoTime();
        int frame = 0;

        while(true){
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
            frame++;
        }
    }

}