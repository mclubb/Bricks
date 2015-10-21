package com.stlwd.bricks;

import android.content.Context;

/**
 * Created by mike.clubb on 10/1/15.
 */
public class SpecialItem extends Sprite {

    boolean isActive = false;
    float dropSpeed = 5.5f;

    public SpecialItem(float x, float y, float z, float width, float height, int textureId, float uv_x, float uv_y, float uv_w, float uv_h, Context context){
        super(x, y, z, width, height, textureId, uv_x, uv_y, uv_w, uv_h, context);

        isActive = true;
    }

    public void Update() {
        this.y += dropSpeed;

        if( this.y < 0 ) {
            isActive = false;
        }
    }

    public void Draw(float[] mvp) {
        if( isActive ) {
            super.Draw(mvp);
        }
    }
}
