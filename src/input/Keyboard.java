package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Cập nhật và lưu trữ trạng thái các input từ bàn phím
 * Xử lý các input đã được lưu 
 */

public class Keyboard implements KeyListener{
    private boolean[] keys = new boolean[256];
    public boolean up, down, left, right, space;
    public boolean enter;
    public boolean enterPressed = false;


    public void update(){
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_U];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        space = keys[KeyEvent.VK_SPACE];

        // Bắt đầu xử lý phím Enter
        if (!enter && keys[KeyEvent.VK_ENTER]) {
            enterPressed = true;
        } else {
            enterPressed = false;
        }
        
        enter = keys[KeyEvent.VK_ENTER];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code >= 0 && code < keys.length) {
            keys[code] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code >= 0 && code < keys.length) {
            keys[code] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void releaseKey(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = false;
        }
    }
}