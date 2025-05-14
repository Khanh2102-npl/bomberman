package entities;

import java.awt.Color;
import graphics.Screen;

/**
 * Phương thức hiển thị thông báo
 */

public class Message extends Entity{
    protected String message;    // Nội dung thông báo
    protected int duration;      // Thời gian hiển thị
    protected Color color;       // Màu sắc
    protected int size;          // Kích thước

    public Message(double x, double y, String message, int duration, Color color, int size){
        this.x = x;
        this.y = y;
        this.message = message;
        this.duration = duration;
        this.color = color;
        this.size = size;
    }

    public int getDuration(){
        return this.duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public String getMessage(){
        return this.message;
    }

    public Color getColor(){
        return this.color;
    }

    public int getSize(){
        return this.size;
    }

    @Override
    public void update(){
        if(duration > 0){
            duration--;
        }
    }

    @Override
    public void render(Screen screen){
    }

    @Override
    public boolean collide(Entity z){
        return true;
    }
}
