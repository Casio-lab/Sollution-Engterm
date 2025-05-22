package com.yourgame.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.yourgame.singleton.MainGame;
import com.yourgame.factory.EntityFactory;
import com.yourgame.factory.Platform;
import com.yourgame.observer.InputHandler;
import com.yourgame.strategy.*;
import com.yourgame.state.*;


import java.util.*;

public class GameScreen implements Screen {

    private final MainGame game;

    private OrthographicCamera camera;
    private Viewport viewport;

    private static final int VIRTUAL_WIDTH = 640;
    private static final int VIRTUAL_HEIGHT = 480;

    private Texture backgroundTexture, coinTexture, heartTexture, platformTexture;
    private Sprite player, enemy, enemy2;

    private ArrayList<Sprite> coins;
    private ArrayList<Platform> platforms;

    private BitmapFont font;
    private GlyphLayout layout;

    private float velocityY = 0f;
    private final float gravity = -900f;
    private final float jumpVelocity = 500f;

    private boolean isGameOver = false;
    private boolean isWin = false;
    private int currentLevel = 1;
    private int lives = 3;
    private int score = 0;

    private float damageCooldown = 1.5f;
    private float timeSinceLastHit = 0f;

    private float enemy2StartX;
    private MovementStrategy enemy1Strategy;
    private MovementStrategy enemy2Strategy;

    private GameStateManager stateManager;
    private InputHandler inputHandler;

    public GameScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();

        backgroundTexture = new Texture("background.png");
        coinTexture = new Texture("coin.png");
        heartTexture = new Texture("heart.png");
        platformTexture = new Texture("platform.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 22;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        generator.dispose();

        layout = new GlyphLayout();

        stateManager = new GameStateManager();
        inputHandler = new InputHandler();

        loadLevel(currentLevel);
    }

    private void loadLevel(int level) {
        player = EntityFactory.createPlayer(new Texture("player.png"));
        enemy = EntityFactory.createEnemy(new Texture("enemy.png"));
        enemy2 = EntityFactory.createEnemy(new Texture("enemy.png"));

        if (level == 1) {
            enemy2.setPosition(90, 250);
            enemy2StartX = enemy2.getX();

            coins = new ArrayList<>();
            coins.add(EntityFactory.createCoin(coinTexture, 320, 120));
            coins.add(EntityFactory.createCoin(coinTexture, 600, 170));
            coins.add(EntityFactory.createCoin(coinTexture, 120, 250));

            platforms = new ArrayList<>();
            platforms.add(new Platform(platformTexture, 300, 100));
            platforms.add(new Platform(platformTexture, 580, 150));
            platforms.add(new Platform(platformTexture, 100, 230));
        } else if (level == 2) {
            enemy2.setPosition(390, 220);
            enemy2StartX = enemy2.getX();

            coins = new ArrayList<>();
            coins.add(EntityFactory.createCoin(coinTexture, 100, 100));
            coins.add(EntityFactory.createCoin(coinTexture, 430, 220));
            coins.add(EntityFactory.createCoin(coinTexture, 600, 300));
            coins.add(EntityFactory.createCoin(coinTexture, 300, 260));

            platforms = new ArrayList<>();
            platforms.add(new Platform(platformTexture, 90, 80));
            platforms.add(new Platform(platformTexture, 400, 200));
            platforms.add(new Platform(platformTexture, 550, 280));
            platforms.add(new Platform(platformTexture, 230, 240));
            platforms.add(new Platform(platformTexture, 3, 200));

        }

        enemy1Strategy = new FullPatrolStrategy(0, VIRTUAL_WIDTH);
        enemy2Strategy = new LimitedPatrolStrategy(enemy2StartX, enemy2StartX + 90);

        isWin = false;
        isGameOver = false;
        lives = 3;
        score = 0;
        velocityY = 0;
        timeSinceLastHit = 0;
    }
    private void drawCenteredText(String text, float offsetY) {
        layout.setText(font, text);
        float x = (VIRTUAL_WIDTH - layout.width) / 2;
        float y = (VIRTUAL_HEIGHT + layout.height) / 2 + offsetY;
        font.draw(game.batch, layout, x, y);
    }

    @Override
    public void render(float delta) {
        timeSinceLastHit += delta;
        if (stateManager.isInPlay()) {
            update(delta);

            if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
                game.setScreen(new MainMenuScreen(game));
            }
        }

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        platforms.forEach(p -> p.sprite.draw(game.batch));
        coins.forEach(c -> c.draw(game.batch));
        enemy.draw(game.batch);
        enemy2.draw(game.batch);
        player.draw(game.batch);

        font.draw(game.batch, "Score: " + score, 420, 470);
        for (int i = 0; i < lives; i++) {
            game.batch.draw(heartTexture, 10 + i * 40, 440, 32, 32);
        }

        if (!stateManager.isInPlay()) {
            drawCenteredText(stateManager.getCurrentMessage(), 20);
            if (stateManager.getCurrentState() instanceof PauseState) {
                drawCenteredText(((PauseState) stateManager.getCurrentState()).getSubMessage(), -20);
            } else {
                drawCenteredText("Press R to Restart", -20);
                drawCenteredText("Press M for Main Menu", -60);
            }
        }

        game.batch.end();

        if (!stateManager.isInPlay() && Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            currentLevel = 1;
            stateManager.setState(new PlayingState());
            loadLevel(currentLevel);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            if (stateManager.isPaused()) {
                stateManager.setState(new PlayingState());
            } else if (stateManager.isInPlay()) {
                stateManager.setState(new PauseState());
            }
        }

    }

    private void update(float delta) {
        inputHandler.handleHorizontal(player, delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (player.getY() == 0 || isStandingOnPlatform()) {
                velocityY = jumpVelocity;
            }
        }

        velocityY += gravity * delta;
        float newY = player.getY() + velocityY * delta;

        for (Platform platform : platforms) {
            if (platform.isPlayerOnTop(player) && velocityY <= 0) {
                newY = platform.getTop();
                velocityY = 0;
                break;
            }
        }

        if (newY < 0) {
            newY = 0;
            velocityY = 0;
        }

        player.setY(newY);

        enemy1Strategy.move(enemy, delta);
        enemy2Strategy.move(enemy2, delta);

        if (player.getBoundingRectangle().overlaps(enemy.getBoundingRectangle())
            || player.getBoundingRectangle().overlaps(enemy2.getBoundingRectangle())) {
            if (timeSinceLastHit > damageCooldown) {
                lives--;
                timeSinceLastHit = 0f;
                if (lives <= 0) {
                    stateManager.setState(new GameOverState());
                }
            }
        }

        coins.removeIf(c -> {
            if (player.getBoundingRectangle().overlaps(c.getBoundingRectangle())) {
                score++;
                return true;
            }
            return false;
        });

        if (coins.isEmpty()) {
            if (currentLevel == 1) {
                game.setScreen(new LevelTransitionScreen(game, 2));
            }
            else {
                stateManager.setState(new WinState());
            }
        }
    }
    private boolean isStandingOnPlatform() {
        for (Platform platform : platforms) {
            if (platform.isPlayerOnTop(player)) return true;
        }
        return false;
    }
    public void setLevel(int level) {
        this.currentLevel = level;
        loadLevel(level);
    }
    public GameScreen(MainGame game, int level) {
        this.game = game;
        this.currentLevel = level;
    }


    @Override public void resize(int width, int height) { viewport.update(width, height); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

}
