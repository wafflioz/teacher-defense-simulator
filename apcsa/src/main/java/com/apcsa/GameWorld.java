// BOM removal rewrite
package com.apcsa;

import java.util.*;
import java.util.function.Supplier;

import com.apcsa.combat.Animations;
import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;
import com.apcsa.combat.enemies.FulkSmallestMinion;

public class GameWorld {

    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Tower> towers = new ArrayList<>();

    private static final List<Wave> WAVES = List.of(
        new Wave("tutorial", List.of(
            new WaveEntry("Minions", () -> new FulkSmallestMinion(0, 1), 3, 1.0)
        )),
        new Wave("wave1", List.of(
            new WaveEntry("Fast Minions", () -> new FulkSmallestMinion(0, 1), 5, 0.6)
        )),
        new Wave("bossfight ts mrbeast", List.of(
            /* replace ts with bfb once implemented */
            new WaveEntry("Slow Minions", () -> new FulkSmallestMinion(0, 1), 4, 0.8)
        ))
    );

    private static int currentWaveIndex = 0;
    private static Wave currentWave;
    private static boolean gameLoopStarted = false;

    public static String getCurrentWaveName() {
        if (currentWaveIndex < WAVES.size()) {
            return WAVES.get(currentWaveIndex).name;
        }
        return "Complete";
    }

    public static void startGameLoop() {
        if (gameLoopStarted) {
            return;
        }

        gameLoopStarted = true;
        currentWaveIndex = 0;
        currentWave = WAVES.get(currentWaveIndex);
        currentWave.start();

        Thread gameThread = new Thread(GameWorld::runGameLoop);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    private static void runGameLoop() {
        long lastTime = System.nanoTime();
        int frame = 0;

        while (true) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            if (currentWave != null) {
                currentWave.update(deltaTime);
                if (currentWave.isFinished() && enemies.isEmpty()) {
                    currentWaveIndex++;
                    if (currentWaveIndex < WAVES.size()) {
                        currentWave = WAVES.get(currentWaveIndex);
                        currentWave.start();
                    } else {
                        currentWave = null;
                    }
                }
            }

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
                    Animations.updateAnimations(tower);
                }
            }

            if (frame % 60 == 0) {
                System.out.println("Wave: " + getCurrentWaveName());
                System.out.println("Enemies: " + enemies.size());
                System.out.println("Towers: " + towers.size());
                System.out.println();
            }

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                break;
            }

            frame++;
        }
    }

    private static final class Wave {
        private final String name;
        private final List<WaveEntry> entries;
        private int entryIndex;
        private double timer;
        private boolean finished;

        public Wave(String name, List<WaveEntry> entries) {
            this.name = name;
            this.entries = entries;
            this.entryIndex = 0;
            this.timer = 0;
            this.finished = false;
        }

        public void start() {
            this.entryIndex = 0;
            this.finished = false;
            for (WaveEntry entry : entries) {
                entry.reset();
            }
            this.timer = entries.isEmpty() ? 0 : entries.get(0).delay;
        }

        public void update(double deltaTime) {
            if (finished || entries.isEmpty()) {
                return;
            }

            timer -= deltaTime;
            while (timer <= 0 && !finished) {
                WaveEntry entry = entries.get(entryIndex);
                entry.spawnOne();

                if (entry.isComplete()) {
                    entryIndex++;
                    if (entryIndex >= entries.size()) {
                        finished = true;
                        break;
                    }
                    timer += entries.get(entryIndex).delay;
                } else {
                    timer += entry.delay;
                }
            }
        }

        public boolean isFinished() {
            return finished;
        }
    }

    private static final class WaveEntry {
        private final Supplier<Enemy> spawner;
        private final int count;
        private final double delay;
        private int spawned;

        public WaveEntry(String name, Supplier<Enemy> spawner, int count, double delay) {
            this.spawner = spawner;
            this.count = count;
            this.delay = delay;
            this.spawned = 0;
        }

        public void spawnOne() {
            if (spawned < count) {
                spawner.get();
                spawned++;
            }
        }

        public boolean isComplete() {
            return spawned >= count;
        }

        public void reset() {
            spawned = 0;
        }
    }
}
