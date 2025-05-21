package com.yourgame.state;

public class GameStateManager {

    private GameState currentState;

    public GameStateManager() {
        currentState = new PlayingState();
    }

    public void setState(GameState state) {
        currentState = state;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void update(float delta) {
        currentState.update(delta);
    }

    public void render() {
        currentState.render();
    }

    public boolean isInPlay() {
        return currentState instanceof PlayingState;
    }

    public String getCurrentMessage() {
        return currentState.getMessage();
    }
    public boolean isPaused() {
        return currentState instanceof PauseState;
    }

}
