package com.yourgame.strategy;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface MovementStrategy {
    void move(Sprite enemy, float delta);
}
