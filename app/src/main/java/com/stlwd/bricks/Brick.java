package com.stlwd.bricks;

import android.content.Context;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mike.clubb on 9/26/15.
 */
public class Brick extends Sprite {

    public boolean isAlive = true;
    public boolean isSpecial;
    int mBrickId = 0;
    List<RectF> uvs;
    List<RectF> brickUVS;

    public Brick(float x, float y, float z, int textureId,  boolean si, Context context, int brick_id) {
        super(x, y, z, 80, 23, textureId, 0, 0, 0, 0, context);

	setBrickUVS();

	mBrickId = brick_id;
        isSpecial = si;

        RectF uv = brickUVS.get(brick_id);

        uv_x = uv.left;
        uv_y = uv.top;
        uv_w = uv.right;
        uv_h = uv.bottom;
        RefreshTexture();

        setupExtraUVS();
    }

    public void Draw(float[] mvp) {
        if(isAlive) {
            super.Draw(mvp);
        }
    }

    public void Champlinize() {
        Random r = new Random();
        RectF uv = uvs.get(r.nextInt(uvs.size() - 1));

        uv_x = uv.left;
        uv_y = uv.top;
        uv_w = uv.right;
        uv_h = uv.bottom;
        RefreshTexture();
    }

    public void setBrickUVS() {
	brickUVS = new ArrayList<RectF>();
	brickUVS.add(new RectF(0, 469/512.0f, 80/512.0f, 488/512.0f));
	brickUVS.add(new RectF(0, 492/512.0f, 80/512.0f, 1));
	brickUVS.add(new RectF(84/512.0f, 492/512.0f, 162/512.0f, 1));
	brickUVS.add(new RectF(84/512.0f, 469/512.0f, 162/512.0f, 488/512.0f));
    }

    public void setupExtraUVS() {
        uvs = new ArrayList<RectF>();
        uvs.add(new RectF( 2/512.0f, 1/512.0f, 52/512.0f, 44/512.0f));
        uvs.add(new RectF(55/512.0f, 1/512.0f,105/512.0f, 44/512.0f));
        uvs.add(new RectF(2/512.0f, 50/512.0f, 52/512.0f, 93/512.0f));
        uvs.add(new RectF(55/512.0f, 50/512.0f,105/512.0f, 93/512.0f));
        uvs.add(new RectF(2/512.0f, 98/512.0f, 52/512.0f, 141/512.0f));
        uvs.add(new RectF(55/512.0f, 98/512.0f,105/512.0f, 141/512.0f));
    }


}
