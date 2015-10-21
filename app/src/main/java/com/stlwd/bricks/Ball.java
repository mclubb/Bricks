package com.stlwd.bricks;

import android.content.Context;
import android.graphics.RectF;

/**
 * Created by mike.clubb on 9/26/15.
 */
public class Ball extends Sprite {
    float velX;
    float velY;

    boolean Reset = false;
    float resetTimer;

    public Ball(float x, float y, float z, float w, float h, int textureId, float uv_x, float uv_y, float uv_w, float uv_h, Context context) {
        super(x, y, z, w, h, textureId, uv_x, uv_y, uv_w, uv_h, context);

        mProgram = Utils.SetupProgram(context, R.string.basic_vertex_shader, R.string.bloom_fragment_shader);

        //ResetBall();
    }

    public void changeY() {
        velY *= -1;
    }
    public void changeX() {
        velX *= -1;
    }

    public void Update() {

        if( Reset ){
            // Update reset timer
            resetTimer += 0.1f;

            if( resetTimer < 3.0f)
                return;
        }

        x += velX;
        y += velY;




    }

    public void ResetBall() {

        Reset = true;
        resetTimer = 0.0f;
        x = 300 - (width/2.0f);
        y = 700;

        velX = 3.5f;
        velY = -7.5f;
    }
}
