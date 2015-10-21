package com.stlwd.bricks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * Created by mike.clubb on 9/25/15.
 */
public class Utils {
    public static int loadTextures(Context context, int resourceId) {
        // Hold the texture id
        final int[] textureHandle = new int[1];

        // Generate the texture
        GLES20.glGenTextures(1, textureHandle, 0);

        // Make sure that the texture was generated
        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        Log.d("LoadTexture", "Loading Resource ID: " + resourceId);
        return textureHandle[0];
    }


    public static int SetupProgram(Context mContext) {
	return SetupProgram(mContext, R.string.basic_vertex_shader, R.string.basic_fragment_shader);
    }

    public static int SetupProgram(Context mContext, int vShaderSource, int fShaderSource) {

        int[] status = new int[1];
        int vShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vShader, mContext.getString(vShaderSource));
        GLES20.glCompileShader(vShader);
        GLES20.glGetShaderiv(vShader, GLES20.GL_COMPILE_STATUS, status, 0);
        if( status[0] != GLES20.GL_TRUE ) {
            Log.d("Sprite Shader Failed", GLES20.glGetShaderInfoLog(vShader));
        }

        int fShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fShader, mContext.getString(fShaderSource));
        GLES20.glCompileShader(fShader);
        GLES20.glGetShaderiv(fShader, GLES20.GL_COMPILE_STATUS, status, 0);
        if( status[0] != GLES20.GL_TRUE ) {
            Log.d("Sprite Shader Failed", GLES20.glGetShaderInfoLog(fShader));
        }

        int mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vShader);
        GLES20.glAttachShader(mProgram, fShader);
        GLES20.glLinkProgram(mProgram);
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, status, 0);
        if( status[0] != GLES20.GL_TRUE ) {
            Log.d("Program Failed", GLES20.glGetProgramInfoLog(mProgram));
        }

        return mProgram;
    }
}
