package com.stlwd.bricks;

import android.content.Context;

/**
 * Created by mike.clubb on 10/7/15.
 */
public class MenuItem extends Sprite {
	int mLevelId = 0;
    public MenuItem (float x, float y, float z, int textureId, Context context, boolean locked, int levelId) {
        super(x,y, z, 100, 100, textureId, 139/1024.0f, 170/778.0f, 186/1024.0f, 219/778.0f, context );

	if( locked )
	{
        	uv_x = 355/1024.0f;
       		uv_y =	170/778.0f;
	        uv_w = 	402/1024.0f;
	        uv_h = 	219/778.0f;

		RefreshTexture();
	}

	mLevelId = levelId;
    }
    
    public int GetLevelId() {
	    return mLevelId;
    }
}
