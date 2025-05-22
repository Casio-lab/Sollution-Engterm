package com.yourgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;
import com.yourgame.singleton.MainGame;

public class IntroScreen implements Screen {

    private final MainGame game;
    private OrthographicCamera camera;
    private BitmapFont font;
    private GlyphLayout layout;

    public IntroScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.color = Color.GOLD;
        font = generator.generateFont(parameter);
        generator.dispose();

        layout = new GlyphLayout();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen(game));
            }
        }, 2.5f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        layout.setText(font, "JUMPING JACK");
        font.draw(game.batch, layout, (640 - layout.width) / 2, 260);

        layout.setText(font, "Loading...");
        font.draw(game.batch, layout, (640 - layout.width) / 2, 200);

        game.batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        font.dispose();
    }
}
