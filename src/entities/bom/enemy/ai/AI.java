package entities.character.enemy.ai;

import java.util.Random;

public abstract class AI{
    protected Random random = new Random();

    /*
     * Thuật toán tìm đường đi
     * Trả về hướng đi xuống/phải/trái/lên tương ứng với các giá trị 0/1/2/3
     */
    public abstract int CalculateDirection();
}
