package com.ch.chengine;

import com.ch.opengl.OpenGlDrawable;

/**
 * Created by charleston on 04/03/15.
 */
public class Rect extends Sprite{
    public OpenGlDrawable container;
    public float relativeLeft, relativeBottom, relativeRight, relativeTop;
    public Rect(OpenGlDrawable container, float relativeLeft, float relativeBottom, float relativeRight, float relativeTop){
        this.container = container;
        this.relativeLeft = relativeLeft; this.relativeBottom = relativeBottom;this.relativeRight = relativeRight;this.relativeTop = relativeTop;
        this.width = Dimensions.block;
        this.height = Dimensions.block;
    }
    public float getX(){
        return getLeft();
    }
    public float getY(){
        return getBottom();
    }

    public float getLeft(){return container.getX()+this.relativeLeft;}
    public float getRight(){return container.getX()+this.relativeRight;}
    public float getTop(){return container.getY()+this.relativeTop;}
    public float getBottom(){return container.getY()+this.relativeBottom;}

    @Override
    public float[] getUv() {
        return new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                1.0f, 0.5f,
                1.0f, 0.0f
        };
    }

    @Override
    public void setDimensions() {
        //this.width = Dimensions.block;
        //this.height = Dimensions.block;
        //this.relativeRight = this.width; this.relativeTop = this.height;
    }
}
