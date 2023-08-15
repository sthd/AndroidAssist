package com.er.aa.menu;

public class Item {
    private int imageResId;
    private String title;
    private Class<?> targetActivity;

    // Constructor with target activity
    public Item(int imageResId, String title, Class<?> targetActivity) {
        this.imageResId = imageResId;
        this.title = title;
        this.targetActivity = targetActivity;
    }

    // Constructor without target activity
    public Item(int imageResId, String title) {
        this.imageResId = imageResId;
        this.title = title;
        this.targetActivity = null;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public Class<?> getTargetActivity() {
        return targetActivity;
    }
}
