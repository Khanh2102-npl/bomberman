package entities.character.enemy;

import main.Board;
import main.Game;
import entities.Entity;
import entities.Message;
import entities.bom.Flame;
import entities.character.Bomber;
import entities.character.Character;
import entities.character.enemy.ai.AI;
import graphics.Screen;
import graphics.Sprite;
import level.Coordinate;
import sound.Sound;

import java.awt.*;

/*
 * Lớp trừu tượng đại diện cho các Enemy-quái vật trong trò chơi
 * Quản lý các hành vi của kẻ địch: di chuyển, va chạm, chết, hoạt ảnh,...
 */
public abstract class Enemy extends Character{
    protected int points;
    protected double speed;
    protected AI ai;

    protected final double MAX_STEPS; // tổng số bước đi trong 1 ô
    protected final double rest; // phân du để tránh rung khi di chuyển 
    protected double steps; // số bước còn lại 

    protected int finalAnimation = 30;
    protected Sprite deadSprite;

    // khởi tạo Enemy tại tọa độ (x,y) trên Board, với Sprite khi chết, tốc đọ và điểm số
    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points){
        super(x, y, board);
        this.points = points;
        this.speed = speed;

        MAX_STEPS = Game.TILES_SIZE / speed;
        rest = (MAX_STEPS - (int)MAX_STEPS)/MAX_STEPS;
        steps = MAX_STEPS;

        TimeAfter = 20;
        deadSprite = dead;
    } 

    @Override
    public void update(){
        Animate();

        if(!alive){
            AfterKill();
            return;
        }else{
            CalculateMove();
        }
    }

    @Override
    public void render(Screen Screen){
        if(alive){
            ChooseSprite();
        }else{
            if(TimeAfter > 0){
                sprite = deadSprite;
                animate = 0;
            }else{
                sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60);
            }
        }
        Screen.RenderEntity((int)x,(int)y-sprite.SIZE, this);
    }

    @Override
    /*
     * Tính toán hướng di chuyển cho Enemy theo ai và cập nhật giá trị cho direction 
     * Sử dụng CanMove() kiểm tra xem có thể di chuyển đến điểm đã tính toán không
     * Sử dụng Move() để di chuyển 
     * Cập nhật lại giá trị moving khi thay đổi trạng thái di chuyển 
     */
    public void CalculateMove() {
        int xa = 0, ya = 0;

        if (steps <= 0) {
            direction = ai.CalculateDirection();
            steps = MAX_STEPS;  // reset lại số bước
        }

        // xác định hướng di chuyển
        switch (direction) {
            case 0: ya = -1; break; // lên
            case 1: xa = 1; break;  // phải
            case 2: ya = 1; break;  // xuống
            case 3: xa = -1; break; // trái
        }

        // kiểm tra và thực hiện di chuyển
        if (CanMove(xa * speed, ya * speed)) {
            steps -= 1 + rest;
            Move(xa * speed, ya * speed);
            moving = true;
        } else {
            steps = 0; // cho chọn hướng mới
            moving = false;
        }
    }

    @Override
    public void Move(double xa, double ya){
        if(!alive) return;
        x += xa; 
        y += ya;
    }

    @Override
    public boolean CanMove(double xa, double ya) {
        double xr = x + xa;
        double yr = y + ya;

        for (int c = 0; c < 4; c++) {
            double xt = (xr + c % 2 * 11) / Game.TILES_SIZE;
            double yt = (yr + c / 2 * 13 - 13) / Game.TILES_SIZE;

            Entity entity = board.getEntity(xt, yt, this);
            if (!entity.collide(this)) {
                return false;
            }
        }
        return true;
    }



    
    @Override
    public boolean collide(Entity e){
        if(e instanceof Flame){
            this.Kill();
            return false;
        }
        if(e instanceof Bomber){
            ((Bomber) e).Kill();
            return false;
        }
        return true;
    }

    @Override
    public void Kill(){
        if(!alive) return;
        alive = false;

        board.addPoint(points);

        Message msg = new Message(getXMessage(), getYMessage(),"+" + points, 2, Color.white, 14);
        board.addMessage(msg);
        Sound.play("AA126_11");
    }

    @Override
    protected void AfterKill(){
        if(TimeAfter > 0){
            --TimeAfter;
        }else{
            if(finalAnimation > 0){
                --finalAnimation;
            }else{
                remove();
            }
        }
    }

    protected abstract void ChooseSprite();

}

