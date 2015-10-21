package com.stlwd.bricks;

import android.content.Context;
import android.graphics.RectF;

/**
 * Created by mike.clubb on 9/26/15.
 */
public class Border extends Sprite {

    public Border(float x, float y, float z, float w, float h, int textureId, float uv_x, float uv_y, float uv_w, float uv_h, Context context) {
        super(x, y, z, w, h, textureId, uv_x, uv_y, uv_w, uv_h, context);
        mProgram = Utils.SetupProgram(context, R.string.basic_vertex_shader, R.string.bloom_fragment_shader);
    }
}
