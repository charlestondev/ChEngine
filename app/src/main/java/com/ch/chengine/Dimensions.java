package com.ch.chengine;

/**
 * Created by charleston on 05/03/15.
 */
public class Dimensions {
    public static float block;
    public static float screenHeight;
    public static float screenWidth;
    public static float unit;
    public static void setUnits(float screenWidth, float screenHeight){
        Dimensions.block = screenHeight/6;
        Dimensions.screenHeight = screenHeight;
        Dimensions.screenWidth = screenWidth;
        Dimensions.unit = screenHeight/700;
    }
}
