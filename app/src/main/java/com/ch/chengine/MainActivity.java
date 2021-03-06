package com.ch.chengine;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ch.opengl.GLSurf;

/*
* this class is used to create a screen with a surface to draw
* */
public class MainActivity extends Activity {
    // Our OpenGL Surfaceview
    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "Rendering images, handling inputs");
        // Turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Super
        super.onCreate(savedInstanceState);

        // Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // We create our Surfaceview for our OpenGL here.
        glSurfaceView = new GLSurf(this);

        // Set our view.
        setContentView(R.layout.activity_main);
        //setContentView(glSurfaceView);

        // Retrieve our Relative layout from our main layout we just set to our view.
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.gamelayout);

        // Attach our surfaceview to our relative layout from our main layout.
        RelativeLayout.LayoutParams glParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.addView(glSurfaceView, glParams);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //glSurfaceView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((GLSurf)glSurfaceView).mRenderer.onSaveInstanceState(outState);
        Log.d("MainActivity", "saving state");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ((GLSurf)glSurfaceView).mRenderer.onRestoreInstanceState(savedInstanceState);
        Log.d("MainActivity", "restoring state");
    }
}
