package com.ch.jogo;

import com.ch.chengine.Dimensions;
import com.ch.chengine.Sprite;

/**
 * Created by charleston on 25/03/15.
 */
public class Background1 extends Sprite {
    public Background1(){
        this.x = 0; this.y = 0;
        setDimensions();
    }
    @Override
    public void setDimensions(){
        this.width = (int) Dimensions.screenWidth;this.height = (int)Dimensions.screenHeight;
    }

    @Override
    public float[] getUv() {
        return new float[]{
                //x  e y
                0.0f, 0.0f,//ponto superior esquerdo
                0.0f, 0.20f,//ponto inferior esquerdo
                0.95f, 0.20f,//ponto inferior direito
                0.95f, 0.0f//ponto supeior direito
        };
    }
}
