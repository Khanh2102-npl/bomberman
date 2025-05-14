package level;

import main.Board;
import main.Game;
import entities.LayerEntity;
import entities.character.Bomber;
import entities.character.Character;
import entities.character.enemy.Balloon;
import entities.character.enemy.Doll;
import entities.character.enemy.Oneal;
import entities.tile.Grass;
import entities.tile.Portal;
import entities.tile.Wall;
import entities.tile.destroyable.Brick;
import entities.tile.item.BomItem;
import entities.tile.item.FlameItem;
import entities.tile.item.SpeedItem;
import exceptions.Loadlevelexception;
import graphics.Screen;
import graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/*
 * Lớp FileLevelLoader trong game Bomberman là một lớp mở rộng từ LevelLoader
 * có nhiệm vụ đọc dữ liệu bản đồ từ file cấu hình và tạo các thực thể (Entity) tương ứng trong trò chơi
 */

public class FilelevelLoader extends LevelLoader{
    /**
     * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được từ
     * ma trận bản đồ trong tệp cấu hình
     */
    private static char[][] maps;
    private int level;

    public FilelevelLoader(Board board, int level) throws Loadlevelexception{
        super(board, level);
        this.level = level;
    }

    @Override
    /*
     * đọc nội dung từ file res/levels/Level{level}.txt để khởi tạo bản đồ
     * cập nhật các giá trị đọc được vào width, height, level, maps
     */
    public void loadLevel(int level) throws Loadlevelexception{
        List<String> list = new ArrayList<>();
        try {
            File file = new File("res" + File.separator + "levels" + File.separator + "Level" + level + ".txt");
            FileReader fr = new FileReader(file);

            BufferedReader buff = new BufferedReader(fr);
            String line;
            while ((line = buff.readLine()) != null && !line.trim().isEmpty()) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Loadlevelexception("Không thể tải bản đồ level " + level + ": " + e.getMessage());
        }
        String[] arrays = list.get(0).trim().split(" ");
        this.level = Integer.parseInt(arrays[0]);
        height = Integer.parseInt(arrays[1]);
        width = Integer.parseInt(arrays[2]);
        maps = new char[height][width];
        for (int i = 0; i < height; i++) {
            String mapLine = list.get(i + 1);
            if (mapLine.length() < width) {
                throw new Loadlevelexception("Dòng bản đồ thứ " + (i + 1) + " không đủ ký tự. Phải có " + width + ", nhưng chỉ có " + mapLine.length());
            }
            for (int j = 0; j < width; j++) {
                maps[i][j] = mapLine.charAt(j);
            }
        }
        //gan cac phan tu cho mang
    }

    @Override
    /*
     * Phương thức này tạo thực thể trong trò chơi từ bản đồ _map đã đọc ở trên
     * Dùng addEntity để thêm đối tượng nền (tile), addCharacter cho đối tượng có chuyển động (Bomber, enemy)
     */
    public void createEntities(){
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                int pos = x + y * getWidth();
                char c = maps[y][x];
                switch (c) {
                    // Thêm grass
                    case ' ':
                        board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;
                    // Thêm Wall
                    case '#':
                        board.addEntity(pos, new Wall(x, y, Sprite.wall));
                        break;
                    // Thêm Portal
                    case 'x':
                        board.addEntity(pos, new LayerEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new Portal(x, y, board, Sprite.portal),
                                new Brick(x, y, Sprite.brick)));
                        break;
                    // Thêm brick
                    case '*':
                        board.addEntity(x + y * width,
                                new LayerEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;
                    // Thêm Bomber
                    case 'p':
                        board.addCharacter(new Bomber(Coordinate.tiletopixel(x), Coordinate.tiletopixel(y) + Game.TILES_SIZE, board));
                        Screen.SetOffset(0, 0);
                        board.addEntity(x + y * width, new Grass(x, y, Sprite.grass));
                        break;

                    // Thêm Balloon
                    case '1':
                        board.addCharacter(new Balloon(Coordinate.tiletopixel(x), Coordinate.tiletopixel(y) + Game.TILES_SIZE, board));
                        board.addEntity(x + y * width, new Grass(x, y, Sprite.grass));
                        break;
                    // Thêm Oneal
                    case '2':
                        board.addCharacter(new Oneal(Coordinate.tiletopixel(x), Coordinate.tiletopixel(y) + Game.TILES_SIZE, board));
                        board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;
                    // Thêm Doll
                    case '3':
                        board.addCharacter(new Doll(Coordinate.tiletopixel(x), Coordinate.tiletopixel(y) + Game.TILES_SIZE, board));
                        board.addEntity(x + y * width, new Grass(x, y, Sprite.grass));
                        break;
                    // Thêm oneal
                    // Thêm BomItem            
                    case 'b':
                        LayerEntity layer = new LayerEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new BomItem(x, y, Sprite.powerup_bombs),
                                new Brick(x, y, Sprite.brick));
                        board.addEntity(pos, layer);
                        break;
                    // Thêm SpeedItem
                    case 's':
                        layer = new LayerEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new SpeedItem(x, y, Sprite.powerup_speed),
                                new Brick(x, y, Sprite.brick));
                        board.addEntity(pos, layer);
                        break;
                    // Thêm FlameItem
                    case 'f':
                        layer = new LayerEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new FlameItem(x, y, Sprite.powerup_flames),
                                new Brick(x, y, Sprite.brick));
                        board.addEntity(pos, layer);
                        break;

                    default:
                        board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;

                }
            }
        }
        System.out.println("Hoàn tất tạo entity cho level " + level);
    }
}
