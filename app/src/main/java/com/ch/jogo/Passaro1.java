package com.ch.jogo;

import com.ch.chengine.Circle;
import com.ch.chengine.Collidable;
import com.ch.chengine.Dimensions;
import com.ch.chengine.Rect;
import com.ch.chengine.Sprite;

/**
 * Created by charleston on 15/07/15.
 */
public class Passaro1 extends Sprite implements Collidable{
    public Rect rect;
    public Passaro1(){
        this.width = Dimensions.block;
        this.height = Dimensions.block;
        this.x = 600;
        this.y = 0;
        rect = new Rect(this, 0,0,width,height);
    }
    @Override
    public float[] getUv() {
        return new float[]{
                //x    y
                0.2f, 0.1f, //ponto superior esquerdo
                0.2f, 0.2f, //ponto inferior esquerdo
                0.4f, 0.2f, //ponto inferior direito
                0.4f, 0.1f  //ponto superior direito
        };
    }

    @Override
    public void setDimensions() {

    }

    @Override
    public Rect getCollisionRect() {
        return rect;
    }

    @Override
    public Circle getCollisionCircle() {
        return null;
    }

    @Override
    public void collided() {

    }
}
