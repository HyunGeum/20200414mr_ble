package com.nordicsemi.nrfUARTv2;

public class product {
    int image;
    String name, color;

    public product(int image, String name, String color) {
        this.image = image;
        this.name = name;
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }


}
