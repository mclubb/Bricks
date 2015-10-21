package com.stlwd.bricks;

import android.content.Context;

/**
 * Created by mike.clubb on 10/6/15.
 */
public class Minion extends Sprite {
    float velx = -3.25f;

    public Minion (float x, float y, float z, float w, float h, int textureId, float uv_x, float uv_y, float uv_w, float uv_h, Context context) {
        super(x, y, z, w, h, textureId, uv_x, uv_y, uv_w, uv_h, context);
    }

    public void Update() {
        x += velx;

        if( x < -width/2.0f || x > 600 + width/2.0f) {
            velx *= -1;
            y -= height/2.0f;
        }
    }
}
