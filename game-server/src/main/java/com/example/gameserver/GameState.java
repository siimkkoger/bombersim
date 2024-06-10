package com.example.gameserver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
@Setter
@Component
public class GameState {

    private ConcurrentMap<UUID, Player> players = new ConcurrentHashMap<>();
    private List<Bomb> bombs = Collections.synchronizedList(new ArrayList<>());

    // Methods to manage game state
    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }

    public void removePlayer(UUID playerId) {
        players.remove(playerId);
    }

    public Player getPlayer(UUID playerId) {
        return players.get(playerId);
    }

}
