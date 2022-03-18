package com.ch.chengine;

import android.util.Log;

import com.ch.opengl.OpenGlDrawable;

/**
 * Created by charleston on 28/06/15.
 */
public class Button extends OpenGlDrawable implements Collidable{
    public Rect rect;
    public Button(){
        rect = new Rect(this,0,0, Dimensions.block,Dimensions.block);
    }
    @Override
    public float[] getUv() {
        return new float[]{
                0.0f, 0.0f,
                0.0f, 0.12f,
                0.12f, 0.12f,
                0.12f, 0.0f
        };
    }

    @Override
    public void setDimensions() {
        this.width = Dimensions.block;
        this.height = Dimensions.block;
        rect = new Rect(this,0,0, Dimensions.block,Dimensions.block);
    }

    @Override
    public Rect getCollisionRect() {
        return this.rect;
    }

    @Override
    public Circle getCollisionCircle() {
        return null;
    }

    @Override
    public void collided() {
        Log.d("teste", "button pressed");
    }

}
