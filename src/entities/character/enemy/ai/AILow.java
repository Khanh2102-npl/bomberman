package entities.character.enemy.ai;

public class AILow extends AI{

    @Override
    public int CalculateDirection(){
        return random.nextInt(4);
    }
}