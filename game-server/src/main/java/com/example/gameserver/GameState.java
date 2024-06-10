package com.example.gameserver;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
@Setter
public class GameState {
    private ConcurrentMap<String, Player> players = new ConcurrentHashMap<>();
    private List<Bomb> bombs = Collections.synchronizedList(new ArrayList<Bomb>());

    // Getters and setters
}
