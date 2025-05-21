package com.yourgame.observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class InputHandler {

    public void handleHorizontal(Sprite player, float delta) {
        float speed = 200f;
        float newX = player.getX();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            newX -= speed * delta;
            if (!player.isFlipX()) player.flip(true, false); // Повернуть влево
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += speed * delta;
            if (player.isFlipX()) player.flip(true, false); // Повернуть вправо
        }

        newX = Math.max(0, Math.min(newX, 640 - player.getWidth())); // Границы экрана
        player.setX(newX);
    }
}
