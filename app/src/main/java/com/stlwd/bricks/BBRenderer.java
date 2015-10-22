package com.stlwd.bricks;

import android.content.Context;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mike.clubb on 9/25/15.
 */
public class BBRenderer implements Renderer {
    Context mContext;
    int mScreenWidth, mScreenHeight;
    float mWidth, mHeight;
    boolean loaded = false;
    int soundID;

    float[] m3DProjectionViewMatrix;
    float[] mProjectionMatrix;
    float[] m3DProjectionMatrix;

    float[] mModelMatrix;
    float[] mMVPMatrix;

    Camera camera;

    public enum GAMESCENE  {TITLE, MENU, GAME, LEVELCOMPLETE, GAMEOVER, CREDITS};

    int[] textures;
    Paddle mPaddle;
    Ball mBall;
    List<Brick> mBricks;
    List<SpecialItem> mSpecialItems;
    List<Cube> backgroundCubes;
    List<SpriteToo> backgroundSpinner;
    List<Minion> mMinions;
    List<Sprite> mLives;
    Sprite background;
    List<Vector3> levelList;
    Levels mLevels;
    int lives;

    float mPaddleLeftVelX;
    float mPaddleRightVelX;

    List<Sprite> titleObjects;
    List<Sprite> menuObjects;
    List<Sprite> gameOverObjects;
    Sprite border;

    int score;

    GAMESCENE mGameScene;

    int level = 0;
    boolean isBricks = true;

    public BBRenderer(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 1);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glDepthMask(true);

        m3DProjectionMatrix = new float[16];
        m3DProjectionViewMatrix = new float[16];
        mProjectionMatrix = new float[16];
        mModelMatrix = new float[16];
        mMVPMatrix = new float[16];

        textures = new int[5];
        mBricks = new ArrayList<Brick>();
        mSpecialItems = new ArrayList<SpecialItem>();
        backgroundCubes = new ArrayList<Cube>();
        backgroundSpinner = new ArrayList<SpriteToo>();
        titleObjects = new ArrayList<Sprite>();
        menuObjects = new ArrayList<Sprite>();
        gameOverObjects = new ArrayList<Sprite>();
        mMinions = new ArrayList<Minion>();
        mLives = new ArrayList<Sprite>();
        mLevels = new Levels();


        camera = new Camera(0, 0, -1, 0, 0, 0, 0, 1, 0);
        LoadContent();
    }

    public void handleTouch(MotionEvent event) {
        float x, y, rx, ry, nx, ny;

        x = event.getX();
        y = event.getY();

        rx = x / mScreenWidth;
        ry = y / mScreenHeight;

        nx = rx * mWidth;
        ny = ry * mHeight;

        if(mGameScene == GAMESCENE.GAME) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (event.getX() > (mScreenWidth / 2)) {
                    mPaddle.SetMoving(1);
                } else {
                    mPaddle.SetMoving(-1);
                }
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                mPaddle.SetNotMoving();
            }
        }

        if(mGameScene == GAMESCENE.MENU) {
            // Check to see if ACTION_UP and X, Y is in the play button
            if( event.getAction() == MotionEvent.ACTION_UP) {

                if( ny > 340 && ny < 457 && nx < 270) {
                    // Lets Play a Game
                    SetupNewGame();
                    mGameScene = GAMESCENE.GAME;
                }
            }
        }

        if( mGameScene == GAMESCENE.GAMEOVER ) {
            /*if( event.getAction() == MotionEvent.ACTION_UP ) {
                if( ny > 350 && ny < 425 ) {
                    SetupNewGame();
                    mGameScene = GAMESCENE.GAME;
                }

                if( ny > 542 && ny < 650 ) {
                    mGameScene = GAMESCENE.MENU;
                }
            }
	    */
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mScreenWidth = width;
        mScreenHeight = height;

        mWidth = 800;
        mHeight = 600;
        Matrix.orthoM(mProjectionMatrix, 0, 0, mWidth, mHeight, 0, 0, 100);

        float ratio = (float)width/height;
        Matrix.frustumM(m3DProjectionMatrix, 0, -ratio, ratio, -1, 1, 0.1f, 200);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Update();
        Draw();
    }

    public void AnimateToLevel(int lvl){
        if( lvl >= mLevels.GetLevelCount()) {
            // Really  this should be game over or back to menu or something
            level = 0;
        }
        SetupLevel();
    }


    public void Update() {

        switch(mGameScene) {
            case TITLE:
                TitleUpdate();
                break;
            case GAME:
                GameUpdate();
                break;
            case MENU:
                MenuUpdate();
                break;
	    case LEVELCOMPLETE:
		GameOverUpdate();
		break;
            case GAMEOVER:
                GameOverUpdate();
                break;
            default:
                break;
        }
    }

    List<Brick> BricksToRemove;
    List<SpecialItem>SpecialItemsToRemove;
    List<Minion>MinionsToRemove;
    public void GameUpdate() {
        GLES20.glClearColor(0, 0, 0, 1);
        Matrix.setIdentityM(m3DProjectionViewMatrix, 0);
        Matrix.multiplyMM(m3DProjectionViewMatrix, 0, m3DProjectionMatrix, 0, camera.getCameraViewMatrix(), 0);

        for (SpriteToo s : backgroundSpinner)
            s.Update();

        MinionsToRemove = new ArrayList<Minion>();
        for (Minion m : mMinions) {
            m.Update();
            if( m.y < 75.0f)
                MinionsToRemove.add(m);
        }

        for(Minion m : MinionsToRemove)
            mMinions.remove(m);


        if (mBricks.size() == 0) {
            level++;
            AnimateToLevel(level);
            return;
        }

	mPaddle.Update();
        mBall.Update();

        // Checking the bounds for the ball
        RectF ballRect = mBall.GetBounds();
        RectF boundsRect = new RectF(0, 100, 800, 600);
        // Check left and right walls
        if( ballRect.right > boundsRect.right || ballRect.left < boundsRect.left) {
            mBall.changeX();
        }

        // Check top wall
        if( ballRect.top < boundsRect.top) {
            mBall.changeY();
        }

        // Check to see if we hit the bottom
        if( ballRect.bottom > boundsRect.bottom) {
            // Dead
            mBall.ResetBall();
            if( mLives.size() == 0) {
                // Game Over
                mGameScene = GAMESCENE.GAMEOVER;
            } else {
                mLives.remove(mLives.size() - 1);
            }
        }

        // Checking collisions on the paddle
        RectF paddleRect = mPaddle.GetBounds();
        if(ballRect.intersect(paddleRect)) {
            mBall.changeY();
        }


        // Checking to see if the ball is hitting any bricks
        BricksToRemove = new ArrayList<Brick>();
        for (Brick b : mBricks) {
            if (b.isAlive) {
                if (mBall.x + (mBall.width / 2.0f) > b.x - (b.width / 2.0f) &&
                        mBall.x - (mBall.width / 2.0f) < b.x + (b.width / 2.0f) &&
                        mBall.y + (mBall.height / 2.0f) > b.y - (b.height / 2.0f) &&
                        mBall.y - (mBall.height / 2.0f) < b.y + (b.height / 2.0f)) {
                    if (b.isSpecial) {
                        Random r = new Random();
                        float n = r.nextFloat();
                        Log.d("SpecialItem", "Float: " + n);
                        if( n <  0.3f)
                            mSpecialItems.add(new SI_CHAMPLIN(b.x, b.y, b.z, 35, 35, b.mTextureId, 368/512.0f, 0, 400.0f/512.0f, 32/512.0f,mContext));
                        else if ( n < 0.6f)
                            mSpecialItems.add(new SI_Minion(b.x, b.y, b.z, 35, 43, b.mTextureId, 320/512.0f, 83/512.0f, 400.0f/512.0f, 232/512.0f, mContext));
                        else
                            mSpecialItems.add(new SI_MCNALLY(b.x, b.y, b.z, 35, 35, b.mTextureId, 0, 160/512.0f, 35.0f/512.0f, 195/512.0f, mContext));
                    }
                    score += 30;
                    BricksToRemove.add(b);
                    mBall.changeY();
                }
            }
        }

        // Remove any dead bricks
        for (Brick b : BricksToRemove) {
            mBricks.remove(b);
        }

        // Update special items
        for (SpecialItem si : mSpecialItems) {
            si.Update();
        }

        // check to see if the ball and the mMinions collide
        for (Minion m : mMinions ) {
            if( mBall.GetBounds().intersect(m.GetBounds())) {
                mBall.changeY();
                mBall.changeX();
            }
        }

        SpecialItemsToRemove = new ArrayList<SpecialItem>();
        // check to see if special item hits paddle
        for (SpecialItem si : mSpecialItems) {
            RectF siRect = si.GetBounds();
            if (siRect.intersect(paddleRect)) {
                score += 50;

                SpecialItemsToRemove.remove(si);
                si.isActive = false;
                if (si.getClass().equals(SI_Minion.class)) {
                    Minionize();
                } else if (si.getClass().equals(SI_MCNALLY.class)) {
                    mPaddleRightVelX *= -1;
                    mPaddleLeftVelX *= -1;
                } else if(si.getClass().equals(SI_CHAMPLIN.class)) {
                    mPaddle.uv_x = 0;
                    mPaddle.uv_y = 233/512.0f;
                    mPaddle.uv_w = 0.5f;
                    mPaddle.uv_h = 264/512.0f;
                    mPaddle.RefreshTexture();
                    for (Brick b : mBricks) {
                        b.Champlinize();
                    }
                }
            }
        }

        for(SpecialItem si : SpecialItemsToRemove)
            mSpecialItems.remove(si);
    }

    public void Minionize() {
        if(mMinions.size() > 0 ) {
            return;
        }

        Random r = new Random();
        mMinions.add(new Minion(840, 600, 0, 80, 150, textures[0], 320/512.0f, 83/512.0f, 400/512.0f, 232/512.0f, mContext));
    }

    public void Draw() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        switch(mGameScene) {
            case TITLE:
                TitleDraw();
                break;
            case GAME:
                GameDraw3D();
                GameDraw2D();
                break;
            case MENU:
                MenuDraw();
                break;
            case GAMEOVER:
                GameOverDraw();
                break;
            default:
                break;
        }
    }

    public void GameDraw2D() {
        background.Draw(mProjectionMatrix);
	border.Draw(mProjectionMatrix);
        mPaddle.Draw(mProjectionMatrix);
        mBall.Draw(mProjectionMatrix);

        for( Sprite s: mLives ) {
            s.Draw(mProjectionMatrix);
        }

        for( Brick b : mBricks ) {
            b.Draw(mProjectionMatrix);
        }

        for( Sprite s: mMinions )
            s.Draw(mProjectionMatrix);

        // Draw special items
        for(SpecialItem si : mSpecialItems) {
            si.Draw(mProjectionMatrix);
        }
    }

    public void GameDraw3D() {
        for( SpriteToo s : backgroundSpinner)
            s.Draw(m3DProjectionViewMatrix);
    }

    public void LoadContent() {
        textures[0] = Utils.loadTextures(mContext, R.raw.breakout_sprites);
        textures[1] = Utils.loadTextures(mContext, R.raw.background);
        textures[2] = Utils.loadTextures(mContext, R.raw.game_text);
        textures[3] = Utils.loadTextures(mContext, R.raw.window);
        textures[4] = Utils.loadTextures(mContext, R.raw.buttons);

        loadTitleObjects();
        loadMenuObjects();
        loadGameObjects();
        loadGameOverObjects();

        score = 0;

        mGameScene = GAMESCENE.TITLE;
	SetupNewGame();
    }

    public void SetupLives() {
        mLives.clear();
        lives = 3;

        for(int x = 0; x < lives; x++) {
            mLives.add(new Sprite(600 - (50 * x) - 50, 35, 0, 30, 30, textures[0], 160 / 512.0f, 199 / 512.0f, 175 / 512.0f, 214 / 512.0f, mContext));
        }
    }

    public void SetupNewGame() {
        SetupLives();
        SetupLevel();
    }

    public void SetupLevel() {
        mPaddleLeftVelX = -2.5f;
        mPaddleRightVelX = 2.5f;
        mBricks.clear();
        levelList = mLevels.GetLevel(level);
        for(Vector3 v : levelList) {
            mBricks.add(new Brick(v.x, v.y, v.z, 80, 15, textures[0], 0.0f, 470.0f/512.0f, 80.0f/512.0f, 483.0f/512.0f, false, mContext));
        }
        mBall.ResetBall();
        isBricks = true;
    }

    public void loadGameObjects() {
	border = new Border(300, 55, 0, 600, 8, textures[0], 201/512.0f, 47/512.0f, 501/512.0f, 79/512.0f, mContext); 
        background = new Sprite(400, 300, -1, 800, 600, textures[1], 0, 0, 1, 1, mContext);
        mPaddle = new Paddle(100, 575, 0, 100, 18, textures[0], 0, 443 / 512.0f, 78 / 512.0f, 463 / 512.0f, mContext);
        mBall = new Ball(100, 560, 0, 15, 15, textures[0], 330 / 512.0f, 490 / 512.0f, 345 / 512.0f, 505 / 512.0f, mContext);
    }

    public void loadGameOverObjects() {
	    gameOverObjects.clear();
	// Load the main window
	gameOverObjects.add( new Sprite(400, 300, -1, 800, 600, textures[3], 215.0f/819.0f, 32.0f/1024.0f, 405.0f/819.0f, 184.0f/1024.0f, mContext));
	
	// Load the level select text
	gameOverObjects.add( new Sprite(400, 50, 0, 248, 52, textures[3], 193.0f/819.0f, 796.0f/1024.0f, 275.0f/819.0f, 807.0f/1024.0f, mContext));

	// Unlit Stars
	gameOverObjects.add( new Sprite(400, 300, 0, 350, 204, textures[3], 473.0f/819.0f, 770.0f/1024.0f, 573.0f/819.0f, 821.0f/1024.0f, mContext));
	
	// Home
	gameOverObjects.add( new Sprite(250, 525, 0, 100, 100, textures[4], 292/1024.0f, 249/778.0f, 337/1024.0f, 295/778.0f, mContext) );
	
	// Menu
	gameOverObjects.add( new Sprite(400, 525, 0, 100, 100, textures[4], 50/1024.0f, 472/778.0f, 95/1024.0f, 519/778.0f, mContext) );
	
	// Play
	gameOverObjects.add( new Sprite(550, 525, 0, 100, 100, textures[4], 50/1024.0f, 249/778.0f, 95/1024.0f, 295/778.0f, mContext) );
	 
    }

    public void loadMenuObjects() {
	    menuObjects.clear();
	// Load the main window
	menuObjects.add( new Sprite(400, 300, 0, 800, 600, textures[3], 215.0f/819.0f, 32.0f/1024.0f, 405.0f/819.0f, 184.0f/1024.0f, mContext));

	// Load the level select text
	menuObjects.add( new Sprite(400, 50, 0, 248, 52, textures[3], 273.0f/819.0f, 12.0f/1024.0f, 341.0f/819.0f, 25.0f/1024.0f, mContext));

	// Load the levels that can be selected
	for(int a = 0; a < 3; a++)
	for(int i =0; i < 5; i++ ) {
		if( a * 5 + i <  4 ) {
			menuObjects.add( new Sprite((i * 100) + 130 + (30 * i), (a * 100) + 200 + (30 * a), 0, 100, 100, textures[4], 139/1024.0f, 170/778.0f, 186/1024.0f, 219/778.0f, mContext) );
		} else {
			menuObjects.add( new Sprite((i * 100) + 130 + (30 * i), (a * 100) + 200 + (30 * a), 0, 100, 100, textures[4], 355/1024.0f, 170/778.0f, 402/1024.0f, 219/778.0f, mContext) );
		}
	}
    }

    public void loadTitleObjects() {
        // Load the Beaver Text
        titleObjects.add(new Sprite(300, 135, 0, 500, 130, textures[2], 43/512.0f, 41/512.0f, 196/512.0f, 85/512.0f, mContext));

        // Load the beaver
        titleObjects.add(new Sprite(300, 265, 0, 256, 130, textures[0], 0, 335/512.0f, 125/512.0f, 400/512.0f, mContext));

        // Load the vulture Text
        titleObjects.add(new Sprite(300, 400, 0, 500, 130, textures[2], 43/512.0f, 98/512.0f, 196/512.0f, 139/512.0f, mContext));

        // Load the vulture
        titleObjects.add(new Sprite(300, 535, 0, 256, 130, textures[0], 128/512.0f, 335/512.0f, 256.0f/512.0f, 400.0f/512.0f,mContext));

        // Load the studios Text
        titleObjects.add(new Sprite(300, 670, 0, 500, 130, textures[2], 43 / 512.0f, 151 / 512.0f, 208 / 512.0f, 194 / 512.0f, mContext));
    }

    public void MenuUpdate() {
        GLES20.glClearColor(1, 1, 1, 1);
    }

    public void MenuDraw() {
        for(Sprite s : menuObjects) {
            s.Draw(mProjectionMatrix);
        }
    }

    public void GameOverUpdate() {
        GLES20.glClearColor(1, 1, 1, 1);
    }

    public void GameOverDraw() {
        for(Sprite s : gameOverObjects) {
            s.Draw(mProjectionMatrix);
        }
    }

    int screenTime;
    public void TitleUpdate() {
        GLES20.glClearColor(1, 1, 1, 1);

        for(Sprite s : titleObjects) {
            s.Update();
        }

        if( screenTime > 300 ) {
            mGameScene = GAMESCENE.MENU;
        }

        screenTime++;
    }

    public void TitleDraw() {
        for(Sprite s : titleObjects) {
            s.Draw(mProjectionMatrix);
        }
    }

}
