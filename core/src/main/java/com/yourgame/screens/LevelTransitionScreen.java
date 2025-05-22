package com.yourgame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;
import com.yourgame.singleton.MainGame;

public class LevelTransitionScreen implements Screen {

    private final MainGame game;
    private final int nextLevel;

    private OrthographicCamera camera;
    private BitmapFont font;
    private GlyphLayout layout;

    public LevelTransitionScreen(MainGame game, int nextLevel) {
        this.game = game;
        this.nextLevel = nextLevel;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.CYAN;
        font = generator.generateFont(parameter);
        generator.dispose();

        layout = new GlyphLayout();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new GameScreen(game, nextLevel));
            }
        }, 1.5f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        String text = "Level " + nextLevel + " Starting...";
        layout.setText(font, text);
        font.draw(game.batch, layout, (640 - layout.width) / 2, 240);

        game.batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { font.dispose(); }
}
