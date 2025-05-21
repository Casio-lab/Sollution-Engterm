package com.yourgame.state;

public class PlayingState implements GameState {

    private boolean finished = false;

    @Override
    public void update(float delta) {
        // Логика игры обрабатывается в GameScreen
    }

    @Override
    public void render() {}

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public String getMessage() {
        return "";
    }

    public void markFinished() {
        this.finished = true;
    }
}
