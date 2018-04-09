package cluedo_game;

public class GameMusic extends LoopSound implements Runnable {
    static Thread t;

    public GameMusic() {}

    public void play() {
        t = new Thread(this);
        t.start();
    }

    public void run() {
        String fileLocation = "src/music/gamemusic.wav";
        try {
                super.playSound(fileLocation);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
