package entities.character.enemy.ai;

import entities.bom.Bom;
import entities.character.Bomber;
import entities.character.enemy.Enemy;

public class AIMedium extends AI{
    Bomber bomer;
    Enemy e;

    public AIMedium(Bomber bomer, Enemy e){
        this.bomer = bomer;
        this.e = e;
    }

    /*
     * Cài đặt thuật toán tìm đường đi 
     */
    @Override
    public int CalculateDirection(){
        if(bomer == null) return random.nextInt(4);

        int vertical = random.nextInt(2);

        if(vertical == 1){
            int v = CalculateColDirection();
            if(v != -1){
                return v;
            }else{
                return CalculateRowDirection();
            }
        }else{
            int h = CalculateRowDirection();
            if(h != -1){
                return h;
            }else{
                return CalculateColDirection();
            }
        }
    }

    /*
     * Các phương thức bổ trợ 
     */
    // Xác định hướng di chuyển theo trục ngang(cột)
    protected int CalculateColDirection(){
        if(bomer.getXtile() < e.getXtile()){
            return 3;
        }else if(bomer.getXtile() > e.getXtile()){
            return 1;
        }
        return -1;
    }
    //Xác định hướng di chuyển theo trụ dọc(hàng)
    protected int CalculateRowDirection(){
        if(bomer.getYtile() < e.getYtile()){
            return 0;
        }else if(bomer.getYtile() > e.getYtile()){
            return 2;
        }
        return -1;
    }
}
