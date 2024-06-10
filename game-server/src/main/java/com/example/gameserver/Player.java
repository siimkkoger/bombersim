package com.example.gameserver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Player {

    private String name;
    private int x;
    private int y;
    private int score;

    public void move(int dx, int dy) {
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
