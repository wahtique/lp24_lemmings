package sample.model;

import java.util.ArrayList;
import java.util.HashSet;

/**Level which can be loaded from a gameSession. Used to start from any level specified in a gameSession
 * Created by naej on 19/06/17.
 */
//TODO document this

public class LoadableLevel extends Level {
    private GameSession gameSession;

    public LoadableLevel(GameSession gameSession,String url){

        super(url,new ArrayList<Lemmings>());
        this.gameSession = gameSession;

        for(StudentData data : gameSession.getStudents()){
            getLemmingsNotSpawned().add(new Lemmings(new Vector(120,20), new Vector(50,0),this,data,gameSession.getLevel()));
        }

    }

    public int getLevel(){
        return gameSession.getLevel();
    }
}
