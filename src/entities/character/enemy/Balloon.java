package entities.character.enemy;

import main.Board;
import main.Game;
import entities.character.enemy.ai.AI;
import entities.character.enemy.ai.AILow;
import graphics.Screen;
import graphics.Sprite;

import static java.lang.Math.random;
import java.util.Random;

public class Balloon extends Enemy{
    public Balloon(int x, int y, Board board){
        super(x, y, board, Sprite.balloom_dead, 0.5, 100);
        sprite = Sprite.balloom_left1;
        ai = new AILow();
        direction = ai.CalculateDirection();
    }

    @Override
    protected void ChooseSprite(){
        switch (direction) {
            case 0:
            case 1:
                sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2,Sprite.balloom_right3, animate, 60);
                break;
            case 2:
            case 3:
                sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2,Sprite.balloom_left3, animate, 60);
                break;
        }
    }
    
}