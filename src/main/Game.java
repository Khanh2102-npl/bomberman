package main;

import input.Keyboard;
import graphics.Screen;
import gui.Frame;
import main.Board;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.KeyEvent;


/**
 * Quản lý vòng lặp game, lưu trữ một vài tham số cấu hình toàn cục
 * Cập nhật trạng thái và hiển thị hình ảnh lên màn hình
 */

public class Game extends Canvas{
    public static final int TILES_SIZE = 16,
                            WIDTH = TILES_SIZE * (31 /2),
                            HEIGHT = 13 * TILES_SIZE;
    
    public static int SCALE = 3;

    public static final String TITLE = "Bomberman Game";

    protected static int SCREEN_DELAY = 3;

    protected int screen_delay = SCREEN_DELAY;

    private static final int BOM_RATE = 1;
    private static final int BOM_RADIUS = 1;
    private static final double BOMBER_SPEED = 1.0;

    public static int bom_rate = BOM_RATE;
    public static int bom_radius = BOM_RADIUS;
    public static double bomber_speed = BOMBER_SPEED;

    public static final int TIME = 300;
    public static final int POINT = 0;

    private Keyboard INPUT;
    private boolean RUN = false; // Trạng thái chạy của game
    private boolean PAUSE = true; // Trạng thía tạm dừng
    
    private Board BOARD;
    private Screen SCREEN;
    private Frame FRAME;

    private boolean isPausedByUser = false;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    /**
     * Contructor khởi tạo
     * @param frame
     */
    public Game(Frame frame){
        FRAME = frame;
        FRAME.setTitle(TITLE);

        SCREEN = new Screen(WIDTH, HEIGHT);
        INPUT = new Keyboard();

        BOARD = new Board(this, INPUT, SCREEN);
        addKeyListener(INPUT);

        setFocusable(true);                 // cần có
        requestFocusInWindow();            // yêu cầu lấy focus

    }
    /**
    * Vẽ màn hình trong quá trình chơi
     */
    private void renderGame(){
        BufferStrategy t = getBufferStrategy();
        if(t == null){
            createBufferStrategy(3);
            return;
        }

        SCREEN.clear();

        BOARD.render(SCREEN);
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = SCREEN.pixels[i];
        }

        Graphics g = t.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        BOARD.renderMessage(g);
        g.dispose();
        t.show();
    }
    
    /**
     * Vẽ màn hình khi trò chơi tạm dừng (pause = false)
     */
    private void renderScreen(){
        BufferStrategy t = getBufferStrategy();
        if(t == null){
            createBufferStrategy(3);
            return;
        }

        SCREEN.clear();

        Graphics g = t.getDrawGraphics();
        BOARD.drawScreen(g);
        g.dispose();
        t.show();
    }

    private void update(){
        INPUT.update();

        if (INPUT.enterPressed) {
            System.out.println("Enter pressed");
            if (!PAUSE) {
                PAUSE = true;
                isPausedByUser = true;
                BOARD.setScreenshow(3);
                screen_delay = -1;
            } else if (isPausedByUser && BOARD.getScreenshow() == 3) {
                PAUSE = false;
                isPausedByUser = false;
                BOARD.setScreenshow(-1);
            }
        }

        BOARD.update();
    }


    public void start() {
        RUN = true;
        new Thread(() -> {
            long LastTime = System.nanoTime();
            long timer = System.currentTimeMillis();
            final double ns = 1000000000.0 / 60.0;
            double delta = 0;
            int frames = 0;
            int updates = 0;

            requestFocusInWindow();

            while (RUN) {
                long now = System.nanoTime();
                delta += (now - LastTime) / ns;
                LastTime = now;

                while (delta >= 1) {
                    update();
                    updates++;
                    delta--;
                }

                if (PAUSE) {
                    if (screen_delay <= 0 && !isPausedByUser) {
                        BOARD.setScreenshow(-1);
                        PAUSE = false;
                    }
                    renderScreen();
                } else {
                    renderGame();
                }

                frames++;

                if (System.currentTimeMillis() - timer > 1000) {
                    FRAME.setTime(BOARD.subtractTime());
                    FRAME.setPoint(BOARD.getPoint());
                    timer += 1000;

                    FRAME.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
                    updates = 0;
                    frames = 0;
                    if (BOARD.getScreenshow() == 2) {
                        screen_delay--;
                    }
                }
            }
        }).start(); // chạy trong thread mới
    }


    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();  // Bắt sự kiện bàn phím khi được thêm vào Frame
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
    }

    public static int getBom_rate(){
        return bom_rate;
    }
    public static int getBom_radius(){
        return bom_radius;
    }
    public static double getBomber_speed(){
        return bomber_speed;
    }
    
    public static void setBom_rate(int i){
        Game.bom_rate += i;
        System.out.println("So luong bom co the dat: " + Game.getBom_rate());
    }
    public static void setBom_radius(int i){
        Game.bom_radius += i;
    }
    public static void setBomber_speed(double i){
        Game.bomber_speed += i;
    }
    
    public void resetScreen_delay(){
        screen_delay = SCREEN_DELAY;
    }
    public Board getBoard(){
        return BOARD;
    }
    public boolean isPause(){
        return PAUSE;
    }
    public void resetPause(){
        PAUSE = true;
    }
   
    public static void addBom_rate(int bom_rate){
        Game.bom_rate = bom_rate;
    }
    public static void addBom_radius(int bom_radius){
        Game.bom_radius = bom_radius;
    }
    public static void addBomber_speed(double bomber_speed){
        Game.bomber_speed = bomber_speed;
    }
}