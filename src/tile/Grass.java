package entities.tile;

import entities.Entity;
import graphics.Sprite;

/*
 * Đại diện cho ô cỏ (Grass tile) trong game Bomberman 
 * Đây là một ô nền, cho phép các đối tượng khác đi xuyên qua
 */
public class Grass extends Tile{
    public Grass(int x, int y, Sprite sprite){
        super(x, y, sprite);
    }

    /**
	 * Cho bất kì đối tượng khác đi qua
	 * @param e
	 * @return
	 */
    @Override
    public boolean collide(Entity e){
        return true;
    }
    
}
