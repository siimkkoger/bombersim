package com.example.gameserver;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class GameSocketHandler extends TextWebSocketHandler {
    
    private final GameStateManager gameStateManager;

    public GameSocketHandler(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Handle incoming messages from clients (e.g., player moves, bomb placements)
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Send the initial game state to the newly connected client
    }
}
