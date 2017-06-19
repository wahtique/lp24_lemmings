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
    private double SFXVolume;
    private double BGMVolume;



    public void playSFX(String soundFile) throws IOException
    {
        URL urlIn = getClass().getResource(soundFile);
        Media m = new Media(urlIn.toString());
        MediaPlayer mp = new MediaPlayer(m);
        mp.setVolume(SFXVolume);
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
        FloatControl gainControl = (FloatControl) BGM.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (float) ((range * BGMVolume) + gainControl.getMinimum());
        gainControl.setValue(gain);
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


    private SoundManager()
    {
        setBGMVolume(1);
        setSFXVolume(1);
        try
        {
            BGM = AudioSystem.getClip();
        } catch (LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }

    private static class SoundManagerHolder
    {
        private final static SoundManager instance = new SoundManager();
    }

    public static SoundManager getSoundManager()
    {
        return SoundManagerHolder.instance;
    }

    public double getBGMVolume()
    {
        return BGMVolume;
    }

    public void setBGMVolume(double BGMVolume)
    {
        if(BGMVolume > 1)
        {
            this.BGMVolume = 1;
        }
        else if(BGMVolume < 0 )
        {
            this.BGMVolume = 0;
        }
        else
        {
            this.BGMVolume = BGMVolume;
        }
    }
    public double getSFXVolume()
    {
        return SFXVolume;
    }

    public void setSFXVolume(double SFXVolume)
    {
        if(BGMVolume > 1)
        {
            this.SFXVolume = 1;
        }
        else if(BGMVolume < 0 )
        {
            this.SFXVolume = 0;
        }
        else
        {
            this.SFXVolume = SFXVolume;
        }
    }
}
