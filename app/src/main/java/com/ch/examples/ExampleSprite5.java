package com.ch.examples;

import com.ch.chengine.Dimensions;
import com.ch.chengine.Sprite;

/**
 * Created by charleston on 04/03/15.
 */
public class ExampleSprite5 extends Sprite {
    public ExampleSprite5(){
        this.x = 0;
        this.y = 0;
    }

    @Override
    public float[] getUv() {
        //temporary
        return new float[]{
                0.0f, 0.0f,
                0.0f, 1.8f,
                1.8f, 1.8f,
                1.8f, 0.0f
        };
    }

    @Override
    public void setDimensions() {
        this.width = Dimensions.block;
        this.height = Dimensions.block;
        this.x = Dimensions.block*4;
        this.y = Dimensions.block*4;
    }
}
