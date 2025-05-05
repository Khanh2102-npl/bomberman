package entities.tile.destroyable;

import graphics.Screen;
import graphics.Sprite;
import level.Coordinate;

public class Brick extends DestroyableTile{

    public Brick(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

    @Override
	public void update() {
		super.update();
	}

    @Override
	public void render(Screen screen) {
		int xx = Coordinate.tiletopixel(x);
		int yy = Coordinate.tiletopixel(y);
		
		if(destroyed) {
			sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
			
			screen.RenderEntityWithBelowSprite(xx, yy, this, belowSprite);
		}
		else
			screen.RenderEntity( xx, yy, this);
	}
	

}