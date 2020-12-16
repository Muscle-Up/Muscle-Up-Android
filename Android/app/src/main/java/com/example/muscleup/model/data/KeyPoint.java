package com.example.muscleup.model.data;

public class KeyPoint {

    float x;
    float y;
    float score;

    public KeyPoint(float x, float y, float score) {
        this.x = x;
        this.y = y;
        this.score = score;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getScore() {
        return score;
    }
}
