package com.example.gameserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameSocketHandler extends TextWebSocketHandler {

    static Logger logger = LoggerFactory.getLogger(GameSocketHandler.class);

    private final Map<WebSocketSession, UUID> sessionToPlayerMap = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final GameStateManager gameStateManager;
    private final SimpMessagingTemplate messagingTemplate;

    public GameSocketHandler(GameStateManager gameStateManager, SimpMessagingTemplate messagingTemplate) {
        this.gameStateManager = gameStateManager;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        UUID playerId = sessionToPlayerMap.get(session);

        if (playerId == null) {
            // Player is not connected, ignore message
            logger.warn("Received message from unregistered player");
            return;
        }

        // Parse the message to determine action (e.g., player move, bomb placement)
        var actionMessage = objectMapper.readValue(message.getPayload(), ActionMessage.class);
        handleActionMessage(playerId, actionMessage);

        // Broadcast the updated game state to all clients
        messagingTemplate.convertAndSend("/topic/game-state", gameStateManager.getGameState());
        /*
        This might be faster if I have latency problems
        for (WebSocketSession ws : sessionToPlayerMap.keySet()) {
            ws.sendMessage(new TextMessage(objectMapper.writeValueAsString(gameStateManager.getGameState())));
        }
         */
    }

    private void handleActionMessage(UUID playerId, ActionMessage actionMessage) {
        switch (actionMessage.getType()) {
            case "MOVE":
                handleMove(playerId, actionMessage.getMoveData());
                break;
            case "PLACE_BOMB":
                handlePlaceBomb(playerId);
                break;
            // Add more cases as needed
        }
    }

    private void handleMove(UUID playerId, MoveData moveData) {
        Player player = gameStateManager.getGameState().getPlayer(playerId);
        if (player != null) {
            player.setPosition(moveData.newPosition().x(), moveData.newPosition().y());
        }
    }

    private void handlePlaceBomb(UUID playerId) {
        Player player = gameStateManager.getGameState().getPlayer(playerId);
        if (player != null) {
            Bomb bomb = new Bomb(player.getPosition());
            gameStateManager.getGameState().getBombs().add(bomb);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extract player name from session or handshake attributes
        // For demonstration, using a hardcoded player name
        String playerName = "playerNameFromHandshake"; // Replace with actual extraction logic
        UUID playerId = UUID.randomUUID();
        Player player = new Player(playerId, playerName);

        // Add player to the game state
        gameStateManager.getGameState().addPlayer(player);

        // Map session to player ID
        sessionToPlayerMap.put(session, playerId);

        // Send the initial game state to the newly connected client
        session.sendMessage(new TextMessage("Welcome " + playerName));
        // Optionally, send the current game state to the newly connected player
        session.sendMessage(new TextMessage("Initial game state: " + gameStateManager.getGameState().toString()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Handle player disconnection
        UUID playerId = sessionToPlayerMap.remove(session);

        if (playerId != null) {
            gameStateManager.getGameState().removePlayer(playerId);
            // Optionally, broadcast the updated game state to all clients
            // messagingTemplate.convertAndSend("/topic/game-state", gameStateManager.getGameState());
        }
    }
}

