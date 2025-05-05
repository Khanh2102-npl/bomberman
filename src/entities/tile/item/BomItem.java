package entities.tile.item;

import main.Game;
import entities.Entity;
import entities.character.Bomber;
import graphics.Sprite;
import sound.Sound;

/*
 * đại diện cho vật phẩm tăng số lượng bom mà người chơi (Bomber) có thể đặt cùng lúc
 */
public class BomItem extends Item{

    public BomItem(int x, int y, Sprite sprite){
        super(x, y, sprite);
    }

    @Override
    // xử lý Bomber ăn Item
	public boolean collide(Entity e) {
            if (e instanceof Bomber) {   
                Sound.play("Item");
                Game.addBom_rate(1);
                remove();
            }
        return false;
	}
}