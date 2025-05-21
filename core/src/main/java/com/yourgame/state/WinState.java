package com.yourgame.state;

public class WinState implements GameState {

    @Override
    public void update(float delta) {}

    @Override
    public void render() {}

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public String getMessage() {
        return "YOU WIN ALL LEVELS";
    }
}
