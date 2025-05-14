package entities.bom;

import entities.Entity;
import entities.AnimateEntity;
import entities.character.Bomber;
import entities.character.Character;
import main.Board;
import main.Game;
import graphics.Screen;
import graphics.Sprite;
import level.Coordinate;
import sound.Sound;

public class Bom extends AnimateEntity{
    protected double TimeToExplode = 120; // thời gian chờ trước khi nổ 2s 
    public int TimeAfter = 20; // thời gian tồn tại sau khi nổ 
    protected Board board;
    protected Flame[] flames;
    protected FlameSegment centerFlame;
    protected boolean exploded = false;
    protected boolean allowed = true;

    public Bom(int x, int y, Board board){
        this.x = x;
        this.y = y;
        this.board = board;
        sprite = Sprite.bomb;
    }

    @Override
    public void update(){
        if(TimeToExplode > 0){
            TimeToExplode--;
        }else{
            if(!exploded){
                explode();   
            }else{
                updateFlames();
            }

            if(TimeAfter > 0){
                TimeAfter--;
            }else{
                remove();
            }
        }
        Animate();
    }

    @Override
    public void render(Screen screen){
        if(exploded){
            sprite = Sprite.bomb_exploded2;
            renderFlames(screen);
        }else{
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60);

            int xt = (int) x << 4;
            int yt = (int) y << 4;

            screen.RenderEntity(xt, yt, this);
        }
    }

    public void renderFlames(Screen screen){
        if(centerFlame != null) centerFlame.render(screen);
        for(int i = 0; i < flames.length; i++){
            flames[i].render(screen);
        }
    }

    public void updateFlames(){
        for(int i = 0; i < flames.length; i++){
            flames[i].update();
        }
    }

    /*
     * Xử lý bom nổ
     */
    protected void explode(){
        exploded = true;
        allowed = true;
        //Xử lý khi các Character đứng tại vị trí Bom 
        Character str = board.getCharacterAt((int)x, (int)y, null);
        if(str != null){
            str.Kill();
        }
        //Tạo lửa flame 
        flames = new Flame[4];
        for(int i = 0; i < flames.length; i++){
            flames[i] = new Flame((int) x, (int) y, i, Game.getBom_radius(), board);
        }

        // Tạo flame ở giữa (vị trí bom nổ)
        centerFlame = new FlameSegment((int) x, (int) y, -1, false); // -1 để chỉ hướng trung tâm
        
        Sound.play("BOM_11_M");
        Game.setBom_rate(1);
        System.out.println("So luong bom co the dat sau khi reset " + Game.getBom_rate());
    }

    public void Time_explode(){
        TimeToExplode = 0;
    }
    
    public FlameSegment flameAt(int x, int y){
        if(!exploded) return null;

        for(int i = 0; i < flames.length; i++){
            if(flames[i] == null) return null;
            FlameSegment e = flames[i].flameSegmentAt(x, y);
            if(e != null) return e;
        }
        return null;
    }

    @Override
    public boolean collide(Entity e){
        // Xử lý khi Bomber di ra sau khi vừa đặt bom 
        if(e instanceof Bomber){
            double diffX = e.getX() - Coordinate.tiletopixel(getX());
            double diffY = e.getY() - Coordinate.tiletopixel(getY());

            if(!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)){
                allowed = false;
            }
            return allowed;
        }
        //Xử lý va chạm flame với flame của bom khác
        if(e instanceof Flame){
            Time_explode();
            return true;
        }
        return false;
    }
}