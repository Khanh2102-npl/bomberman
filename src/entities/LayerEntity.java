package entities;

import graphics.Screen;
import entities.tile.destroyable.DestroyableTile;

import java.util.LinkedList;

/**
 * Xu ly nhieu entity tai cung 1 vi tri
 */
public class LayerEntity extends Entity{
    protected LinkedList<Entity> entities = new LinkedList<>();

    // Danh sach luu cac Entity tai vi tri (a,b), luu tu duoi len
    public LayerEntity(int a, int b, Entity ... entity){
        x = a;
        y = b;

        for(int i = 0; i < entity.length; i++){
            entities.add(entity[i]);

            if(i > 1){
                if(entity[i] instanceof DestroyableTile){
                    ((DestroyableTile)entity[i]).AddBelowSprite(entity[i-1].getSprite());
                }
            }
        }
    }

    // Cap nhat Entity moi nhat
    @Override
    public void update(){
        cleanRemove();
        for (Entity e : entities) {
            e.update();
        }
    }
    public void cleanRemove(){
        LinkedList<Entity> stillActive = new LinkedList<>();
        for (Entity e : entities) {
            if (!e.isRemove()) {
             stillActive.add(e);
            }
        }
    entities = stillActive;
    }
    public Entity getTopEntity(){
        return entities.getLast();
    }

    // Ve Entity moi nhat
    @Override
    public void render(Screen screen){
        getTopEntity().render(screen);
    }

    // Lay Entity moi nhat de xu ly va cham
    @Override
    public boolean collide(Entity z){
        return getTopEntity().collide(z);
    }
}
