package com.stlwd.bricks;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mike.clubb on 9/26/15.
 */
public class BrickRotate extends Sprite {

    float angle;

    public BrickRotate(float x, float y, float z, float w, float h, int textureId, float uv_x, float uv_y, float uv_w, float uv_h, boolean si, Context context) {
        super(x, y, z, w, h, textureId, uv_x, uv_y, uv_w, uv_h, context);
        rotation = 0.0f;
    }

    public void Update() {
        rotation += 0.5f;
    }



}
