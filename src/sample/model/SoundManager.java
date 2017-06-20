package sample.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**Class managing the sound effects. Singleton.
 * @author William
 * @since 12/06/2017
 */

public class SoundManager
{

    /**Background music*/
    private Clip BGM;
    private double SFXVolume;
    private double BGMVolume;


    /**
     * Play a SFX
     * @param soundFile the path to the file. From /src
     * @throws IOException if the file does not exist
     */
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

    /**
     * set the BGM from a file and adjust the volume
     * @param musicFile path to the file, from /src
     * @throws IOException if the file does not exist
     * @throws UnsupportedAudioFileException should be wav
     * @throws LineUnavailableException if it can't read the stream
     */
    public void setBGM(String musicFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException
    {
        URL u = getClass().getResource(musicFile);
        AudioInputStream BGMStream = AudioSystem.getAudioInputStream(u);
        BGM.close();
        BGM.open(BGMStream);
        FloatControl gainControl = (FloatControl) BGM.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (float) ((range * BGMVolume) + gainControl.getMinimum());
        gainControl.setValue(gain);
    }

    /**
     * Play the BGM actually stored
     * @throws IOException if the BGM isnt set properly
     */
    public void playBGM() throws IOException
    {

        stopBGM();
        BGM.loop(-1);


    }

    /**
     * Stop the BGM
     */
    public void stopBGM()
    {
        BGM.stop();
    }

    /**
     * Build a new SoundManager with default volume parameter = 1 and BGM = null
     */
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

    /**
     * private class holding the singleton
     */
    private static class SoundManagerHolder
    {
        private final static SoundManager instance = new SoundManager();
    }

    /**
     * static method used to access to the singleton
     * @return the SoundManager
     */
    public static SoundManager getSoundManager()
    {
        return SoundManagerHolder.instance;
    }

    public double getBGMVolume()
    {
        return BGMVolume;
    }

    /**
     * set the BGM volume, between 0 and 1
     * @param BGMVolume set to 1 if >1, 0 if <0
     */
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

    /**
     * set the SFX volume
     * @param SFXVolume set to 1 if >1, 0 if <0
     */
    public void setSFXVolume(double SFXVolume)
    {
        if(SFXVolume > 1)
        {
            this.SFXVolume = 1;
        }
        else if(SFXVolume < 0 )
        {
            this.SFXVolume = 0;
        }
        else
        {
            this.SFXVolume = SFXVolume;
        }
    }
}
