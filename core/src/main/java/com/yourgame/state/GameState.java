package com.yourgame.state;

public interface GameState {
    void update(float delta);
    void render();
    boolean isFinished();
    String getMessage();
}
