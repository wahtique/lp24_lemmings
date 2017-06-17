package sample.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author William
 * @since 12/06/2017
 */
public class SoundManager
{
    private Clip BGM;

    public void playSound(String soundFile) throws IOException
    {
        URL urlIn = getClass().getResource(soundFile);
        Media m = new Media(urlIn.toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.setAutoPlay(true);
        new Thread(() ->
        {
            synchronized (mp)
            {
                mp.play();
            }
        }).start();
    }



    public void setBGM(String musicFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException
    {
        URL u = getClass().getResource(musicFile);
        AudioInputStream BGMStream = AudioSystem.getAudioInputStream(u);
        BGM.open(BGMStream);
    }


    public void playBGM() throws IOException
    {

        stopBGM();
        BGM.loop(177);


    }

    public void stopBGM()
    {
        BGM.stop();
    }


    public SoundManager() throws LineUnavailableException
    {

        BGM = AudioSystem.getClip();
    }
}
