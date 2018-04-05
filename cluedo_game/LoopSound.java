package cluedo_game;

import javax.sound.sampled.*;
import java.io.File;

public class LoopSound implements Runnable {
    private String fileLocation = "src/music/start.wav";
    private static boolean playSong = true;

    public LoopSound() {}

    public void play() {
        Thread t = new Thread(this);
        t.start();
    }

    public static void turnMusicOff() {
        playSong = false;
    }

    @Override
    public void run() {
        try {
            while (playSong)
                playSound(fileLocation);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void playSound(String fileName) throws Exception {
        File soundFile = new File(fileName);
        AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
        AudioFormat format = stream.getFormat();

        SourceDataLine line = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);

        line.start();
        int nBytesRead = 0;
        int nBytesWritten = 0;
        byte[] abData = new byte[128000];
        while (nBytesRead != -1) {
            nBytesRead = stream.read(abData, 0, abData.length);

            if (nBytesRead >= 0)
                nBytesWritten = line.write(abData, 0, nBytesRead);
        }

        line.drain();
        line.close();
    }
}
