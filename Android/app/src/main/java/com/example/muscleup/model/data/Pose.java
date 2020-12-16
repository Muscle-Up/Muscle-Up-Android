package com.example.muscleup.model.data;

public class Pose {
    private float area;
    private float[] bbox;
    private int category_id;
    private float[] keypoints;
    private float score;

    public float getArea() {
        return area;
    }

    public float[] getBbox() {
        return bbox;
    }

    public int getCategory_id() {
        return category_id;
    }

    public float[] getKeypoints() {
        return keypoints;
    }

    public float getScore() {
        return score;
    }
}
