package entities;

import graphics.IRender;
import graphics.Sprite;
import graphics.Screen;
import level.Coordinate;


public abstract class Entity implements IRender{
    protected double x, y;
    protected boolean remove = false;
    protected Sprite sprite;


    // Phuong thuc  
    @Override
    public abstract void update();

    // Phuong thuc cap nhat hinh anh entity theo trang thai
    @Override
    public abstract void render(Screen screen);

    // Phuong thuc xu ly hai entity va cham nhau
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