    package com.yourgame.screens;

    import com.badlogic.gdx.*;
    import com.badlogic.gdx.graphics.*;
    import com.badlogic.gdx.graphics.g2d.*;
    import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
    import com.badlogic.gdx.utils.Align;
    import com.yourgame.screens.GameScreen;
    import com.yourgame.singleton.MainGame;
    import com.yourgame.screens.IntroScreen;



    public class MainMenuScreen implements Screen {

        private final MainGame game;
        private BitmapFont font;
        private OrthographicCamera camera;

        private final String[] menuItems = { "Start Game", "Exit" };
        private int selectedIndex = 0;
        public MainMenuScreen(MainGame game) {
            this.game = game;
        }

        @Override
        public void show() {
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 640, 480);

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 18;
            parameter.color = Color.WHITE;
            font = generator.generateFont(parameter);
            generator.dispose();
        }

        @Override
        public void render(float delta) {
            handleInput();

            Gdx.gl.glClearColor(0, 0, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();

            font.draw(game.batch, "JUMPING JACK", 320, 400, 0, Align.center, false);

            for (int i = 0; i < menuItems.length; i++) {
                String text = (i == selectedIndex ? "> " : "  ") + menuItems[i];
                font.draw(game.batch, text, 320, 300 - i * 40, 0, Align.center, false);
            }

            game.batch.end();
        }

        private void handleInput() {
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                selectedIndex = (selectedIndex + 1) % menuItems.length;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                selectedIndex = (selectedIndex - 1 + menuItems.length) % menuItems.length;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (selectedIndex == 0) {
                    game.setScreen(new GameScreen(game));
                } else if (selectedIndex == 1) {
                    Gdx.app.exit();
                }
            }
        }

        @Override public void resize(int width, int height) {}
        @Override public void pause() {}
        @Override public void resume() {}
        @Override public void hide() {}
        @Override public void dispose() { font.dispose(); }
    }
