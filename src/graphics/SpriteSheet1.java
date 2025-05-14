package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất.
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó.
 */
public class SpriteSheet1 implements SpriteSheetInterface {

    private String path;
    public final int SIZE;
    public int[] pixels;

    public static SpriteSheet1 tiles = new SpriteSheet1("res/textures/tile.png", 256);

    public SpriteSheet1(String path, int size) {
        this.path = path;
        this.SIZE = size;
        this.pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
        File file = new File(path);
        System.out.println("Loading image from: " + file.getAbsolutePath());

        if (!file.exists()) {
            throw new IllegalArgumentException("File không tồn tại: " + path);
        }

        BufferedImage image = ImageIO.read(file); // Sẽ lỗi nếu file không tồn tại
        int w = image.getWidth();
        int h = image.getHeight();
        image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
        throw new RuntimeException("Lỗi load ảnh: " + path, e);
    	}
    }
    @Override
    public int[] getPixels() {
        return pixels;
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
