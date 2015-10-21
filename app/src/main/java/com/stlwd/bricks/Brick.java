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
    List<RectF> uvs;

    public Brick(float x, float y, float z, float w, float h, int textureId, float uv_x, float uv_y, float uv_w, float uv_h, boolean si, Context context) {
        super(x, y, z, w, h, textureId, uv_x, uv_y, uv_w, uv_h, context);

        isSpecial = si;

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
