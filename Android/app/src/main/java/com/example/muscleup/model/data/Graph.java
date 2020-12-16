package com.example.muscleup.model.data;

import com.github.mikephil.charting.data.Entry;

public class Graph extends Entry {

    int id;
    float value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
