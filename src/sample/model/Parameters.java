package sample.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**A class used to store some game parameters. One should be charged or created by default at the start of the game
 * @author William
 * @since 15/05/2017
 */
public class Parameters implements Serializable
{
    private int SFXVolume;
    private int musicVolume;

    public Parameters() throws Exception
    {
        if(!generalParametersExist())
        {
            SFXVolume = 100;
            musicVolume = 100;
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

    /* for the following getters, the int given as parameter should be between 0 to 100 */



    public int getSFXVolume()
    {
        return SFXVolume;
    }

    public void setSFXVolume(int SFXVolume)
    {
        this.SFXVolume = SFXVolume;
    }

    public int getMusicVolume()
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
