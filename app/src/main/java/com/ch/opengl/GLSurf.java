package com.ch.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
/*
    this class prepare the renderer that has some internal methods that implement the game main loop
 */
public class GLSurf extends GLSurfaceView {

	public final GLRenderer mRenderer;
	
	public GLSurf(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new GLRenderer(context);
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

	@Override
	public void onPause() {
		super.onPause();
		mRenderer.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mRenderer.onResume();
	}
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mRenderer.processTouchEvent(e);
        return true;
    }
}
