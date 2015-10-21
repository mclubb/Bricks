package com.stlwd.bricks;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by mike.clubb on 10/2/15.
 */
public class SpriteToo {

        // Private Variables
        private FloatBuffer vertices;
        private ShortBuffer indices;
        private FloatBuffer textureCoords;
        int mProgram;
        String vShaderSource = null;
        String fShaderSource = null;

        float x, y, z, width, height;
        float uv_x, uv_y, uv_w, uv_h;
        int mTextureId;

    float angle;

        public SpriteToo(float x, float y, float z, float w, float h, int textureId) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.width = w;
            this.height = h;
            this.mTextureId = textureId;
            this.uv_x = 0.0f;
            this.uv_y = 0.0f;
            this.uv_w = 1.0f;
            this.uv_h = 1.0f;
            SetupSprite();
        }

        public SpriteToo(float x, float y, float z, float w, float h, int textureId, float uv_w, float uv_h) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.width = w;
            this.height = h;
            this.mTextureId = textureId;
            this.uv_x = 0.0f;
            this.uv_y = 0.0f;
            this.uv_w = uv_w;
            this.uv_h = uv_h;
            SetupSprite();
        }

        public SpriteToo(float x, float y, float z, float w, float h, int textureId, float uv_x, float uv_y, float uv_w, float uv_h) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.width = w;
            this.height = h;
            this.mTextureId = textureId;
            this.uv_x = uv_x;
            this.uv_y = uv_y;
            this.uv_w = uv_w;
            this.uv_h = uv_h;
            SetupSprite();
        }

        public void Update(){
            angle += 0.75f;
        }

        public void Draw(float[] ProjectionMatrix) {
            GLES20.glUseProgram(mProgram);

            int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
            GLES20.glEnableVertexAttribArray(mPositionHandle);
            GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertices);

            int mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "aTextCoord");
            GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
            GLES20.glVertexAttribPointer(mTextureCoordHandle, 2, GLES20.GL_FLOAT, false, 0, textureCoords);

            int mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "uTexture");
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
            GLES20.glUniform1i(mTextureUniformHandle, 0);

            // Local variable to hold model matrix
            float[] ModelMatrix = new float[16];
            float[] RotationMatrix = new float[16];
            float[] ModelRotationMatrix = new float[16];
            float[] ModelProjectionMatrix = new float[16];

            // Clear the model matrix
            Matrix.setIdentityM(ModelMatrix, 0);
            Matrix.setIdentityM(RotationMatrix, 0);

            // Move the model in x, y, z
            Matrix.translateM(ModelMatrix, 0, ModelMatrix, 0, this.x, this.y, this.z);

            Matrix.rotateM(RotationMatrix, 0, angle, 0, 0, -1);

            Matrix.multiplyMM(ModelRotationMatrix, 0, ModelMatrix, 0, RotationMatrix, 0);

            // Multiply the model and the Projection Matrices
            Matrix.multiplyMM(ModelProjectionMatrix, 0, ProjectionMatrix, 0, ModelRotationMatrix, 0);

            // Setup a handle for the projection
            int mProjectionHandle = GLES20.glGetUniformLocation(mProgram, "uMatrix");
            GLES20.glUniformMatrix4fv(mProjectionHandle, 1, false, ModelProjectionMatrix, 0);

            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

            GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.limit(), GLES20.GL_UNSIGNED_SHORT, indices);

            GLES20.glDisableVertexAttribArray(mPositionHandle);
        }

        public void SetupSprite() {

            angle = 0.0f;

            vShaderSource = "attribute vec4 aPosition;" +
                    "uniform mat4 uMatrix;" +
                    "attribute vec2 aTextCoord;" +
                    "varying vec2 vTextCoord;" +
                    "void main() {" +
                    "   gl_Position = uMatrix * aPosition;" +
                    "   vTextCoord = aTextCoord;" +
                    "}";

            fShaderSource = "precision mediump float;" +
                    "uniform sampler2D uTexture;" +
                    "varying vec2 vTextCoord;" +
                    "void main() {" +
                    "   gl_FragColor = texture2D(uTexture, vTextCoord);" +
                    "}";

            int[] status = new int[1];
            int vShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
            GLES20.glShaderSource(vShader, vShaderSource);
            GLES20.glCompileShader(vShader);
            GLES20.glGetShaderiv(vShader, GLES20.GL_COMPILE_STATUS, status, 0);
            if( status[0] != GLES20.GL_TRUE ) {
                Log.d("Sprite Shader Failed", GLES20.glGetShaderInfoLog(vShader));
            }

            int fShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
            GLES20.glShaderSource(fShader, fShaderSource);
            GLES20.glCompileShader(fShader);
            GLES20.glGetShaderiv(fShader, GLES20.GL_COMPILE_STATUS, status, 0);
            if( status[0] != GLES20.GL_TRUE ) {
                Log.d("Sprite Shader Failed", GLES20.glGetShaderInfoLog(fShader));
            }

            mProgram = GLES20.glCreateProgram();
            GLES20.glAttachShader(mProgram, vShader);
            GLES20.glAttachShader(mProgram, fShader);
            GLES20.glLinkProgram(mProgram);
            GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, status, 0);
            if( status[0] != GLES20.GL_TRUE ) {
                Log.d("Program Failed", GLES20.glGetProgramInfoLog(mProgram));
            }

            float w = width / 2.0f;
            float h = height / 2.0f;

            float[] verts = {-w, h, 0, -w, -h, 0,w, -h, 0, w,  h, 0};
            vertices = ByteBuffer.allocateDirect(verts.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            vertices.put(verts);
            vertices.position(0);

            short[] index = {0, 1, 2, 0, 2, 3};
            indices = ByteBuffer.allocateDirect(index.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
            indices.put(index);
            indices.position(0);

            float[] textureCoordData = {uv_x, uv_h, uv_x, uv_y, uv_w, uv_y, uv_w, uv_h};
            textureCoords = ByteBuffer.allocateDirect(textureCoordData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            textureCoords.put(textureCoordData);
            textureCoords.position(0);
        }
    }
