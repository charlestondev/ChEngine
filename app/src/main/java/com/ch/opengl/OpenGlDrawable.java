package com.ch.opengl;

import com.ch.chengine.MainGame;

/**
 * Created by charleston on 04/03/15.
 */
public abstract class OpenGlDrawable {
    public float x, y;
    public float width, height;
    public float getX(){return x;}
    public float getY(){return y;}
    public float[] getVertices() {
        return new float[]{
                getX(),       height+getY(), 0.0f,
                getX(),       getY(),        0.0f,
                width+getX(), getY(),        0.0f,
                width+getX(), height+getY(), 0.0f,};
    }
    public abstract float[] getUv();
    public abstract void setDimensions();
}
