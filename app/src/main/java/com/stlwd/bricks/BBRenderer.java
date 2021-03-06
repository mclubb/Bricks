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

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    List<MenuItem> menuLevelObjects;
    List<LevelCompleteItem> levelCompleteButtons; 
    List<Sprite> gameOverObjects;

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
        menuLevelObjects = new ArrayList<MenuItem>();
        gameOverObjects = new ArrayList<Sprite>();
        levelCompleteButtons = new ArrayList<LevelCompleteItem>();
        mMinions = new ArrayList<Minion>();
        mLives = new ArrayList<Sprite>();
        mLevels = new Levels();


        camera = new Camera(0, 0, -1, 0, 0, 0, 0, 1, 0);
        LoadContent();
    }

    public void Save(String path, String s) {
	String LevelFromSaveFile = GetLevelFromSave(path);

	int levelFromGame = 0;
	int levelFromSave = 0;

	try {
		levelFromGame = Integer.parseInt(s);
	} catch( Exception e) {}

	try {
		levelFromSave = Integer.parseInt(LevelFromSaveFile);
	} catch( Exception e) {}	

	if( levelFromGame > levelFromSave ) { 
		// Write the current level to the file
		File file = new File(path);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(s.getBytes());
			fos.close();
		} catch( Exception e) {}
	}
    }

    public String GetLevelFromSave(String path) {
	    Log.d("READER", "GETLEVELFROMSAVE: " + path);
	String sLevel = "";
	File file = new File(path);
	FileInputStream fis = null;
	InputStreamReader isr = null;
	BufferedReader br = null;
	String brString;
	try {
		fis = new FileInputStream(file);
		isr = new InputStreamReader(fis);
		br = new BufferedReader(isr);
		while( (brString = br.readLine() ) != null ) {
			sLevel += brString;
		}
		fis.close();
	} catch( Exception e) { Log.e("READER", "GETLEVELFROMSAVE FAILED", e);}
	if( sLevel.equals("") ) { sLevel = "0"; }
	return sLevel;
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

		for(MenuItem mi : menuLevelObjects) {
			if( mi.GetBounds().contains(nx, ny) && !mi.IsLocked() ) {
				level = mi.GetLevelId();
				SetupNewGame();
				mGameScene = GAMESCENE.GAME;
			}
		}
            }
        }

        if( mGameScene == GAMESCENE.GAMEOVER ) {
		for(LevelCompleteItem lci : levelCompleteButtons) {
			if( lci.GetBounds().contains(nx, ny)) {
				// Get the action so we know what to do
				int action = lci.GetActionCode();

				switch(action) {
					case 0: // Home
						mGameScene = GAMESCENE.TITLE;
						break;
					case 1: // Menu
						loadMenuObjects();
						mGameScene = GAMESCENE.MENU;
						break;
					case 2: // Play
						SetupNewGame();
						mGameScene = GAMESCENE.GAME;
						break;
				}
				break;
			}
		}
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
		// TODO Write to a file showing that the level has been completed
		String path = mContext.getExternalFilesDir(null) + "/data";
		String fileName = path + "/level_completed.txt";
		Save(fileName, "" + level);

	
		// Setup the GameScene LevelComplete	
		loadMenuObjects();
		mGameScene = GAMESCENE.GAMEOVER;
		
            	//AnimateToLevel(level);
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
            case LEVELCOMPLETE:
                GameOverDraw();
            case GAMEOVER:
                GameOverDraw();
                break;
            default:
                break;
        }
    }

    public void GameDraw2D() {
        background.Draw(mProjectionMatrix);
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
            mBricks.add(new Brick(v.x, v.y, 0, textures[0], false, mContext, (int)v.z));
        }
        mBall.ResetBall();
        isBricks = true;
    }

    public void loadGameObjects() {
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
	levelCompleteButtons.add( new LevelCompleteItem(250, 525, 0, textures[4], mContext, 0) );
	
	// Menu
	levelCompleteButtons.add( new LevelCompleteItem(400, 525, 0, textures[4], mContext, 1) );
	
	// Play
	levelCompleteButtons.add( new LevelCompleteItem(550, 525, 0, textures[4], mContext, 2) );
	 
    }

    public void loadMenuObjects() {
	String path = mContext.getExternalFilesDir(null) + "/data";
	String fileName = path + "/level_completed.txt";
	File levelDir = new File(path);
	    if( !levelDir.exists() ) {
		    try {
		   	Log.d("READER", "TRYING TO CREATE DIRECTORY");
		    	levelDir.mkdirs();
		    } catch (Exception e) { Log.e("READER", "FAILED TO CREATE DIRECTORY", e); }
	    }
	    
	    File levelFile = new File(fileName);
	    if( !levelFile.exists() ) {
		try {
		    	Log.d("READER", "TRYING TO CREATE FILE");
			levelFile.createNewFile();
		}
		catch(Exception e) { Log.e("READER", "FAILED TO CREATE NEW FILE", e); }
	    }

		String level_number = "";
		int l = 0;
	// We need to get current level passed
		level_number = GetLevelFromSave(fileName); 
		Log.d("READER", "LEVEL NUMBER: " + level_number);
		try {
			l = Integer.parseInt(level_number);
		} catch (Exception e) { }


	    menuObjects.clear();
	// Load the main window
	menuObjects.add( new Sprite(400, 300, 0, 800, 600, textures[3], 215.0f/819.0f, 32.0f/1024.0f, 405.0f/819.0f, 184.0f/1024.0f, mContext));

	// Load the level select text
	menuObjects.add( new Sprite(400, 50, 0, 248, 52, textures[3], 273.0f/819.0f, 12.0f/1024.0f, 341.0f/819.0f, 25.0f/1024.0f, mContext));

	// Load the levels that can be selected
	for(int a = 0; a < 3; a++)
	for(int i =0; i < 5; i++ ) {
		if( a * 5 + i <=  l ) {
			menuLevelObjects.add( new MenuItem((i * 100) + 130 + (30 * i), (a * 100) + 200 + (30 * a), 0, textures[4], mContext, false, a*5 + i + 1) );
		} else {
			menuLevelObjects.add( new MenuItem((i * 100) + 130 + (30 * i), (a * 100) + 200 + (30 * a), 0, textures[4], mContext, true, a*5 + i + 1) );
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

        for(MenuItem s : menuLevelObjects) {
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

	for(LevelCompleteItem lci : levelCompleteButtons) {
		lci.Draw(mProjectionMatrix);
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
