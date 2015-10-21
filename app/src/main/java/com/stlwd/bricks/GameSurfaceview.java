package com.stlwd.bricks;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by mike.clubb on 9/14/15.
 */
public class GameSurfaceview extends GLSurfaceView{

    private BBRenderer mBBRenderer;

    // Default Constructor
    public GameSurfaceview(Context context) {

        // Call the super constructor
        super(context);

        // Set OpenGL to use GLES2.0
        setEGLContextClientVersion(2);

        mBBRenderer = new BBRenderer(context);

        // Set the Renderer to the surfaceview
        setRenderer(mBBRenderer);
    }

    public boolean onTouchEvent( final MotionEvent event) {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mBBRenderer.handleTouch(event);
            }
        });
        return true;
    }

    public void onPause() {
	    super.onPause();
    }
    public void onResume() {
	    super.onResume();
    }
}
