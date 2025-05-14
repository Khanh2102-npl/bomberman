package level;

import main.Game;

/**
 * Chuyen doi giua toa do pixel va toa do tile trong ban do
 */

public class Coordinate {

    // Chuyen doi toa do pixel thanh chi so tile(o tren ban do)
    public static int pixeltotile(double t){
        return (int)(t / Game.TILES_SIZE);
    }

    // Chuyen doi chi so tile thanh toa do pixel
    public static int tiletopixel(int t){
        return t * Game.TILES_SIZE;
    }
    public static int tiletopixel(double t){
        return (int)(t * Game.TILES_SIZE);
    }
}