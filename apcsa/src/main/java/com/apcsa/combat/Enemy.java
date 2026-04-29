package com.apcsa.combat;

import com.apcsa.GameWorld;

public abstract class Enemy {

    protected int hp;
    protected int maxHp;
    protected double speed;
    protected int reward;

    protected double x;
    protected double y;

    protected int tileX;
    protected int tileY;

    protected boolean dead;
    protected boolean reachedEnd;

    public Enemy(int tX, int tY, int m, double s, int r) {
        tileX = tX;
        tileY = tY;

        this.x = tileX;
        this.y = tileY;

        maxHp = m;
        hp = m;
        speed = s;
        reward = r;

        dead = false;
        reachedEnd = false;

        GameWorld.enemies.add(this);
    }
    /**
     * Updates enemy for one frame of the game.
     * For now, this moves the enemy to the right based on its speed.
     *
     * @param deltaTime the amount of time, in seconds, since last update
     */
    public void update(double deltaTime) {
        x += speed * deltaTime;
        tileX = (int) x;
    }

    /**
     * Damages this enemy. If health reaches 0, enemy dies
     *
     * @param damage the amount of damage taken
     */
    public void takeDamage(int damage) {
        hp -= damage;

        if (hp <= 0) {
            hp = 0;
            dead = true;
            remove(this);
        }
    }

    public void add(Enemy enemy){
        GameWorld.enemies.add(enemy);
    }

    public void remove(Enemy enemy){
        GameWorld.enemies.remove(enemy);
    }

    public boolean isDead() {
        return dead;
    }

    public boolean hasReachedEnd() {
        return reachedEnd;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public double getSpeed() {
        return speed;
    }

    public int getReward() {
        return reward;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }
}