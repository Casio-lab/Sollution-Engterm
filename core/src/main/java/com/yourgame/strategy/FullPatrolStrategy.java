package com.yourgame.strategy;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class FullPatrolStrategy implements MovementStrategy {
    private boolean movingRight = true;
    private final float minX;
    private final float maxX;
    private final float speed = 100f;

    public FullPatrolStrategy(float minX, float maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public void move(Sprite enemy, float delta) {
        float x = enemy.getX();

        if (movingRight) {
            x += speed * delta;
            if (x > maxX - enemy.getWidth()) {
                x = maxX - enemy.getWidth();
                movingRight = false;
            }
        } else {
            x -= speed * delta;
            if (x < minX) {
                x = minX;
                movingRight = true;
            }
        }

        // Flip по направлению
        if ((movingRight && enemy.isFlipX()) || (!movingRight && !enemy.isFlipX())) {
            enemy.flip(true, false);
        }

        enemy.setX(x);
    }
}

