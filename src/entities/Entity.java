package entities;

import graphics.IRender;
import graphics.Sprite;
import graphics.Screen;
import level.Coordinate;

/**
 * Lop dai dien cho cac thuc the cua game
 */

public abstract class Entity implements IRender{
    protected double x, y;
    protected boolean remove = false;
    protected Sprite sprite;


    // Phuong thuc cap nhat Entity
    @Override
    public abstract void update();

    // Phuong thuc ve Entity
    @Override
    public abstract void render(Screen screen);

    // Phuong thuc xu ly hai Entity va cham nhau
    public abstract boolean collide(Entity z);

   
   
    public void remove(){
        remove = true;
    }
    
    public boolean isRemove(){
        return remove;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getXtile(){
        return 0;
    }
    public int getYtile(){
        return 0;
    }
}