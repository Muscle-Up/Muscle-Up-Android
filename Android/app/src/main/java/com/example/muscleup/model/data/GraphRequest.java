package com.example.muscleup.model.data;

public class GraphRequest {
    private float weight;
    private float muscleMass;
    private float bodyFatMass;

    public GraphRequest(float weight, float muscleMass, float bodyFatMass) {
        this.weight = weight;
        this.muscleMass = muscleMass;
        this.bodyFatMass = bodyFatMass;
    }

    public float getWeight() {
        return weight;
    }

    public float getMuscleMass() {
        return muscleMass;
    }

    public float getBodyFatMass() {
        return bodyFatMass;
    }
}
