package entities.tile.destroyable;

import entities.Entity;
import entities.bom.Flame;
import entities.tile.Tile;
import graphics.Sprite;

/*
 * đại diện cho một ô tile cố định trên bản đồ có thể bị phá hủy (như gạch hoặc tường gỗ)
 */

public class DestroyableTile extends Tile {
    private final int MAX_ANIMATE = 7500;
	private int animate = 0;
	protected boolean destroyed = false;
	protected int timeToDisapear = 20;
	protected Sprite belowSprite = Sprite.grass;
    
    public DestroyableTile(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

    @Override
	public void update() {
		if(destroyed) {
			if(animate < MAX_ANIMATE){ 
                animate++; 
            }else{
                animate = 0;
            }
			if(timeToDisapear > 0){
				timeToDisapear--;
            }else{
				remove();
            }
		}
	}

    public void destroy() {
		destroyed = true;
	}

    @Override
    // xử lý khi va chạm với Flame
	public boolean collide(Entity e) {
                if(e instanceof Flame) destroy();
		return false;
	}
	
	public void AddBelowSprite(Sprite sprite) {
		belowSprite = sprite;
	}

	protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
		int calc = animate % 30;
		if(calc < 10) {
			return normal;
		}
	
		if(calc < 20) {
			return x1;
		}	
		return x2;
	}
}