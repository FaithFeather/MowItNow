package com.mowitnow.util;

public enum OrientationEnum {
    N ("North"),
    S ("South"),
    E ("East"),
    W ("West");

    String orientation;

    OrientationEnum(String orientation) {
        this.orientation = orientation;
    }

    public String getOrientation() {
        return this.orientation;
    }
}
