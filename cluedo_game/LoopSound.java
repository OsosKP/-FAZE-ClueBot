package cluedo_game;

import javax.sound.sampled.*;
import java.io.File;

public class LoopSound implements Runnable {
    private static boolean playSong = true;
    static SourceDataLine line = null;
    static Thread t;

    public LoopSound() {}

    public void play() {
        t = new Thread(this);
        t.start();
    }

    public static void turnMusicOff() {
        line.stop();
        t.interrupt();
        playSong = false;
    }

    public static void turnMusicOn() {
        playSong = true;
        GameLogic.playMusic();
    }

    @Override
    public void run() {
        String fileLocation = "src/music/start.wav";
        try {
            while (playSong) {
                playSound(fileLocation);
                assert playSong;
            }
        } catch (Exception e) { e.printStackTrace(); }

    }

    private void playSound(String fileName) throws Exception {
        File soundFile = new File(fileName);
        AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
        AudioFormat format = stream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);

        line.start();
        int size = 0;
        byte[] bytes = new byte[131072];
        while (size != -1) {
            size = stream.read(bytes, 0, bytes.length);

            if (size >= 0)
                line.write(bytes, 0, size);
        }

        line.drain();
        line.close();
    }
}
