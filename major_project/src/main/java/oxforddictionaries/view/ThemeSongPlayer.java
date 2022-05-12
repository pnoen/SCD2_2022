package oxforddictionaries.view;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

/**
 * Plays the theme song in the background of the GUI
 */
public class ThemeSongPlayer {
    private boolean paused;
    private MediaPlayer player;

    /**
     * Creates the player and starts the playing
     */
    public void start() {
        String[] path = {"src", "main", "resources", "dreamy night - LilyPichu.mp3"};
        String filename = String.join(File.separator, path);
        File file = new File(filename);
        String fileUri = file.toURI().toString();
        Media themeSong = new Media(fileUri);
        this.player = new MediaPlayer(themeSong);
        player.setOnEndOfMedia(() -> {
            player.seek(Duration.ZERO);
        });
        this.paused = false;
        player.setVolume(0.3);
        player.play();
    }

    /**
     * Pauses and plays the theme song
     */
    public void changeState() {
        if (paused) {
            player.play();
            this.paused = false;
        }
        else {
            player.pause();
            this.paused = true;
        }
    }

    /**
     * @return if paused
     */
    public boolean getState() {
        return paused;
    }
}
