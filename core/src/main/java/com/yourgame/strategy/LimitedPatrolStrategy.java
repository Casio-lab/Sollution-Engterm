package com.yourgame.strategy;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class LimitedPatrolStrategy implements MovementStrategy {
    private boolean movingRight = true;
    private final float startX;
    private final float endX;
    private final float speed = 70f;

    public LimitedPatrolStrategy(float startX, float endX) {
        this.startX = startX;
        this.endX = endX;
    }

    @Override
    public void move(Sprite enemy, float delta) {
        float x = enemy.getX();

        if (movingRight) {
            x += speed * delta;
            if (x > endX) {
                x = endX;
                movingRight = false;
            }
        } else {
            x -= speed * delta;
            if (x < startX) {
                x = startX;
                movingRight = true;
            }
        }

        if ((movingRight && enemy.isFlipX()) || (!movingRight && !enemy.isFlipX())) {
            enemy.flip(true, false);
        }

        enemy.setX(x);
    }
}
