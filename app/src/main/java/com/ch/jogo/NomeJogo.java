package com.ch.jogo;

import com.ch.opengl.OpenGlDrawable;

/**
 * Created by charleston on 24/02/16.
 */
public class NomeJogo extends OpenGlDrawable {

    @Override
    public float[] getUv() {
        return new float[]{
                0f,0f,
                0f,1f,
                1f,1f,
                1f,0f
        };
    }

    @Override
    public void setDimensions() {

    }
}
