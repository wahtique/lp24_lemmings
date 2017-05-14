package sample;

import java.io.*;
import java.util.ArrayList;

/**
 * Class used to describe the state of a game session.
 * Will be used to save the game state after each level played &
 * load former gaming sessions.
 * @author William
 * @since 14/05/2017
 */
public class GameSession implements Serializable
{
    /**Compulsory player name : the saved game will be named as follow.*/
    private String playerName;

    /**The last level attained by the player*/
    private int level;

    /**A list containing the data relative to every students of this session.
     * Funfact : Wanted to name it drunkards at first. */
    private ArrayList<StudentData> students;

    /**Number of students / lemmings we will have to manage*/
    private int numberOfStudents;

    /**Method creating a new game session from scratch. Used mainly to start a new game.
     * @param playerName The name of the player AND the savegame
     * @param numberOfStudents The number of students we will play with*/
    public GameSession(String playerName, int numberOfStudents)
    {
        this.playerName = playerName;
        this.numberOfStudents = numberOfStudents;
        level = 0;
        students = new ArrayList<>();
        /*I had numerOfStudent to the list students*/
        for(int i = 1; i <= numberOfStudents; i++)
        {
            students.add(new StudentData());
        }
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public ArrayList<StudentData> getStudents()
    {
        return students;
    }

    public int getNumberOfStudents()
    {
        return numberOfStudents;
    }

    /**Method saving the game in the default save folder.
     * @return boolean true if the save was successful, false otherwise*/
    public boolean saveGameSession()
    {
        try
        {
            File f = new File("savedGames");
            /*we create the savedGames repertory if it does not exist*/
            if(!f.isDirectory())
            {
                f.mkdir();
            }

            FileOutputStream fout = new FileOutputStream("savedGames/"+playerName);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(this);
            oout.close();
            fout.close();
            return true;

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**Method loading a saved game session. The vrification its existence should be done before
     * @param name the name of the gamesession to be loaded. We assume it exist when the method is called
     * @return  a gameSession built from its persistent representation
     * */
    public GameSession loadGameSession(String name)
    {
        try
        {
            FileInputStream fin = new FileInputStream("savedGanmes/"+name);
            ObjectInputStream oin = new ObjectInputStream(fin);

            GameSession session = (GameSession) oin.readObject();

            oin.close();
            fin.close();
            return session;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


}
