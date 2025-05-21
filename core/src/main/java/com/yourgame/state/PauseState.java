package com.yourgame.state;

public class PauseState implements GameState {

    @Override
    public void update(float delta) {
        // В паузе ничего не происходит
    }

    @Override
    public void render() {
        // Отрисовка уже в GameScreen
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public String getMessage() {
        return "PAUSED\nPress P to Resume";
    }
}
