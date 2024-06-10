package com.example.gameserver;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameStateManager {
    private final GameState gameState;

    public GameStateManager(GameState gameState) {
        this.gameState = gameState;
    }

    public void playerJoin(UUID id, String playerName) {
        Player player = new Player(id, playerName);
        gameState.addPlayer(player);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void playerLeave(UUID playerId) {
        gameState.removePlayer(playerId);
    }

    public Player getPlayer(UUID playerId) {
        return gameState.getPlayer(playerId);
    }
}