package entities.tile.item;

import main.Game;
import entities.Entity;
import entities.character.Bomber;
import graphics.Sprite;
import sound.Sound;

/*
 * đại diện cho vật phẩm tăng phạm vi bom nổ mà người chơi (Bomber) đặt
 */

public class FlameItem extends Item {
    private boolean used = false;
	public FlameItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
    // xử lý Bomber ăn Item
	public boolean collide(Entity e) {
        if (e instanceof Bomber && !used) {
            used = true;
            Sound.play("Item");
            Game.setBom_radius(1);
            this.remove();
            return true;
        }
        return false;
	}
}