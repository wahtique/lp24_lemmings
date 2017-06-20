package sample.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import static sample.model.SoundManager.getSoundManager;

/**A class used to store some game parameters. One should be charged or created by default at the start of the game
 * @author William
 * @since 15/05/2017
 */

/**kinda not used right now...*/
public class Parameters implements Serializable
{
    private double SFXVolume;
    private double musicVolume;

    public Parameters() throws Exception
    {
        if(!generalParametersExist())
        {
            SFXVolume = getSoundManager().getSFXVolume();
            musicVolume = getSoundManager().getBGMVolume();
        }
        else
        {
            FileInputStream fin = new FileInputStream("general.settings");
            ObjectInputStream oin = new ObjectInputStream(fin);
            Parameters param = (Parameters) oin.readObject();
            this.musicVolume = param.getMusicVolume();
            this.SFXVolume = param.getSFXVolume();
            oin.close();
            fin.close();
        }
    }


    private boolean generalParametersExist()
    {
        File f = new File("general.settings");
        return f.exists();

    }

    /* for the following getters, the double given as parameter should be between 0 to 1 */


    public double getSFXVolume()
    {
        return SFXVolume;
    }

    public void setSFXVolume(int SFXVolume)
    {
        this.SFXVolume = SFXVolume;
    }

    public double getMusicVolume()
    {
        return musicVolume;
    }

    public void setMusicVolume(int musicVolume)
    {
        this.musicVolume = musicVolume;
    }

    @Override
    public String toString()
    {
        return "Parameters{" +
                "SFXVolume=" + SFXVolume +
                ", musicVolume=" + musicVolume +
                '}';
    }
}
