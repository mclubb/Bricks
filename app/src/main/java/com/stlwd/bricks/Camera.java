package com.stlwd.bricks;

import android.opengl.Matrix;

/**
 * Created by mike.clubb on 9/15/15.
 */
public class Camera {

    public float eyeX;
    public float eyeY;
    public float eyeZ;
    public float atX;
    public float atY;
    public float atZ;
    public float upX;
    public float upY;
    public float upZ;

    private float[] mViewMatrix;


    boolean isShaking = false;
    int ShakingFrameId = 0;
    float[] ShakingFrames = {0, 0.1f, 0.3f, 0.5f, 07f, 0.5f, 0.3f, 0.1f, 0.0f, -0.3f, -0.5f, -0.7f, -0.5f, -0.3f, -0.1f, -0.1f, 0};


    public Camera(float x, float y, float z, float lx, float ly, float lz, float ux, float uy, float uz)
    {
        this.eyeX = x;
        this.eyeY = y;
        this.eyeZ = z;
        this.atX = lx;
        this.atY = ly;
        this.atZ = lz;
        this.upX = ux;
        this.upY = uy;
        this.upZ = uz;

        mViewMatrix = new float[16];

        setLookAt();
    }

    public void Update() {
        /*if( isShaking ) {
            this.atX = ShakingFrames[ShakingFrameId];
            ShakingFrameId++;
            setLookAt();
        }

        if( ShakingFrameId >= ShakingFrames.length ){
            ShakingFrameId = 0;
            isShaking = false;
        }*/
    }

    public void setLookAt() {
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, atX, atY, atZ, upX, upY, upZ);
    }

    public float[] getCameraViewMatrix() {
        return mViewMatrix;
    }
}
