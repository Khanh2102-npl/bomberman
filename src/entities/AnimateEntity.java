package entities;

/**
 * Lop tao hieu ung hoat hinh
 */

public abstract class AnimateEntity extends Entity{
    protected int animate = 0;

    protected void Animate(){
        if(animate < 7500){ // Tranh tran so
            animate++; 
        }else{
            animate = 0;
        }
    }
}