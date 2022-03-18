package com.ch.chengine;

import com.ch.opengl.OpenGlDrawable;

/**
 * Created by charleston on 02/03/15.
 */
public class Circle extends OpenGlDrawable{
    public float radius;
    public float relativeX, relativeY;
    public OpenGlDrawable container;
    public Circle(OpenGlDrawable container, float relativeX, float relativeY){
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.container = container;
    }
    public float getX(){
        return container.getX()+this.relativeX;
    }
    public float getY(){
        return container.getY()+this.relativeY;
    }

    @Override
    public float[] getUv() {
        return new float[]{
                0.0f, 0.5f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.5f
        };
    }

    @Override
    public void setDimensions() {
        this.width = Dimensions.block/2;
        this.height = Dimensions.block/2;
        this.radius = this.height/2;
    }
}
