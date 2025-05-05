package entities.tile.item;

import main.Game;
import entities.Entity;
import entities.character.Bomber;
import graphics.Sprite;
import sound.Sound;

/*
 * đại diện cho vật phẩm tăng tốc chạy của người chơi (Bomber)
 */

public class SpeedItem extends Item {
	public SpeedItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
    // xử lý Bomber ăn Item
	public boolean collide(Entity e) {
            if (e instanceof Bomber) {
                Sound.play("Item");
                Game.addBomber_speed(0.5);
                remove();
            }
        return false;
	}
}