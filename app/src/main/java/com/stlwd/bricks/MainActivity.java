package com.stlwd.bricks;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private GameSurfaceview mGameSurfaceview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Super
        super.onCreate(savedInstanceState);

        // Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Lets create our GameSurfaceView
        mGameSurfaceview = new GameSurfaceview(this);

        // Lets load our view
        setContentView(mGameSurfaceview);
    }

    @Override
     protected void onPause() {
	mGameSurfaceview.onPause();
	super.onPause();
     }

    @Override
     protected void onResume() {
	mGameSurfaceview.onResume();
	super.onResume();
     }

}
