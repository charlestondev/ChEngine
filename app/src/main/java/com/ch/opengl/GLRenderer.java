package com.ch.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.ch.chengine.Dimensions;
import com.ch.chengine.MainGame;
import com.ch.chengine.DrawableGroup;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/*
    this class draw what we want controlled by a internal main loop
*/
public class GLRenderer implements Renderer, DrawableGroup.GroupChangedListener {

	// Our matrices
	private final float[] mtrxProjection = new float[16];
	private final float[] mtrxView = new float[16];
	private final float[] mtrxProjectionAndView = new float[16];
	
	// Geometric variables

    //
    public static float objsVertices[][];
    public static short objsIndices[][];
    public static float objsUvs[][];
    public FloatBuffer objsVertexBuffer[];
    public ShortBuffer objsDrawListBuffer[];
    public FloatBuffer objsUvBuffer[];
    
    //public short indices[][];

	// Our screenresolution
	float	mScreenWidth = 1280;
	float	mScreenHeight = 768;

	// Misc
	Context mContext;
	long mLastTime;
	int mProgram;

    //
    MainGame mainGame;

	public GLRenderer(Context c)
	{
		mContext = c;
		mLastTime = System.currentTimeMillis() + 100;
	}
	
	public void onPause()
	{
		/* Do stuff to pause the renderer */
	}
	
	public void onResume()
	{
		/* Do stuff to resume the renderer */
		mLastTime = System.currentTimeMillis();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if(mainGame==null) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1);

            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

            int vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER, riGraphicTools.vs_Image);
            int fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_Image);

            riGraphicTools.sp_Image = GLES20.glCreateProgram();
            GLES20.glAttachShader(riGraphicTools.sp_Image, vertexShader);
            GLES20.glAttachShader(riGraphicTools.sp_Image, fragmentShader);
            GLES20.glLinkProgram(riGraphicTools.sp_Image);

            GLES20.glUseProgram(riGraphicTools.sp_Image);
        }
        else{
            Log.d("Render onSurfaceCreated", "mainGame already exist");
        }

	}

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        mScreenWidth = width;
        mScreenHeight = height;
        Dimensions.setUnits(width, height);

        mainGame = new MainGame();
        int groupCount = mainGame.groups.length;

        objsVertices = new float[groupCount][];
        objsIndices = new short[groupCount][];
        objsUvs = new float[groupCount][];
        objsVertexBuffer = new FloatBuffer[groupCount];
        objsDrawListBuffer = new ShortBuffer[groupCount];
        objsUvBuffer = new FloatBuffer[groupCount];


        //mainGame.adjustSizes();
        for (int i = 0; i < mainGame.groups.length; i++) {
            setupTriangle(i,mainGame.groups[i].drawables.size());
            mainGame.groups[i].addListener(this);
            Log.d("renderer", "listener added");
        }
        setupImage(mainGame.groups.length,mainGame.groups);
        GLES20.glViewport(0, 0, (int)mScreenWidth, (int)mScreenHeight);
        for(int i=0;i<16;i++)
        {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        Matrix.orthoM(mtrxProjection, 0, 0f, mScreenWidth, 0.0f, mScreenHeight, 0, 50);
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);


    }
	
	public void setupImage(int imgCount, DrawableGroup[] groups)
	{

		// Generate Textures, if more needed, alter these numbers.
		int[] texturenames = new int[imgCount];
		GLES20.glGenTextures(imgCount, texturenames, 0);
        Bitmap bmp;
        //------------------------------------------------------------
        for (int i = 0; i < imgCount; i++) {
            int id = groups[i].imgAtlas;

            bmp = BitmapFactory.decodeResource(mContext.getResources(), id);

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0+i);

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[i]);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

            bmp.recycle();
        }

	}
	public short[] createIndices(int countObjs){
        short[] indices = new short[countObjs*6];
        short jump = 0;
        for(int i=0; i < countObjs*6; i+=6){
            indices[i+0] = (short)(0+jump);
            indices[i+1] = (short)(1+jump);
            indices[i+2] = (short)(2+jump);
            indices[i+3] = (short)(0+jump);
            indices[i+4] = (short)(2+jump);
            indices[i+5] = (short)(3+jump);
            jump+=4;
        }
        return indices;
    }
	

    public void prepareUVVertices(int group, int countObjs){

        objsUvs[group] = new float[countObjs*8];

        for(int i=0;i<countObjs*8;i+=8){
            objsUvs[group][i] = mainGame.groups[group].drawables.get(i/8).getUv()[0];
            objsUvs[group][i+1] = mainGame.groups[group].drawables.get(i/8).getUv()[1];
            objsUvs[group][i+2] = mainGame.groups[group].drawables.get(i/8).getUv()[2];
            objsUvs[group][i+3] = mainGame.groups[group].drawables.get(i/8).getUv()[3];
            objsUvs[group][i+4] = mainGame.groups[group].drawables.get(i/8).getUv()[4];
            objsUvs[group][i+5] = mainGame.groups[group].drawables.get(i/8).getUv()[5];
            objsUvs[group][i+6] = mainGame.groups[group].drawables.get(i/8).getUv()[6];
            objsUvs[group][i+7] = mainGame.groups[group].drawables.get(i/8).getUv()[7];
        }
        ByteBuffer bb = ByteBuffer.allocateDirect(objsUvs[group].length * 4);
        bb.order(ByteOrder.nativeOrder());
        objsUvBuffer[group] = bb.asFloatBuffer();
        objsUvBuffer[group].put(objsUvs[group]);
        objsUvBuffer[group].position(0);

        objsVertices[group] = new float[countObjs*12];
        for(int i = 0; i < countObjs*12;i+=12){
            objsVertices[group][i] = mainGame.groups[group].drawables.get(i/12).getVertices()[0];
            objsVertices[group][i+1] = mainGame.groups[group].drawables.get(i/12).getVertices()[1];
            objsVertices[group][i+2] = mainGame.groups[group].drawables.get(i/12).getVertices()[2];
            objsVertices[group][i+3] = mainGame.groups[group].drawables.get(i/12).getVertices()[3];
            objsVertices[group][i+4] = mainGame.groups[group].drawables.get(i/12).getVertices()[4];
            objsVertices[group][i+5] = mainGame.groups[group].drawables.get(i/12).getVertices()[5];
            objsVertices[group][i+6] = mainGame.groups[group].drawables.get(i/12).getVertices()[6];
            objsVertices[group][i+7] = mainGame.groups[group].drawables.get(i/12).getVertices()[7];
            objsVertices[group][i+8] = mainGame.groups[group].drawables.get(i/12).getVertices()[8];
            objsVertices[group][i+9] = mainGame.groups[group].drawables.get(i/12).getVertices()[9];
            objsVertices[group][i+10] = mainGame.groups[group].drawables.get(i/12).getVertices()[10];
            objsVertices[group][i+11] = mainGame.groups[group].drawables.get(i/12).getVertices()[11];
        }
        bb = ByteBuffer.allocateDirect(objsVertices[group].length * 4);
        bb.order(ByteOrder.nativeOrder());
        objsVertexBuffer[group] = bb.asFloatBuffer();
        objsVertexBuffer[group].put(objsVertices[group]);
        objsVertexBuffer[group].position(0);
    }
    public void setupTriangle(int group, int countObjs)
    {
        prepareUVVertices(group, countObjs);
        objsIndices[group] = createIndices(countObjs);
        ByteBuffer dlb = ByteBuffer.allocateDirect(objsIndices[group].length * 2);
        dlb.order(ByteOrder.nativeOrder());
        objsDrawListBuffer[group] = dlb.asShortBuffer();
        objsDrawListBuffer[group].put(objsIndices[group]);
        objsDrawListBuffer[group].position(0);
    }
    
    
    public void TranslateSprite()
    {
        /*vertices = new float[]
                {
                        image.left, image.top, 0.0f,
                        image.left, image.bottom, 0.0f,
                        image.right, image.bottom, 0.0f,
                        image.right, image.top, 0.0f,
                };
        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        */
    }
    @Override
    public void onDrawFrame(GL10 unused) {

        long now = System.currentTimeMillis();

        if (mLastTime > now) return;

        long elapsed = now - mLastTime;


        mainGame.beforeRender();

        for (int i = 0; i < mainGame.groups.length; i++) {
            prepareUVVertices(i, mainGame.groups[i].drawables.size());
        }

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        for (int i = 0; i < mainGame.groups.length; i++) {
            renderObjects(mtrxProjectionAndView, objsVertexBuffer[i], objsUvBuffer[i],i, objsIndices[i], objsDrawListBuffer[i]);
        }

        mLastTime = now;
    }
    public void renderObjects(float[] m, FloatBuffer vertexBuffer,FloatBuffer uvBuffer, int textureUnit, short[] indices, ShortBuffer drawListBuffer){
        //GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        int mPositionHandle = GLES20.glGetAttribLocation(riGraphicTools.sp_Image, "vPosition");

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        int mTexCoordLoc = GLES20.glGetAttribLocation(riGraphicTools.sp_Image, "a_texCoord" );

        GLES20.glEnableVertexAttribArray ( mTexCoordLoc );

        GLES20.glVertexAttribPointer ( mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);

        int mtrxhandle = GLES20.glGetUniformLocation(riGraphicTools.sp_Image, "uMVPMatrix");

        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);

        int mSamplerLoc = GLES20.glGetUniformLocation (riGraphicTools.sp_Image, "s_texture" );

        // Set the sampler texture unit to 0, where we have saved the texture.
        GLES20.glUniform1i ( mSamplerLoc, textureUnit);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        //GLES20.glDrawElements(GLES20.GL_TRIANGLES, bgIndices.length, GLES20.GL_UNSIGNED_SHORT, bgDrawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);
    }
    public void processTouchEvent(MotionEvent event){
        mainGame.onTouch(event);
    }
    public void onSaveInstanceState(Bundle bundle){
        mainGame.onSaveInstanceState(bundle);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //on restore is called before onSurfaceCreated, thus mainGame will be null
        if(mainGame==null)
            mainGame = new MainGame();
        mainGame.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void spriteAdded() {
        for (int i = 0; i < mainGame.groups.length; i++) {
            setupTriangle(i,mainGame.groups[i].drawables.size());
        }
        Log.d("Group", "Sprite added");
    }

    @Override
    public void spriteRemoved() {
        for (int i = 0; i < mainGame.groups.length; i++) {
            setupTriangle(i,mainGame.groups[i].drawables.size());
        }
    }
}
