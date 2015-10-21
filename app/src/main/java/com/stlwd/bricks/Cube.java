package com.stlwd.bricks;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by mike.clubb on 10/1/15.
 */
public class Cube {
    int mProgram;
    FloatBuffer mVertices;
    ShortBuffer mIndices;
    float rotationX, rotationY, rotationZ;

    float x, y, z, width, height, depth, rotx, roty, rotz;

    public Cube(float x, float y, float z, float w, float h, float d, float rx, float ry, float rz) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.width = w;
        this.height = h;
        this.depth = d;
        this.rotx = rx;
        this.roty = ry;
        this.rotz = rz;

        this.rotationX = 0;
        this.rotationY = 0;
        this.rotationZ = 0;

        SetupProgram();
        SetupVertices();
        SetupIndices();
    }

    public void Update() {
        rotationX += rotx;
        rotationY += roty;
        rotationZ += rotz;
    }

    public void Draw(float[] pvmatrix) {
        GLES20.glUseProgram(mProgram);

        int mVertexHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        GLES20.glEnableVertexAttribArray(mVertexHandle);
        GLES20.glVertexAttribPointer(mVertexHandle, 3, GLES20.GL_FLOAT, false, 0, mVertices);

        int mMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMatrix");

        float[] ModelMatrix = new float[16];
        Matrix.setIdentityM(ModelMatrix, 0);
        Matrix.translateM(ModelMatrix, 0, ModelMatrix, 0, this.x, this.y, this.z);
        Matrix.rotateM(ModelMatrix, 0, rotationX, 1, 0, 0);
        Matrix.rotateM(ModelMatrix, 0, rotationY, 0, 1, 0);
        Matrix.rotateM(ModelMatrix, 0, rotationZ, 0, 0, 1);

        float[] Test = new float[16];
        Matrix.multiplyMM(Test, 0, pvmatrix, 0, ModelMatrix, 0);

        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, Test, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mIndices.limit(), GLES20.GL_UNSIGNED_SHORT, mIndices);

        GLES20.glDisableVertexAttribArray(mVertexHandle);
    }

    public void SetupVertices() {
        Log.d("CUBE", "W: " + this.width + ", H: " + this.height + ", D: " + this.depth);
        float w = this.width / 2.0f;
        float h = this.height / 2.0f;
        float d = this.depth / 2.0f;

        float[] vertices = {
                -w, h, d,
                -w, -h, d,
                w, -h, d,
                w, h, d,
                w, h, -d,
                w, -h, -d,
                -w, -h, -d,
                -w, h, -d
        };
        mVertices = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertices.put(vertices).position(0);

    }

    public void SetupIndices() {
        short[] vertices = {
                0,1,2,0,2,3, // Front
                3,2,5,3,5,4, // Right
                4,5,6,4,6,7, // Back
                7,6,1,7,1,0 // Left
        };
        mIndices = ByteBuffer.allocateDirect(vertices.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        mIndices.put(vertices).position(0);
    }

    public void SetupProgram() {
        int[] status = new int[1];

        String vShaderCode = "attribute vec4 aPosition;" +
                "uniform mat4 uMatrix;" +
                "void main() {" +
                "   gl_Position = uMatrix * aPosition;" +
                "}";

        String fShaderCode = "precision mediump float;" +
                "void main() {" +
                "   gl_FragColor = vec4(0.45f, 0.6f, 0.89f, 1);" +
                "}";

        int vShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vShader, vShaderCode);
        GLES20.glCompileShader(vShader);
        GLES20.glGetShaderiv(vShader, GLES20.GL_COMPILE_STATUS, status, 0);
        if(status[0] == 0){
            Log.d("CUBE", GLES20.glGetShaderInfoLog(vShader));
        }

        int fShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fShader, fShaderCode);
        GLES20.glCompileShader(fShader);
        GLES20.glGetShaderiv(fShader, GLES20.GL_COMPILE_STATUS, status, 0);
        if(status[0] == 0) {
            Log.d("CUBE", GLES20.glGetShaderInfoLog(fShader));
        }

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vShader);
        GLES20.glAttachShader(mProgram, fShader);
        GLES20.glLinkProgram(mProgram);
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, status, 0);
        if(status[0] == 0) {
            Log.d("CUBE", GLES20.glGetProgramInfoLog(mProgram));
        }
    }
}
