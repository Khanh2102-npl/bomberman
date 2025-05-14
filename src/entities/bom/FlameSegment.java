package entities.bom;

import entities.Entity;
import entities.character.Bomber;
import entities.character.enemy.Enemy;
import graphics.Screen;
import graphics.Sprite;


/*
 * Mô tả một đoạn ngọn lửa (flame segment) sinh ra từ quả bom khi nó phát nổ
 * Xử lý việc hiển thị và va chạm với các đối tượng khác trong game
 */
public class FlameSegment extends Entity{
    protected boolean last; // cho biết segment này có phải là đoạn cuối cùng của ngọn lửa không


    public FlameSegment(int x, int y, int direction, boolean last) {
		this.x = x;
		this.y = y;
		this.last = last;

		
		switch (direction) {
			case 0:
				if(!last) {
					sprite = Sprite.explosion_vertical2;
				} else {
					sprite = Sprite.explosion_vertical_top_last2;
				}
				break;
			case 1:
				if(!last) {
					sprite = Sprite.explosion_horizontal2;
				} else {
					sprite = Sprite.explosion_horizontal_right_last2;
				}
				break;
			case 2:
				if(!last) {
					sprite = Sprite.explosion_vertical2;
				} else {
					sprite = Sprite.explosion_vertical_down_last2;
				}
				break;
			case 3: 
				if(!last) {
					sprite = Sprite.explosion_horizontal2;
				} else {
					sprite = Sprite.explosion_horizontal_left_last2;
				}
				break;
			case -1:
            	sprite = Sprite.bomb_exploded2; 
            	break;
		}
	}

    @Override
    public void render(Screen screen){
        int xt = (int)x << 4;
        int yt = (int)y << 4;

        screen.RenderEntity(xt, yt, this);
    }

    @Override
    public void update(){}

    @Override
	// xử lý khi FlameSegment va chạm với Character
	public boolean collide(Entity e) {
            if(e instanceof Bomber) ((Bomber) e).Kill();
            if(e instanceof Enemy) ((Enemy) e).Kill();
		return true;
	}

}