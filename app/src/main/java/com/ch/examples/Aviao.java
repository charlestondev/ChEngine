package com.ch.examples;

import com.ch.chengine.Circle;
import com.ch.chengine.Collidable;
import com.ch.chengine.Dimensions;
import com.ch.chengine.Rect;
import com.ch.chengine.Sprite;

/**
 * Created by charleston on 04/03/15.
 */
public class Aviao extends Sprite implements Collidable{
    public Rect rect;
    public float[] imagem;
    public float[] imagemNormal = new float[]{
            0.008f, 0.11f,
            0.008f, 0.183f,
            0.21f, 0.183f,
            0.21f, 0.11f
    };
    public float[] imagemDestruido = new float[]{
            0.008f, 0.11f,
            0.008f, 0.183f,
            0.21f, 0.183f,
            0.21f, 0.11f
    };

    public float aceleracaoY = 0;
    public Aviao(){
        this.width = Dimensions.block;
        this.height = Dimensions.block/2;
        this.x = 200; this.y = 200;
        rect = new Rect(this, 0,0,width,height);
        imagem = imagemNormal;
    }

    @Override
    public float[] getUv() {
        //temporary
        return imagem;
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
    public void bateu(){
        imagem = imagemDestruido;
    }
}
