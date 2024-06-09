package com.example.gameserver;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GameLoop {

    private final GameStateManager gameStateManager;
    private final SimpMessagingTemplate messagingTemplate;

    public GameLoop(GameStateManager gameStateManager, SimpMessagingTemplate messagingTemplate) {
        this.gameStateManager = gameStateManager;
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 50) // Update every 50 ms
    public void updateGameState() {
        GameState gameState = gameStateManager.getGameState();
        // Update game state logic here
        
        // Notify all clients about the updated game state
        messagingTemplate.convertAndSend("/topic/game-state", gameState);
    }
}
