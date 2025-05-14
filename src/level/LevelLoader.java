package level;

import main.Board;
import exceptions.Loadlevelexception;

/*
 * Load và lưu trữ thông tin các thực thể trong game
 */

public abstract class LevelLoader{
    protected int width = 20, height = 20;
    protected int level;
    protected Board board;

    public LevelLoader(Board board, int level) throws Loadlevelexception{
        this.board = board;
        loadLevel(level);
        
    }

    public abstract void loadLevel(int level) throws Loadlevelexception;

    public abstract void createEntities();

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getLevel(){
        return this.level;
    }
}