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
    private double ttime=0;
    private ArrayList<Lemmings> lemminagNotSpawned;
    private HashSet<Lemmings> lemmingsList;
    private Vector spawnpoint =new Vector(10,10);
    private double spawndelay = 2;
    private HashSet<Collidable> terrain;
    private HashSet<Vomit> vomits;
    private HashSet<Vomit> vomitsToDel;
    private HashSet<Lemmings> garbageLemmings = new HashSet<>();
    private HitBox exit;

    public Level(String url, ArrayList<Lemmings> lts) {
        this.lemminagNotSpawned = lts;
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
                            terrain.add(new HitBox(url +"/" +words[1]));
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
                        default: System.out.println("Potato"); break;
                    }



                   /* if (words[0].contentEquals("terrain")) {
                        terrain.add(new HitBox(url +"/" +words[1]));
                    } else if (words[0].contentEquals("bg")) {
                        Sprite image = new Sprite(url +"/" +words[1]);
                        if (!words[2].isEmpty()){
                            image.setLayer(Integer.parseInt(words[2]));
                        }
                        Drawer.getDrawer().addSomethingToDraw(image);
                    } else if(words.)*/

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

    public Level(HashSet<Collidable> terrain, HitBox exitArrayList, ArrayList<Lemmings> lts) {
        this.lemminagNotSpawned = lts;
        this.lemmingsList= new HashSet<>();
        this.terrain = terrain;
        this.vomits = new HashSet<>();
        this.vomitsToDel = new HashSet<>();
        this.exit =exit;
    }

    public void spawn(double deltatime){
            if (ttime<spawndelay){
                ttime  =ttime+deltatime;
            }else {
                this.lemminagNotSpawned.get(0).setPosition(spawnpoint);
                Drawer.getDrawer().addSomethingToDraw(this.lemminagNotSpawned.get(0));
                this.lemmingsList.add(this.lemminagNotSpawned.get(0));
                this.lemminagNotSpawned.remove(0);
                ttime =0;
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
        if (!lemminagNotSpawned.isEmpty()){
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
