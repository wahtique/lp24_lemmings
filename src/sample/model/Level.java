package sample.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;

/**
 * Created by yann on 19/05/17.
 */
public class Level {
    private HashSet<Lemmings> lemmingsList;
    private HashSet<Collidable> terrain;
    private HashSet<Vomit> vomits;
    private HashSet<Vomit> vomitsToDel;


    public HashSet<Lemmings> getLemmingsList() {
        return lemmingsList;
    }

    public void setLemmingsList(HashSet<Lemmings> lemmingsList) {
        this.lemmingsList = lemmingsList;
    }

    public HashSet<Collidable> getTerrain() {
        return terrain;
    }

    public HashSet<Vomit> getVomits(){
        return vomits;
    }
    public void setTerrain(HashSet<Collidable> terrain) {
        this.terrain = terrain;
    }


    public Level(HashSet<Collidable> terrain, HashSet<Lemmings> lemmingss) {
        this.terrain = terrain;
        this.lemmingsList = lemmingss;
        this.vomits = new HashSet<>();
        this.vomitsToDel = new HashSet<>();
    }


    public void removeVomit(Vomit toDel){
        vomitsToDel.add(toDel);

    }

    public Level() {
        this.terrain = null;
        this.lemmingsList = null;
        this.vomits= null;
    }

    public void update(double deltatime){
        vomitsToDel.forEach(l -> vomits.remove(l));
        vomitsToDel.clear();
        lemmingsList.forEach(l -> l.update(deltatime));
        vomits.forEach(l -> l.update(deltatime,this));
    }

    public void drawLevel(GraphicsContext gc){
        lemmingsList.forEach(l-> l.draw(gc));
        terrain.forEach(t-> t.draw(gc));
    }
}
