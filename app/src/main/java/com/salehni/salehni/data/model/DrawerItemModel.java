package com.salehni.salehni.data.model;

public class DrawerItemModel {

    private String titles;
    private int icons;

    public String getDrawerText() {
        return titles;
    }

    public void setDrawerText(String titles) {
        this.titles = titles;
    }

    public int getDrawerIcon() {
        return icons;
    }

    public void setDrawerIcon(int icons) {
        this.icons = icons;
    }
}
