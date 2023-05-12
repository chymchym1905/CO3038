package com.example.mobileiotapp;

import java.util.Arrays;

public class Data {
    private int temp[];
    private int light[];
    private int humid[];

    public Data(int[] temp, int[] light, int[] humid) {
        this.temp = temp;
        this.light = light;
        this.humid = humid;
    }

    public int[] getTemp() {
        return temp;
    }

    public void setTemp(int[] temp) {
        this.temp = temp;
    }

    public int[] getLight() {
        return light;
    }

    public void setLight(int[] light) {
        this.light = light;
    }

    public int[] getHumid() {
        return humid;
    }

    public void setHumid(int[] humid) {
        this.humid = humid;
    }

    public int[] appendToArray(int[] array, int element) {
        int[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[newArray.length - 1] = element;
        return newArray;
    }
}
