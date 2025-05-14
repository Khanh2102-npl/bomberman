package entities.bom;

import main.Board;
import main.Game;
import entities.Entity;
import entities.character.Bomber;
import entities.character.enemy.Enemy;
import graphics.Screen;

/*
 * Đại diện cho tia lửa do bom nổ tạo ra
 * Mô phỏng quá trình lửa lan do bom nổ 
 */
public class Flame extends Entity{
    protected Board board;
    protected int direction; // hướng lan (0: trên, 1: phải, 2: dưới, 3: trái)
    private int radius;
    protected int xOrigin, yOrigin; // tọa độ bom nổ 
    protected FlameSegment[] flameSegments = new FlameSegment[0]; // mảng chứa các đoạn của tia lửa 

    public Flame(int x, int y, int direction, int radius, Board board){
        this.xOrigin = x;
        this.yOrigin = y;
        this.direction = direction;
        this.radius = Game.getBom_radius();
        this.board = board;
        CreateFlameSegments();
    }

    /*
     * Tạo mảng FlameSegment[] từ vị trí gốc, theo hướng chỉ định
     * Dừng lại khi đạt đến khoảng cách cho phép (bị chặn hoặc hết bán kính)
     */
    private void CreateFlameSegments(){
        int length = CalculatePermitedDistance();

        if (length < 0) length = 0; // bảo vệ

        flameSegments = new FlameSegment[length];

        boolean last = false;
        int xx = xOrigin;
        int yy = yOrigin;

        for (int i = 0; i < flameSegments.length; i++) {
            last = (i == flameSegments.length - 1);

            switch (direction) {
                case 0: yy--; break;
                case 1: xx++; break;
                case 2: yy++; break;
                case 3: xx--; break;
            }

            flameSegments[i] = new FlameSegment(xx, yy, direction, last);
        }
    }

    /**
	 * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
	 * @return
	 */
    private int CalculatePermitedDistance(){
        int radi = 0;
        int xx = xOrigin; // dùng lại tọa độ gốc
        int yy = yOrigin;

        while (radi < radius) {
            switch (direction) {
                case 0: yy--; break; // lên
                case 1: xx++; break; // phải
                case 2: yy++; break; // xuống
                case 3: xx--; break; // trái
            }

            // Nếu ra ngoài biên map, dừng lại
            if (xx < 0 || yy < 0 || xx >= board.getWidth() || yy >= board.getHeight()) {
                break;
            }

            Entity a = board.getEntity(xx, yy, null);

            // Nếu không thể đi xuyên qua, dừng
            if (a == null || !a.collide(this)) {
                break;
            }

            radi++; // chỉ tăng khi có thể lan tiếp
        }

        return radi;
    }

    public FlameSegment flameSegmentAt(int x, int y){
        for(int i = 0; i < flameSegments.length; i++){
            if(flameSegments[i].getX() == x && flameSegments[i].getY() == y){
                return flameSegments[i];
            }
        }
        return null;
    }

    @Override
    public void update(){}

    @Override
    public void render(Screen screen){
        for(int i = 0; i < flameSegments.length; i++){
            flameSegments[i].render(screen);
        }
    }

    @Override
    /*
     * xử lí va chạm với bomer hoặc enemy thì gọi Kill() của đối tướng đó
     */
    public boolean collide(Entity e){
        if(e instanceof Bomber) ((Bomber) e).Kill();
        if(e instanceof Enemy) ((Enemy) e).Kill();
        return true;
    }
}