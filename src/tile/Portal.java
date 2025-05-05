package entities.tile;

import main.Board;
import main.Game;
import entities.Entity;
import entities.character.Bomber;
import entities.tile.item.Item;
import graphics.Sprite;
import sound.Sound;

/*
 * đại diện cho cổng thoát của màn chơi
 * Khi Bomber đi đến đúng vị trí Portal và đã tiêu diệt hết quái, thì game sẽ chuyển sang màn tiếp theo
 */

public class Portal extends Tile {
    protected Board board;
	public Portal(int x, int y,Board board, Sprite sprite) {
		super(x, y, sprite);
        this.board = board; 
    }

    /*
     * xu li khi 2 entity va cham
     * true cho di qua
     * false khong cho di qua
     * xử lý khi Bomber đi vào
     */

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Bomber ) {
            if(board.detectNoEnemies() == false) return false;
            if(e.getXtile() == getX() && e.getYtile() == getY()) {
                if(board.detectNoEnemies()){
                    board.nextLevel();
                    Sound.play("CRYST_UP");
                }
            }
            return true;
        }
        return true;
    }
}
