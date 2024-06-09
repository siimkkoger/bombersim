package com.example.gameserver;

import org.springframework.stereotype.Component;

@Component
public class GameStateManager {
    private final GameState gameState = new GameState();

    public GameState getGameState() {
        return gameState;
    }

    // Methods to update game state safely
}