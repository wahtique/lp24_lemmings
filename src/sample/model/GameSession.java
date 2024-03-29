package sample.model;

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

    /**The profile settings we will use when we play.
     * When creating a new game, it will use the general settings.
     * When loading, we will use the parameters already existing.
     * Not used for now, may be useful later.*/
    private Parameters profileSettings;



    /**
     * Method creating a new game session from scratch. Used mainly to start a new game.
     * @param playerName The name of the player AND the savegame
     * @param numberOfStudents The number of students we will play with
     * @throws Exception because why not
     */
    public GameSession(String playerName, int numberOfStudents) throws Exception
    {
        this.playerName = playerName;
        this.numberOfStudents = numberOfStudents;
        level = 1;
        students = new ArrayList<>();
        /*I had numerOfStudent to the list students*/
        for(int i = 1; i <= numberOfStudents; i++)
        {
            students.add(new StudentData());
        }
        this.profileSettings = new Parameters();
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

    /**
    * Method saving the game in the default save folder.
    * @return boolean true if the save was successful, false otherwise
    */
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
            f = new File("savedGames/"+playerName+".kkk");
            if(f.exists())
            {
                f.delete();
            }
            FileOutputStream fout = new FileOutputStream(f);
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

    /**
     * Constructor used when loading a game.
     * @param f the file froom which we will load the GameSession
     */
    public GameSession(File f)
    {
        try
        {
            FileInputStream fin = new FileInputStream(f);
            ObjectInputStream oin = new ObjectInputStream(fin);

            GameSession session = (GameSession) oin.readObject();
            this.playerName = session.getPlayerName();
            this.numberOfStudents = session.getNumberOfStudents();
            this.level = session.level;
            this.students = new ArrayList<>(session.getStudents());
            this.profileSettings = session.getProfileSettings();
            oin.close();
            fin.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return "GameSession{" +
                "playerName='" + playerName + '\'' +
                ", level=" + level +
                ", students=" + students +
                ", numberOfStudents=" + numberOfStudents +
                ", profileSettings=" + profileSettings +
                '}';
    }

    /**
     * Build a String to be used with the GUI. Prettier than the one above
     * @return a String to be displayed in the GUI
     */
    public String toDisplayString()
    {
        String result = playerName + "\n" +
                "Current level : " + (level - 1) + "\n" +
                "Exams attended by your lemmings :\n";
        int i =1;
        for (StudentData d : students)
        {
            result += "Student " + i + " : " + (d.getExamsAttended()-1) + "\n";
            ++i;
        }
        return result;
    }

    public Parameters getProfileSettings()
    {
        return profileSettings;
    }

    public void setProfileSettings(Parameters profileSettings)
    {
        this.profileSettings = profileSettings;
    }
}
