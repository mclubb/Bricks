package com.stlwd.bricks;

import java.util.ArrayList;
import java.util.List;

public class Levels
{
    List<List<Vector3>> levels;
    public Levels() {
    	levels = new ArrayList<>();

        List<Vector3> level = new ArrayList<Vector3>();
	// Level 0 - This will never be played
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 1
	level = new ArrayList<Vector3>();
	level.add(new Vector3(120, 125, 0));
	level.add(new Vector3(200, 125, 0));
	level.add(new Vector3(280, 125, 0));
	level.add(new Vector3(440, 125, 0));
	level.add(new Vector3(520, 125, 0));
	level.add(new Vector3(600, 125, 0));
	level.add(new Vector3(120, 150, 0));
	level.add(new Vector3(200, 150, 0));
	level.add(new Vector3(280, 150, 0));
	level.add(new Vector3(440, 150, 0));
	level.add(new Vector3(520, 150, 0));
	level.add(new Vector3(600, 150, 0));
	level.add(new Vector3(120, 175, 0));
	level.add(new Vector3(200, 175, 0));
	level.add(new Vector3(280, 175, 0));
	level.add(new Vector3(360, 175, 0));
	level.add(new Vector3(440, 175, 0));
	level.add(new Vector3(520, 175, 0));
	level.add(new Vector3(600, 175, 0));
	level.add(new Vector3(120, 200, 0));
	level.add(new Vector3(200, 200, 0));
	level.add(new Vector3(280, 200, 0));
	level.add(new Vector3(360, 200, 0));
	level.add(new Vector3(440, 200, 0));
	level.add(new Vector3(520, 200, 0));
	level.add(new Vector3(600, 200, 0));
	level.add(new Vector3(200, 225, 0));
	level.add(new Vector3(280, 225, 0));
	level.add(new Vector3(360, 225, 0));
	level.add(new Vector3(440, 225, 0));
	level.add(new Vector3(520, 225, 0));
	level.add(new Vector3(360, 250, 0));
	levels.add( level );

	// Level 2
	level = new ArrayList<Vector3>();
	level.add(new Vector3(40, 125, 0));
	level.add(new Vector3(200, 125, 2));
	level.add(new Vector3(360, 125, 0));
	level.add(new Vector3(520, 125, 2));
	level.add(new Vector3(680, 125, 0));
	level.add(new Vector3(120, 150, 1));
	level.add(new Vector3(280, 150, 3));
	level.add(new Vector3(440, 150, 1));
	level.add(new Vector3(600, 150, 3));
	level.add(new Vector3(760, 150, 1));
	level.add(new Vector3(40, 175, 0));
	level.add(new Vector3(200, 175, 2));
	level.add(new Vector3(360, 175, 0));
	level.add(new Vector3(520, 175, 2));
	level.add(new Vector3(680, 175, 0));
	level.add(new Vector3(120, 200, 1));
	level.add(new Vector3(280, 200, 3));
	level.add(new Vector3(440, 200, 1));
	level.add(new Vector3(600, 200, 3));
	level.add(new Vector3(760, 200, 1));
	level.add(new Vector3(40, 225, 0));
	level.add(new Vector3(200, 225, 2));
	level.add(new Vector3(360, 225, 0));
	level.add(new Vector3(520, 225, 2));
	level.add(new Vector3(680, 225, 0));
	level.add(new Vector3(120, 250, 1));
	level.add(new Vector3(280, 250, 3));
	level.add(new Vector3(440, 250, 1));
	level.add(new Vector3(600, 250, 3));
	level.add(new Vector3(760, 250, 1));
	levels.add( level );

	// Level 3
	level = new ArrayList<Vector3>();
	level.add(new Vector3(40, 125, 0));
	level.add(new Vector3(120, 125, 1));
	level.add(new Vector3(200, 125, 2));
	level.add(new Vector3(280, 125, 3));
	level.add(new Vector3(360, 125, 0));
	level.add(new Vector3(440, 125, 1));
	level.add(new Vector3(520, 125, 2));
	level.add(new Vector3(600, 125, 3));
	level.add(new Vector3(680, 125, 0));
	level.add(new Vector3(760, 125, 1));
	level.add(new Vector3(40, 150, 1));
	level.add(new Vector3(120, 150, 0));
	level.add(new Vector3(200, 150, 1));
	level.add(new Vector3(280, 150, 2));
	level.add(new Vector3(360, 150, 3));
	level.add(new Vector3(440, 150, 0));
	level.add(new Vector3(520, 150, 1));
	level.add(new Vector3(600, 150, 2));
	level.add(new Vector3(680, 150, 3));
	level.add(new Vector3(760, 150, 0));
	level.add(new Vector3(40, 175, 2));
	level.add(new Vector3(120, 175, 1));
	level.add(new Vector3(200, 175, 0));
	level.add(new Vector3(280, 175, 1));
	level.add(new Vector3(360, 175, 2));
	level.add(new Vector3(440, 175, 3));
	level.add(new Vector3(520, 175, 0));
	level.add(new Vector3(600, 175, 1));
	level.add(new Vector3(680, 175, 2));
	level.add(new Vector3(760, 175, 3));
	level.add(new Vector3(40, 200, 3));
	level.add(new Vector3(120, 200, 2));
	level.add(new Vector3(200, 200, 1));
	level.add(new Vector3(280, 200, 0));
	level.add(new Vector3(360, 200, 1));
	level.add(new Vector3(440, 200, 2));
	level.add(new Vector3(520, 200, 3));
	level.add(new Vector3(600, 200, 0));
	level.add(new Vector3(680, 200, 1));
	level.add(new Vector3(760, 200, 2));
	level.add(new Vector3(40, 225, 0));
	level.add(new Vector3(120, 225, 3));
	level.add(new Vector3(200, 225, 2));
	level.add(new Vector3(280, 225, 1));
	level.add(new Vector3(360, 225, 0));
	level.add(new Vector3(440, 225, 1));
	level.add(new Vector3(520, 225, 2));
	level.add(new Vector3(600, 225, 3));
	level.add(new Vector3(680, 225, 0));
	level.add(new Vector3(760, 225, 1));
	level.add(new Vector3(40, 250, 1));
	level.add(new Vector3(120, 250, 0));
	level.add(new Vector3(200, 250, 3));
	level.add(new Vector3(280, 250, 2));
	level.add(new Vector3(360, 250, 1));
	level.add(new Vector3(440, 250, 0));
	level.add(new Vector3(520, 250, 1));
	level.add(new Vector3(600, 250, 2));
	level.add(new Vector3(680, 250, 3));
	level.add(new Vector3(760, 250, 0));
	levels.add( level );

	// Level 4
	level = new ArrayList<Vector3>();
	level.add(new Vector3(200, 125, 2));
	level.add(new Vector3(280, 125, 2));
	level.add(new Vector3(360, 125, 2));
	level.add(new Vector3(440, 125, 2));
	level.add(new Vector3(520, 125, 2));
	level.add(new Vector3(600, 125, 2));
	level.add(new Vector3(200, 150, 2));
	level.add(new Vector3(280, 150, 1));
	level.add(new Vector3(360, 150, 1));
	level.add(new Vector3(440, 150, 1));
	level.add(new Vector3(520, 150, 1));
	level.add(new Vector3(600, 150, 2));
	level.add(new Vector3(200, 175, 2));
	level.add(new Vector3(280, 175, 1));
	level.add(new Vector3(360, 175, 0));
	level.add(new Vector3(440, 175, 0));
	level.add(new Vector3(520, 175, 1));
	level.add(new Vector3(600, 175, 2));
	level.add(new Vector3(200, 200, 2));
	level.add(new Vector3(280, 200, 1));
	level.add(new Vector3(360, 200, 1));
	level.add(new Vector3(440, 200, 1));
	level.add(new Vector3(520, 200, 1));
	level.add(new Vector3(600, 200, 2));
	level.add(new Vector3(200, 225, 2));
	level.add(new Vector3(280, 225, 2));
	level.add(new Vector3(360, 225, 2));
	level.add(new Vector3(440, 225, 2));
	level.add(new Vector3(520, 225, 2));
	level.add(new Vector3(600, 225, 2));
	levels.add( level );

	// Level 5
	level = new ArrayList<Vector3>();
	level.add(new Vector3(40, 125, 0));
	level.add(new Vector3(200, 125, 0));
	level.add(new Vector3(360, 125, 0));
	level.add(new Vector3(520, 125, 0));
	level.add(new Vector3(680, 125, 0));
	level.add(new Vector3(120, 150, 0));
	level.add(new Vector3(280, 150, 0));
	level.add(new Vector3(440, 150, 0));
	level.add(new Vector3(600, 150, 0));
	level.add(new Vector3(760, 150, 0));
	level.add(new Vector3(40, 175, 0));
	level.add(new Vector3(200, 175, 0));
	level.add(new Vector3(360, 175, 0));
	level.add(new Vector3(520, 175, 0));
	level.add(new Vector3(680, 175, 0));
	level.add(new Vector3(120, 200, 0));
	level.add(new Vector3(280, 200, 0));
	level.add(new Vector3(440, 200, 0));
	level.add(new Vector3(600, 200, 0));
	level.add(new Vector3(760, 200, 0));
	level.add(new Vector3(40, 225, 3));
	level.add(new Vector3(200, 225, 3));
	level.add(new Vector3(360, 225, 3));
	level.add(new Vector3(520, 225, 3));
	level.add(new Vector3(680, 225, 3));
	levels.add( level );

	// Level 6
	level = new ArrayList<Vector3>();
	level.add(new Vector3(200, 125, 1));
	level.add(new Vector3(280, 125, 0));
	level.add(new Vector3(360, 125, 1));
	level.add(new Vector3(120, 150, 1));
	level.add(new Vector3(200, 150, 0));
	level.add(new Vector3(280, 150, 1));
	level.add(new Vector3(360, 150, 0));
	level.add(new Vector3(440, 150, 1));
	level.add(new Vector3(760, 150, 1));
	level.add(new Vector3(40, 175, 1));
	level.add(new Vector3(120, 175, 0));
	level.add(new Vector3(200, 175, 1));
	level.add(new Vector3(360, 175, 1));
	level.add(new Vector3(440, 175, 0));
	level.add(new Vector3(520, 175, 1));
	level.add(new Vector3(680, 175, 1));
	level.add(new Vector3(760, 175, 0));
	level.add(new Vector3(40, 200, 0));
	level.add(new Vector3(120, 200, 1));
	level.add(new Vector3(440, 200, 1));
	level.add(new Vector3(520, 200, 0));
	level.add(new Vector3(600, 200, 1));
	level.add(new Vector3(680, 200, 0));
	level.add(new Vector3(760, 200, 1));
	level.add(new Vector3(40, 225, 1));
	level.add(new Vector3(520, 225, 1));
	level.add(new Vector3(600, 225, 0));
	level.add(new Vector3(680, 225, 1));
	level.add(new Vector3(600, 250, 1));
	levels.add( level );

	// Level 7
	level = new ArrayList<Vector3>();
	level.add(new Vector3(40, 125, 0));
	level.add(new Vector3(120, 125, 0));
	level.add(new Vector3(360, 125, 0));
	level.add(new Vector3(600, 125, 0));
	level.add(new Vector3(760, 125, 0));
	level.add(new Vector3(200, 150, 0));
	level.add(new Vector3(520, 150, 0));
	level.add(new Vector3(280, 175, 0));
	level.add(new Vector3(440, 175, 0));
	level.add(new Vector3(40, 200, 0));
	level.add(new Vector3(120, 200, 0));
	level.add(new Vector3(200, 200, 0));
	level.add(new Vector3(360, 200, 0));
	level.add(new Vector3(520, 200, 0));
	level.add(new Vector3(600, 200, 0));
	level.add(new Vector3(680, 200, 0));
	level.add(new Vector3(760, 200, 0));
	level.add(new Vector3(280, 225, 0));
	level.add(new Vector3(440, 225, 0));
	level.add(new Vector3(40, 250, 0));
	level.add(new Vector3(200, 250, 0));
	level.add(new Vector3(520, 250, 0));
	level.add(new Vector3(760, 250, 0));
	levels.add( level );

	// Level 8
	level = new ArrayList<Vector3>();
	level.add(new Vector3(120, 125, 1));
	level.add(new Vector3(40, 150, 1));
	level.add(new Vector3(120, 150, 1));
	level.add(new Vector3(200, 150, 1));
	level.add(new Vector3(120, 175, 1));
	level.add(new Vector3(680, 175, 1));
	level.add(new Vector3(360, 200, 1));
	level.add(new Vector3(600, 200, 1));
	level.add(new Vector3(680, 200, 1));
	level.add(new Vector3(760, 200, 1));
	level.add(new Vector3(200, 225, 3));
	level.add(new Vector3(280, 225, 1));
	level.add(new Vector3(360, 225, 1));
	level.add(new Vector3(440, 225, 1));
	level.add(new Vector3(520, 225, 3));
	level.add(new Vector3(680, 225, 1));
	level.add(new Vector3(360, 250, 1));
	levels.add( level );

	// Level 9
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 10
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 11
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 12
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 13
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 14
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 15
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 16
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 17
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );
	
	// Level 18
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 19
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 20
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 21
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 22
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 23
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );
	
	// Level 24
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 25
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 26
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 27
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );
	
	// Level 28
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 29
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

	// Level 30 
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );
    }

    public List<Vector3> GetLevel(int level)
    {
	return levels.get(level);
    }

    public int GetLevelCount() {
        return levels.size();
    }
}
