package com.example.muscleup.model.data;

public class PoseItem {

    public static final int ITEM_POSE_IMAGE = 0;
    public static final int ITEM_UPLOAD = 1;

    private int viewType;
    private byte[] image;

    public PoseItem(int viewType, byte[] image) {
        this.viewType = viewType;
        this.image = image;
    }

    public int getViewType() {
        return viewType;
    }

    public byte[] getImage() {
        return image;
    }
}
