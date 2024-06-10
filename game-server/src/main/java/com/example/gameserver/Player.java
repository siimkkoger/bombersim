package com.example.gameserver;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Player {

    private UUID id;
    private String name;
    private int x;
    private int y;
    private int score;

    public Player(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.x = 0;
        this.y = 0;
        this.score = 0;
    }

    public void setPosition(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void increaseScore() {
        score++;
    }

    public void decreaseScore() {
        score--;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
