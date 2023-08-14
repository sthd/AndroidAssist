package com.er.aa.menu;

public class Item {
    private int imageResId;
    private String title;

    public Item(int imageResId, String title) {
        this.imageResId = imageResId;
        this.title = title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }
}
