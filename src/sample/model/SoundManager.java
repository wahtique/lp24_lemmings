package sample.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private Media BGM;
    private MediaPlayer BGMPlayer;

    public void playSound(String soundFile) throws IOException
    {
        URL urlIn = getClass().getResource(soundFile);
        Media m = new Media(urlIn.toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.setAutoPlay(true);
        new Thread()
        {
            public void run()
            {
                synchronized (mp)
                {
                    mp.stop();
                    mp.play();
                }
            }
        }.start();
    }



    public void setBGM(String musicFile)
    {
        if (!BGM.getSource().equalsIgnoreCase(musicFile))
        {
            URL urlIn = getClass().getResource(musicFile);
            BGM = new Media(urlIn.toString());
            BGMPlayer = new MediaPlayer(BGM);
            BGMPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
    }
    public void playBGM()
    {
        new Thread()
        {
            public void run()
            {
                synchronized (BGMPlayer)
                {
                    BGMPlayer.stop();
                    BGMPlayer.setAutoPlay(true);
                }

            }
        }.start();
    }

    public void stopBGM()
    {
        if (BGM != null && BGMPlayer != null) BGMPlayer.stop();
    }

    public SoundManager(String BGMPath) throws IOException
    {
        URL urlIn = getClass().getResource(BGMPath);
        BGM = new Media(urlIn.toString());
        BGMPlayer  = new MediaPlayer(BGM);
    }

    public SoundManager()
    {
        BGM = null;
        BGMPlayer = null;
    }
}
