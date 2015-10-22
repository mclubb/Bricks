package com.stlwd.bricks;

import android.content.Context;

/**
 * Created by mike.clubb on 9/26/15.
 */
public class Paddle extends Sprite {
    float velX;
    int direction;

    public Paddle(float x, float y, float z, float w, float h, int textureId, float uv_x, float uv_y, float uv_w, float uv_h, Context context) {
        super(x, y, z, w, h, textureId, uv_x, uv_y, uv_w, uv_h, context);

        velX = 7.5f;
	direction = 0;
    }

    public void SetMoving(int direction) {
	this.direction = direction;
    }

    public void SetNotMoving() {
	this.direction = 0;
    }

    public void Update() {
	this.x += (float)velX * direction;

        if( this.x - (width/2.0f) < 0)
            this.x = (width/2.0f);

        if( this.x + (width/2.0f) > 800 )
	    this.x = 800 - (width/2.0f);
    }

    public void Move(float direction ) {

        if( direction < 0 ) {
            this.x -= velX;
        } else {
            this.x += velX;
        }

        if( this.x - (width/2.0f) < 0)
            this.x = (width/2.0f);

        if( this.x + (width/2.0f) > 800 )
            this.x = 800 - (width/2.0f);
    }
}
