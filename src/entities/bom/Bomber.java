package entities.character;

import main.Game;
import main.Board;
import entities.Entity;
import entities.bom.Bom;
import entities.LayerEntity;
import entities.bom.Flame;
import entities.character.enemy.Enemy;
import entities.tile.item.Item;
import graphics.Screen;
import graphics.Sprite;
import input.Keyboard;
import level.Coordinate;
import sound.Sound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomber extends Character{
    private List<Bom> boms;
    protected Keyboard input;
    public static List<Item> items = new ArrayList<Item>();
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bom tiếp theo,
     * cứ mỗi lần đặt 1 Bom mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int timeBetweenPutBoms = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        this.boms = board.getBoms();
        input = board.getInput();
        this.sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        ClearBoms();
        if (!alive) {
            AfterKill();
            return;
        }

        if (timeBetweenPutBoms < -7500){
            timeBetweenPutBoms = 0;
        }else{ 
            timeBetweenPutBoms--;
        }
        Animate();

        CalculateMove();

        DetectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        CalculateXOffset();

        if (alive)
            ChooseSprite();
        else
            sprite = Sprite.player_dead1;

        screen.RenderEntity((int) x, (int) y - sprite.SIZE, this);
    }

    public void CalculateXOffset() {
        int xScroll = Screen.CalculateXOffset(board, this);
        Screen.SetOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     * kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
     * Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
     * timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
     * nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
     * sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
     */
    private void DetectPlaceBomb() {
        
        if(input.space && Game.getBom_rate() > 0 && timeBetweenPutBoms < 0) {
			
			int xt = Coordinate.pixeltotile(x + sprite.getSize() / 2);
			int yt = Coordinate.pixeltotile( (y + sprite.getSize() / 2) - sprite.getSize() ); 
			
			PlaceBomb(xt,yt);
			Game.setBom_rate(-1);
			
            timeBetweenPutBoms = 30;
			
		}
    }

    //thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
    protected void PlaceBomb(int x, int y) {
        
        Bom b = new Bom(x, y, board);
	    board.addBom(b);
        Sound.play("BOM_SET");
    }

    private void ClearBoms(){
        boms.removeIf(b -> {
            if (b.isRemove()) {
                Game.addBom_rate(1);
                return true;
            }
            return false;
        });
    }

    @Override
    public void Kill() {
        if (!alive){ 
            return;
        }
        alive = false;
        Sound.play("endgame3");
    }

    @Override
    protected void AfterKill() {
        if (TimeAfter > 0) --TimeAfter;
        else {
            board.endGame();
        }
    }

    @Override
    /*
     * xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
     * nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
     */ 
    protected void CalculateMove() {
        int xa = 0, ya = 0;
		if(input.up) ya--;
		if(input.down) ya++;
		if(input.left) xa--;
		if(input.right) xa++;
		
		if(xa != 0 || ya != 0)  {
			Move(xa * Game.getBomber_speed(), ya * Game.getBomber_speed());
			moving = true;
		} else {
			moving = false;
		}
    }

    @Override
     //kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
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
    // sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
    // nhớ cập nhật giá trị _direction sau khi di chuyển
    public void Move(double xa, double ya) {
        if(xa > 0) direction = 1;
		if(xa < 0) direction = 3;
		if(ya > 0) direction = 2;
		if(ya < 0) direction = 0;
		
		if(CanMove(0, ya)) { 
			y += ya;
		}
		
		if(CanMove(xa, 0)) {
			x += xa;
		}
    }
    @Override
    /*  
     * xử lý va chạm với Flame
     * xử lý va chạm với Enemy
    */
    public boolean collide(Entity e) {
        if(e instanceof Flame){
            this.Kill();
            return false;
        }
        if(e instanceof Enemy){
            this.Kill();
            return true;
        }
        if( e instanceof LayerEntity) return(e.collide(this));
        return true;
    }

    private void ChooseSprite() {
        switch (direction) {
            case 0:
                sprite = Sprite.player_up;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                }
                break;
            case 1:
                sprite = Sprite.player_right;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
            case 2:
                sprite = Sprite.player_down;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                }
                break;
            case 3:
                sprite = Sprite.player_left;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                }
                break;
            default:
                sprite = Sprite.player_right;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
        }
    }
}