package entities.tile;

import entities.Entity;
import graphics.Screen;
import graphics.Sprite;
import level.Coordinate;

/*
 * đại diện cho các thực thể tĩnh (không di chuyển) trên bản đồ trong game Bomberman
 */

public abstract class Tile extends Entity{
    public Tile(int x, int y, Sprite sprite){
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    /* 
     * Mặc định không cho bất cứ một đối tượng nào đi qua
	 * @param e
	 * @return
	 */ 
	@Override
	public boolean collide(Entity e) {
		return false;//khong cho di qua
	}
	
	@Override
	public void render(Screen screen) {
		screen.RenderEntity( Coordinate.tiletopixel(x), Coordinate.tiletopixel(y), this);
	}
	
	@Override
	public void update() {}
}
