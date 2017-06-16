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
    private HashSet<Lemmings> garbageLemmings = new HashSet<>();
    private HitBox exit;


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


    public Level(HashSet<Collidable> terrain, HashSet<Lemmings> lemmingss, HitBox exit) {
        this.terrain = terrain;
        this.lemmingsList = lemmingss;
        this.vomits = new HashSet<>();
        this.vomitsToDel = new HashSet<>();
        this.exit =exit;
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
        this.getGarbageLemmings().forEach(l-> lemmingsList.remove(l));
        this.garbageLemmings.clear();
    }

    public void drawLevel(GraphicsContext gc){
        lemmingsList.forEach(l-> l.draw(gc));
        terrain.forEach(t-> t.draw(gc));
    }

    public void select(Vector pos){
        this.lemmingsList.stream().filter(l-> l.isInHitbox(pos)).findFirst().get().setSelected(true);
    }

    public void deselect(Vector pos) {
        if (lemmingsList.stream().anyMatch(l -> l.isInHitbox(pos) && l.isSelected())) {
            this.lemmingsList.stream().filter(l -> l.isInHitbox(pos)).findFirst().get().setSelected(false);
        }
    }

    public HashSet<Lemmings> getGarbageLemmings() {
        return garbageLemmings;
    }

    public HitBox getExit() {

        return exit;
    }
}
