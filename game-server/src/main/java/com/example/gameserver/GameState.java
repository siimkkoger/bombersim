package com.example.gameserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameState {
    private ConcurrentMap<String, Player> players = new ConcurrentHashMap<>();
    private List<Bomb> bombs = Collections.synchronizedList(new ArrayList<>());

    // Getters and setters
}
