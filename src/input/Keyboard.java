package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Cập nhật và lưu trữ trạng thái các input từ bàn phím
 * Xử lý các input đã được lưu 
 */

public class Keyboard implements KeyListener{
    private boolean[] keys = new boolean[150];
    public boolean up, down, left, right, space;

    public void update(){
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_U];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        space = keys[KeyEvent.VK_SPACE];
    }

    @Override
    public void keyTyped(KeyEvent t){
    }

    @Override
    public void keyPressed(KeyEvent t){
        keys[t.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent t){
        keys[t.getKeyCode()] = false;
    }
}