package main;

import gui.Frame;
import sound.Sound;

public class BombermanGame {
    public static void main(String[] args) {
        Sound.play("soundtrack");
        new Frame(); // chỉ khởi tạo frame, chưa chạy game
    }
}
