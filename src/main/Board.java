package main;

import entities.Entity;
import entities.bom.Bom;
import entities.bom.FlameSegment;
import entities.Message;
import entities.character.Character;
import entities.character.Bomber;
import entities.character.enemy.Doll;
import entities.character.enemy.Oneal;
import graphics.Screen;
import graphics.IRender;
import input.Keyboard;
import level.LevelLoader;
import level.FilelevelLoader;
import exceptions.Loadlevelexception;
import main.Game;

import java.awt.*;
import java.awt.image.BaseMultiResolutionImage;
import java.lang.System.Logger.Level;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board implements IRender {
    protected LevelLoader levelLoader;  
    protected Game game;
    
    protected Keyboard input;
    protected Screen screen;

    public Entity[] etities;
    public List<Character> characters = new ArrayList<>();
    public List<Bom> boms = new ArrayList<>();
    public List<Message> messages = new ArrayList<>();

    private int screenshow = -1;
    
    
    private int time = Game.TIME;
    private int point = Game.POINT;

    private int currentLevel = 1;

    public Board(Game GAME, Keyboard INPUT, Screen SCREEN){
        game = GAME;
        input = INPUT;
        screen = SCREEN;

        loadLevel(1);
    }

    // Cập nhật trạng thái game 
    @Override
    public void update(){

        if (game.isPause()) return;

        updateEntities(); 
        updateBombs();  
        updateCharacter();
        updateMessage();
        detectEndGame();

        for(int i = 0; i < characters.size(); i++){
            if(characters.get(i).isRemove()){
                characters.remove(i);
            }
        }

        if (detectNoEnemies()) {
            nextLevel();
        }
    }

    // Hiển thị lên màn hình
    @Override
    public void render(Screen screen){
        if(!game.isPause()) {
            int x1 = Screen.xOffset >> 4;
            int x2 = (Screen.xOffset + screen.GetWidth() + Game.TILES_SIZE) / Game.TILES_SIZE;
            int y1 = Screen.yOffset >> 4;
            int y2 = (Screen.yOffset + screen.GetHeight()) / Game.TILES_SIZE;

            for(int i = y1; i < y2; i++){
                for(int j = x1; j < x2; j++){
                    etities[j + i * levelLoader.getWidth()].render(screen);
                }
            }

            renderBomb(screen);
            renderCharacter(screen);
        }
    }


    // Chuyển level
    public void nextLevel(){
        Game.addBom_radius(1);
        Game.addBom_rate(1);
        Game.addBomber_speed(1.0);
        currentLevel++;
        loadLevel(currentLevel);
        System.out.println(">>> Chuyển sang màn " + currentLevel);  
    }

    // Tải level
    public void loadLevel(int i){
        time = Game.TIME;
        screenshow = 2;
        game.resetScreen_delay();
        game.resetPause();
        characters.clear();
        boms.clear();
        messages.clear();

        try {
            levelLoader = new FilelevelLoader(this, i);
            etities = new Entity[levelLoader.getHeight() * levelLoader.getWidth()];
            levelLoader.createEntities();
        } catch (Loadlevelexception e) {
            endGame();
        }
        System.out.println("Đang load level " + i);
    }

    // Kiểm tre điều kiện kết thúc game 
    public void detectEndGame(){
        if(time <= 0){
            endGame();
        }
    }

    // Kết thúc game
    public void endGame(){
        screenshow = 1;
        game.resetScreen_delay();
        game.resetPause();
    }

    // Kiểm tra nếu không có quái vật
    public boolean detectNoEnemies(){
        int t = 0;
        for(int i = 0; i < characters.size(); i++){ 
            if(characters.get(i) instanceof Bomber == false){
                t++;
            }
        }
        return t == 0;
    }

    // Vẽ màn hình tùy theo trạng thái
    public void drawScreen(Graphics g){
        switch (screenshow) {
            case 1:
                screen.DrawEndGame(g, point);   // Ket thuc tro choi
                break;
            case 2:
                screen.DrawChangeLevel(g, levelLoader.getLevel());    // level tiep theo
                break;
            case 3:
                screen.DrawPaused(g);   // Tam dung
                break;
            default:
                break;
        }
    }

    // Quản lý bom
    public List<Bom> getBoms(){
        return boms;
    }
    public Bom getBomAt(double x, double y){
        Iterator<Bom> t = boms.iterator();

        while (t.hasNext()) {
            Bom b = t.next();
            if(b.getX() == (int) x && b.getY() == (int) y) return b;
        }
        return null;    
    }

    // Quan ly nhan vat
    public Character getCharacterAt(int x, int y, Character z){
        Iterator<Character> t = characters.iterator();

        while(t.hasNext()){
            Character b = t.next();
            if(b == z){
                continue;
            }
            if(b.getXtile() == x && b.getYtile() == y){
                return b;
            }
        }
        return null;
    }

    // Lấy phần ngọn lửa khi bom nổ
    public FlameSegment getFlameSegmentAt(int x, int y){
        Iterator<Bom> t = boms.iterator();
        
        while (t.hasNext()) {
            Bom b = t.next();
            FlameSegment c = b.flameAt(x, y);
            if(c != null){
                return c;
            }
        }
        return null;
    }

    // Lấy thực thể tại vị trí (x,y)
    public Entity getEntityAt(double x, double y){
        int ix = (int)x;
        int iy = (int)y;

        // Kiểm tra x, y nằm trong bản đồ
        if (ix < 0 || iy < 0 || ix >= levelLoader.getWidth() || iy >= levelLoader.getHeight()) {
            return null; // hoặc trả về một đối tượng Entity mặc định
        }

        return etities[ix + iy * levelLoader.getWidth()];
    }

    // Hiển thị thực thể vị trí (x,y)
    public Entity getEntity(double x, double y, Character z){
        Entity t = null;
        
        t = getFlameSegmentAt((int) x, (int) y); 
        if(t != null) return t;

        t = getBomAt(x, y);
        if(t != null) return t;

        t = getCharacterAt((int)x, (int)y, z);
        if(t != null) return t;

        t = getEntityAt(x, y);
        return t;
        
    }

    // Tìm kiếm và trả về bomber
    public Bomber getBomber(){
        Iterator<Character> t = characters.iterator();

        Character b;
        while (t.hasNext()) {
            b = t.next();
            
            if(b instanceof Bomber){
                return (Bomber)b;
            }
        }
        return null;
    }


    // Thêm cái đối tượng entity, character, bom và message
    public void addEntity(int t, Entity b){
        etities[t] = b;
    } 
    public void addCharacter(Character t){
        characters.add(t);
    }
    public void addBom(Bom t){
        boms.add(t);
    }
    public void addMessage(Message t){
        messages.add(t);
    }


    // Vẽ các đối tượng character, bom và message
    protected void renderCharacter(Screen screen){
        Iterator<Character> t = characters.iterator();
        
        while (t.hasNext()) {
            t.next().render(screen);
        }
    }
    protected void renderBomb(Screen screen){
        Iterator<Bom> t = boms.iterator();

        while (t.hasNext()) {
            t.next().render(screen);
        }
    }
    protected void renderMessage(Graphics g){
        Message m;
        for(int i = 0; i < messages.size(); i++){
            m = messages.get(i);
            g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
            g.setColor(m.getColor());
            g.drawString(m.getMessage(),(int)m.getX() - Screen.xOffset * Game.SCALE, (int)m.getY());
        }
    }

    // Cập nhật các đối tượng entity, bom, character, message
    protected void updateEntities(){
        if(game.isPause()){
            return;
        }
        for(int i = 0; i < etities.length; i++){
            if (etities[i] != null) {
                etities[i].update(); // Chỉ gọi update khi không null
            }
        }
    }
    protected void updateBombs(){
        if(game.isPause()){
            return;
        }
        Iterator<Bom> str = boms.iterator();
        while (str.hasNext()) {
            str.next().update();
        }
    }
    protected void updateCharacter(){
        if(game.isPause()){
            return;
        }
        Iterator<Character> str = characters.iterator();
        while (str.hasNext() && !game.isPause()) {
            str.next().update();
        }
    }
    protected void updateMessage(){
        if(game.isPause()){
            return;
        }
        Message m;
        int l;
        for(int i = 0; i < messages.size(); i++){
            m = messages.get(i);
            l = m.getDuration();

            if(l > 0){
                m.setDuration(--l);
            }else{
                messages.remove(i);
            }
        }
    }

    // Phương thức đếm thời gian còn lại 
    public int subtractTime(){
        if(game.isPause() || time <= 0){
            return time;
        }
        return --time;
    }


    
    public Keyboard getInput(){
        return this.input;
    }

    public LevelLoader getLevel(){
        return this.levelLoader; 
    }

    public Game getGame(){
        return this.game;
    }    

    public int getScreenshow(){
        return this.screenshow;
    }

    public void setScreenshow(int i){
        this.screenshow = i;
    }

    public int getTime(){
        return this.time;
    }

    public int getPoint(){
        return this.point;
    }

    public void addPoint(int point){
        this.point += point;
    }

    public int getWidth(){
        return levelLoader.getWidth();
    }

    public int getHeight(){
        return levelLoader.getHeight();
    }
}
