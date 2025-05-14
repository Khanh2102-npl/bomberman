package graphics;

import main.Board;
import main.Game;
import entities.Entity;
import entities.character.Bomber;

import java.awt.*;
/*
 * Xử lí hiển thị (render) các đối tượng (Entity) và 
 * 1 số màn hình phụ (Game over, chuyển level,...)
 */
public class Screen {
    protected int width, height;
    public int[] pixels;
    private int transparentColor = 0xffff00ff;

    public static int xOffset = 0, yOffset = 0;

    public Screen(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    } 

    public void clear(){
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
    }

    // Vẽ thực thể (Entity) lên màn hình tọa độ (xp, yp) sau khi trừ offset
    public void RenderEntity(int xp, int yp, Entity entity){
        xp -= xOffset;
        yp -= yOffset;
        for(int i = 0; i < entity.getSprite().getSize(); i++){
            int ya = i + yp;
            for(int j = 0; j < entity.getSprite().getSize(); j++){
                int xa = j + xp;
                if(xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height){
                    break;
                }if(xa < 0){
                    xa = 0;
                }
                int color = entity.getSprite().getPixel(j + i * entity.getSprite().getSize());
                if(color != transparentColor){
                    pixels[xa + ya * width] = color;
                }
            }
        }
    }

    /*
     * Giống renderEntity(), nhưng thêm 1 sprite phía dưới (ví dụ: Bomber đứng trên mặt đất).
     * Nếu pixel entity là trong suốt → dùng pixel của sprite bên dưới (below) để hiển thị.
     */
    public void RenderEntityWithBelowSprite(int xp, int yp, Entity entity, Sprite below) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < entity.getSprite().getSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < entity.getSprite().getSize(); x++) {
				int xa = x + xp;
				if(xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) break; //fix black margins
				if(xa < 0) xa = 0;
				int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
				if(color != transparentColor) 
					pixels[xa + ya * width] = color;
				else
					pixels[xa + ya * width] = below.getPixel(x + y * below.getSize());
            }
			
		}
	}

    // Thay đổi vị trí camera 
    public static void SetOffset(int xO, int yO) {
		xOffset = xO;
		yOffset = yO;
	}

    public static int CalculateXOffset(Board board, Bomber bomber) {
		if(bomber == null) return 0;
		int temp = xOffset;
		
		double BomberX = bomber.getX() / 16;
		double complement = 0.5;
		int firstBreakpoint = board.getWidth() / 4;
		int lastBreakpoint = board.getWidth() - firstBreakpoint;
		
		if( BomberX > firstBreakpoint + complement && BomberX < lastBreakpoint - complement) {
			temp = (int)bomber.getX()  - (Game.WIDTH / 2);
		}
		
		return temp;
	}
    
    //Hiển thị màn hình GAME OVER và điểm số
    public void DrawEndGame(Graphics g, int points) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GetRealWidth(), GetRealHeight());
		
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.white);
		DrawCenteredString("GAME OVER", GetRealWidth(), GetRealHeight(), g);
		
		font = new Font("Arial", Font.PLAIN, 10 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.yellow);
		DrawCenteredString("POINTS: " + points, GetRealWidth(), GetRealHeight() + (Game.TILES_SIZE * 2) * Game.SCALE, g);
	}

    //Hiển thị LEVEL hiện tại khi qua màn
    public void DrawChangeLevel(Graphics g, int level) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GetRealWidth(), GetRealHeight());
		
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
		g.setFont(font);
		g.setColor(Color.white);
		DrawCenteredString("LEVEL " + level, GetRealWidth(), GetRealHeight(), g);
		
	}

    //Hiển thị điểm số, thời gian còn lại, play, exit khi game bị tạm dừng
    public void DrawPaused(Graphics g) {
		g.setColor(new Color(0, 0, 0, 150)); // nền mờ
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString("PAUSED", 150, 100);

		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Score: " + Game.POINT, 150, 150);
		g.drawString("Time left: " + Game.TIME, 150, 180);

		g.drawString("Press Enter to Resume", 150, 230);
		g.drawString("Press Esc to Exit", 150, 260);
		
	}

    /*
     * Căn giữa dòng chữ s trong hình chữ nhật có kích thước w, h
     * Tính toán theo FontMetrics để đảm bảo chữ đúng giữa về cả chiều ngang và dọc
     */
    public void DrawCenteredString(String s, int w, int h, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (w - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	 }

    public int GetWidth() {
		return width;
	}
	
	public int GetHeight() {
		return height;
	}
	
	public int GetRealWidth() {
		return width * Game.SCALE;
	}
	
	public int GetRealHeight() {
		return height * Game.SCALE;
    }
}