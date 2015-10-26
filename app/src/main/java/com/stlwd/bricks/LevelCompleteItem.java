package com.stlwd.bricks;

import android.content.Context;

/**
 * Created by mike.clubb on 10/7/15.
 */
public class LevelCompleteItem extends Sprite {

	int actionCode = 0;

    public LevelCompleteItem (float x, float y, float z, int textureId, Context context, int action) {
        super(x,y, z, 100, 100, textureId, 139/1024.0f, 170/778.0f, 186/1024.0f, 219/778.0f, context );

	actionCode = action;

	switch(actionCode) {
		case 0: // Home
			uv_x =  292/1024.0f;
			uv_y =	249/778.0f;
			uv_w = 	337/1024.0f;
			uv_h = 	295/778.0f;
			break;

		case 1: // Menu
			uv_x = 50/1024.0f;
			uv_y =	472/778.0f;
			uv_w = 	95/1024.0f;
			uv_h = 	519/778.0f;
			break;

		case 2: // Play
			uv_x = 50/1024.0f;
			uv_y =	249/778.0f;
			uv_w = 	95/1024.0f;
			uv_h = 	295/778.0f;
			break;

	}
	RefreshTexture();

    }

    public int GetActionCode() {
	    return actionCode;
    }
}
