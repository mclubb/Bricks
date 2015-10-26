package com.stlwd.bricks;

import java.util.ArrayList;
import java.util.List;

public class Levels
{
    List<List<Vector3>> levels;
    public Levels() {
    	levels = new ArrayList<>();

        List<Vector3> level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 240, 0));
        level.add(new Vector3(240, 240, 0));
        level.add(new Vector3(360, 240, 0));
        levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 360, 0));
        level.add(new Vector3(240, 360, 0));
        level.add(new Vector3(360, 360, 0));
        levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );
	
        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

        level = new ArrayList<Vector3>();
        level.add(new Vector3(120, 120, 0));
        level.add(new Vector3(240, 120, 0));
        level.add(new Vector3(360, 120, 0));
	levels.add( level );

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
