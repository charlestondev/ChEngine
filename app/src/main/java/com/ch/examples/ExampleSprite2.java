package com.ch.examples;

import com.ch.chengine.Dimensions;
import com.ch.chengine.Rect;
import com.ch.chengine.Sprite;

/**
 * Created by charleston on 04/03/15.
 */
public class ExampleSprite2 extends Sprite {
    public Rect rect;
    public ExampleSprite2(){
        this.x = 0;
        this.y = 0;
        this.rect = new Rect(this, 0,0, Dimensions.block, Dimensions.block);
    }

    @Override
    public float[] getUv() {
        //temporary
        return new float[]{
                0.0f, 0.0f,
                0.0f, 0.8f,
                0.8f, 0.8f,
                0.8f, 0.0f
        };
    }

    @Override
    public void setDimensions() {
        this.width = Dimensions.block;
        this.height = Dimensions.block;
        this.x = Dimensions.block;
        this.y = Dimensions.block;
    }
}
