package sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;

public class Sound{
    //Phát âm thanh .wav nằm trong thư mục /sound/
    public static void play(String sound) {
        new Thread(() -> {
            try {
                System.out.println("Playing sound: " + sound);
                File file = new File("res/sound/" + sound + ".wav");

                if (!file.exists()) {
                    System.err.println("Không tìm thấy file: " + file.getAbsolutePath());
                    return;
                }

                AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            } catch (Exception e) {
                System.err.println("Lỗi khi phát âm thanh: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }


    public static void stop(String sound){
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Sound.class.getResourceAsStream("/sound/" + sound + ".wav"));
                    clip.open(inputStream);
                    clip.stop();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}