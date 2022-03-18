package com.ch.chengine;

import com.ch.opengl.OpenGlDrawable;

/**
 * Created by charleston on 03/03/15.
 */
public abstract class Sprite extends OpenGlDrawable{

    public float[] getVertices() {
        return new float[]{
                getX()-MainGame.cameraX,       height+getY()-MainGame.cameraY, 0.0f,
                getX()-MainGame.cameraX,       getY()-MainGame.cameraY,        0.0f,
                width+getX()-MainGame.cameraX, getY()-MainGame.cameraY,        0.0f,
                width+getX()-MainGame.cameraX, height+getY()-MainGame.cameraY, 0.0f,};
    }

    @Override
    abstract public float[] getUv();
}
