package com.yourgame.singleton;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yourgame.screens.MainMenuScreen;
import com.yourgame.screens.GameScreen;
import com.yourgame.screens.IntroScreen;

public class MainGame extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


}
