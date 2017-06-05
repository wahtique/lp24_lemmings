package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;

/**
 * Created by yann on 19/05/17.
 */
public class Level {
    private HashSet<Lemmings> lemmingsList;
    private HashSet<HitBox> terrain;

    public HashSet<Lemmings> getLemmingsList() {
        return lemmingsList;
    }

    public void setLemmingsList(HashSet<Lemmings> lemmingsList) {
        this.lemmingsList = lemmingsList;
    }

    public HashSet<HitBox> getTerrain() {
        return terrain;
    }

    public void setTerrain(HashSet<HitBox> terrain) {
        this.terrain = terrain;
    }


    public Level(HashSet<HitBox> terrain, HashSet<Lemmings> lemmingss) {
        this.terrain = terrain;
        this.lemmingsList = lemmingss;
    }

    public Level() {
        this.terrain = null;
        this.lemmingsList = null;
    }

    public void update(double deltatime){
        lemmingsList.forEach(l -> l.update(deltatime,terrain));
    }

    public void drawLevel(GraphicsContext gc){
        lemmingsList.forEach(l-> l.drawLemmings(gc));
        terrain.forEach(t-> t.drawImage(gc));
    }
}

