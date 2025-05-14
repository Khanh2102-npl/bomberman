package entities.character;

import graphics.Screen;
import main.Board;
import main.Game;
import entities.AnimateEntity;

/*
 * Lớp trừu tượng đại diện cho các dối tương có thể điều khiển hoặc có hành vi động 
 * Bomer và Enemy trong game 
 */

public abstract class Character extends AnimateEntity{
    protected Board board;
    protected int direction = -1; // hướng đi hiện tại của nhân vật (-1 là đang đứng yên)
    protected boolean alive = true;
    protected boolean moving = false;
    protected int TimeAfter = 40; // thời gian đếm ngược sau khi bị tiêu diệt

    public Character(int x, int y, Board board){
        this.x = x;
        this.y = y;
        this.board = board;
    }

    @Override
    public abstract void update(); // cập nhật trạng thái đối tượng mỗi khung hình Frame 

    @Override
    public abstract void render(Screen screen); // vẽ đối tượng lên màn hình 

    protected abstract void CalculateMove(); // Tính toán hướng đi, cách di chuyển tiếp theo 

    protected abstract void Move(double xa, double ya); // di chuyển theo hướng đã tính toán 

    public abstract void Kill(); // gọi khi đối tượng bị tiêu diệt

    protected abstract void AfterKill(); // xử lý hiệu ứng bị tiêu diệtdiệt

    protected abstract boolean CanMove(double x, double y); // kiểm tra đối tượng có di chuyển đến vị trí tính toán không 


    /*
     * Phương thức bổ trợ 
     * Tính toán vị trí hiệu ứng đối tượng khi tạo ra âm thanh
     */
    protected double getXMessage(){
        return (x * Game.SCALE) + (sprite.SIZE / 2 * Game.SCALE);
    }
    protected double getYMessage(){
        return (y * Game.SCALE) - (sprite.SIZE / 2 * Game.SCALE);
    }
}
