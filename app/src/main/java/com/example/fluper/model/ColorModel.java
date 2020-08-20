package com.example.fluper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ColorModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("color")
    @Expose
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
