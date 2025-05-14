package entities.character.enemy;


import java.util.Random;
import main.Board;
import main.Game;
import entities.character.enemy.ai.AILow;
import entities.character.enemy.ai.AIMedium;
import graphics.Sprite;

public class Oneal extends Enemy {
	//private Random random = new Random();
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, 0.8 , 100);
		
		sprite = Sprite.oneal_left1;
		
		ai = new AILow();
		direction = ai.CalculateDirection();
                //this._speed += random.nextDouble()/2;
                
	}
	
	@Override
	protected void ChooseSprite() {
		switch(direction) {
			case 0:
			case 1:
				if(moving)
					sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 60);
				else
					sprite = Sprite.oneal_left1;
				break;
			case 2:
			case 3:
				if(moving)
					sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 60);
				else
					sprite = Sprite.oneal_left1;
				break;
		}
	}
}