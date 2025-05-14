package entities.character.enemy;

import main.Board;
import entities.character.enemy.ai.AILow;
import entities.character.enemy.ai.AIMedium;
import graphics.Sprite;

public class Doll extends Enemy{
    public Doll(int x, int y, Board board){
        super(x, y, board, Sprite.doll_dead, 0.8, 100);

        sprite = Sprite.doll_left1;

        ai = new AIMedium(board.getBomber(), this);

        direction = ai.CalculateDirection();
    }

    @Override
    protected void ChooseSprite() {
        switch (direction) {
            case 0:
            case 1:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 60);
                } else {
                    sprite = Sprite.doll_left1;
                }
                break;
            case 2:
            case 3:
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 60);
                } else {
                    sprite = Sprite.doll_left1;
                }
                break;
        }
    }
    
}
