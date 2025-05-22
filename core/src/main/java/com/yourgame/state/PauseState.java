package com.yourgame.state;

public class PauseState implements GameState {

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public String getMessage() {
        return "PAUSED";
    }

    public String getSubMessage() {
        return "Press P to Resume";
    }
}

