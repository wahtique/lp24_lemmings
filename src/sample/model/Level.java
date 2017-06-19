package sample.model;

import javafx.scene.canvas.GraphicsContext;
import sample.view.Drawer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by yann on 19/05/17.
 */

public class Level {

    private double ttime=5;
    private ArrayList<Lemmings> lemmingsNotSpawned;
    private HashSet<Lemmings> lemmingsList = new HashSet<>();
    private Vector spawnpoint =new Vector(10,100);
    private double spawndelay = 5;
    private HashSet<Collidable> terrain;
    private HashSet<Vomit> vomits;
    private HashSet<Vomit> vomitsToDel;
    private HashSet<Lemmings> garbageLemmings = new HashSet<>();
    private HitBox exit;

    public int Pls = 10;
    public int Construct = 10;
    public int Vomits = 10;


    public Level(String url, ArrayList<Lemmings> lts) {
        this.lemmingsNotSpawned = lts;
        this.terrain = new HashSet<>();
        this.lemmingsList = new HashSet<>();
        this.vomits = new HashSet<>();
        this.vomitsToDel = new HashSet<>();

        try {
            URI uri = this.getClass().getResource(url).toURI();

            Path myPath;


            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem;
                try {
                    fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                }catch (Exception e){
                    fileSystem = FileSystems.getFileSystem(uri);
                }
                myPath = fileSystem.getPath(url);
            } else {
                myPath = Paths.get(uri);
            }

            Stream<Path> walk = Files.walk(myPath, 1);
            String config = "";

            for (Iterator<Path> it = walk.iterator(); it.hasNext(); ) {
                String temp = it.next().getFileName().toString();
                 if (temp.endsWith(".txt")) {
                    config = url + "/" + temp;
                }
            }
            if (!config.equals("")) {
                BufferedReader input = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(config)));
                String line = input.readLine();
                while (line != null) {

                    String[] words = line.split("=");
                    switch (words[0]){
                        case "terrain":
                            HitBox trn = new HitBox(url +"/" +words[1]);
                            terrain.add(trn);
                            Drawer.getDrawer().addSomethingToDraw(trn);
                            break;
                        case "decor":
                            Sprite image = new Sprite(url +"/" +words[1]);
                            if (!words[2].isEmpty()){
                                image.setLayer(Integer.parseInt(words[2]));
                            }
                            Drawer.getDrawer().addSomethingToDraw(image);
                            break;
                        case "wayout":
                            this.exit = new HitBox(url +"/" +words[1]);
                            break;
                        case "spawn":
                            if(!words[1].isEmpty() && !words[2].isEmpty() && !words[3].isEmpty()){
                                spawnpoint= new Vector(Double.parseDouble(words[1]), Double.parseDouble(words[2]));
                                spawndelay= Double.parseDouble(words[3]);
                            }
                            break;
                            case "powers":
                            if(!words[1].isEmpty() && !words[2].isEmpty() && !words[3].isEmpty()){
                                Pls = Integer.parseInt(words[1]);
                                Vomits = Integer.parseInt(words[3]);
                                Construct = Integer.parseInt(words[2]);

                            }
                            break;
                        default: break;
                    }




                    line = input.readLine();
                }
//                System.out.println(input.readLine());
//                System.out.println(input.readLine());

            }

        } catch (Exception e) {
            System.out.println("Can't instantiate the beautiful Level !!!");
            e.printStackTrace();
        }
    }

    public Level(HashSet<Collidable> terrain, HitBox exit, ArrayList<Lemmings> lts) {
        this.lemmingsNotSpawned = lts;
        this.lemmingsList= new HashSet<>();
        this.terrain = terrain;
        this.vomits = new HashSet<>();
        this.vomitsToDel = new HashSet<>();
        this.exit =exit;
    }

    public void spawn(double deltatime){
            if (ttime<=spawndelay){
                ttime  =ttime+deltatime;
            }else {
                this.lemmingsNotSpawned.get(0).setPosition(new Vector(spawnpoint.getX(),spawnpoint.getY()));
                Drawer.getDrawer().addSomethingToDraw(this.lemmingsNotSpawned.get(0));
                this.lemmingsList.add(this.lemmingsNotSpawned.get(0));
                this.lemmingsNotSpawned.remove(0);
                this.ttime =0;
            }
    }

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


    public void removeVomit(Vomit toDel){
        vomitsToDel.add(toDel);

    }

    public Level() {
        this.terrain = null;
        this.lemmingsList = null;
        this.vomits= null;
    }

    public void update(double deltatime){
        if (!lemmingsNotSpawned.isEmpty()){
            spawn(deltatime);
        }
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
        if (lemmingsList.stream().anyMatch(l -> l.isInHitbox(pos) && !l.isSelected())) {
            this.lemmingsList.stream().filter(l -> l.isInHitbox(pos)).findFirst().get().setSelected(true);
        }
    }
    public void toggleSelect(Vector pos){
        if (lemmingsList.stream().anyMatch(l -> l.isInHitbox(pos))) {
            Lemmings temp = this.lemmingsList.stream().filter(l -> l.isInHitbox(pos)).findFirst().get();
            temp.setSelected(!temp.isSelected());
        }
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

    public ArrayList<Lemmings> getLemmingsNotSpawned() {
        return lemmingsNotSpawned;
    }

    public void setLemmingsNotSpawned(ArrayList<Lemmings> lemmingsNotSpawned) {
        this.lemmingsNotSpawned = lemmingsNotSpawned;
    }
}
