package com.example.gameserver;

import java.util.UUID;

public class ActionMessage {
    private String type;
    private UUID playerId;
    private MoveData moveData;

    // Getters and setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public MoveData getMoveData() {
        return moveData;
    }

    public void setMoveData(MoveData moveData) {
        this.moveData = moveData;
    }
}