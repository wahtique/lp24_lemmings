package sample.controler;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.model.*;
import sample.view.Drawer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;


public class MainGameController {
    @FXML
    private Canvas canvas;

    @FXML
    private Pane panneau;

   // private Color currentColor = Color.color(1, 0.0078, 0);
    private Level test = new Level(new HashSet<Collidable>(), new HitBox("resources/levels/testlevel1/wayout.png"),new ArrayList<Lemmings>());

    private Drawer drawer;

    private AnimatedSprite anim;
    MainGameUpdater timeSetter;

    public void start() throws IOException, URISyntaxException {
        drawer = Drawer.getDrawer();
        drawer.setCanvas(canvas);
        Sprite bg = new Sprite("resources/levels/testlevel1/bg-5.png");
        Sprite fg = new Sprite("resources/levels/testlevel1/fg1.png");
        bg.setLayer(-10);
        fg.setLayer(10);
        drawer.addSomethingToDraw(bg);
        drawer.addSomethingToDraw(fg);

        timeSetter = new MainGameUpdater();
        timeSetter.start(this);


        test.getTerrain().add(new HitBox("resources/levels/testlevel1/terrain1.png"));
        test.getTerrain().forEach(o->drawer.addSomethingToDraw(o));
        Lemmings roger = new Lemmings(new Vector(120,20), new Vector(50,0),
                                        "resources/Lemming/hitboxes/walk/feets.png",
                                        "resources/Lemming/hitboxes/walk/body.png", test);
        Lemmings paniou = new Lemmings(new Vector(50,20), new Vector(50,0),
                "resources/Lemming/hitboxes/walk/feets.png",
                "resources/Lemming/hitboxes/walk/body.png",test);
        test.getLemmingsNotSpawned().add(roger);
        test.getLemmingsNotSpawned().add(paniou);

    }

    public void update(double deltaTime) {
       //System.out.println("FPS : "+ 1/deltaTime );
       autoSetCanvasDim();
//       anim.update(deltaTime);
       test.update(deltaTime);
       drawer.draw();
     //  test.drawLevel(canvas.getGraphicsContext2D());

    }

    public void onMouseClick (MouseEvent e) {
        //  test.getLemmingsList().stream().findFirst().get().setPosition(new Vector(e.getX(),e.getY()));
        if (e.isPrimaryButtonDown()) {
            //test.getVomits().add(new Vomit(new Vector(e.getX(), e.getY())));
            test.select(new Vector(e.getX(), e.getY()));
        } else if(e.isSecondaryButtonDown()) {
            test.deselect(new Vector(e.getX(),e.getY()));
        }
    }
    public void autoSetCanvasDim(){

        if (panneau.getWidth()>panneau.getHeight()) {
            canvas.setScaleX(panneau.getHeight() / panneau.getPrefHeight());

            canvas.setScaleY(panneau.getHeight() / panneau.getPrefHeight());

        }else{
            canvas.setScaleY(panneau.getWidth() / panneau.getPrefWidth());

            canvas.setScaleX(panneau.getWidth() / panneau.getPrefWidth());

        }
    }
    public void dumbAutoSetCanvasDim(){

        canvas.setScaleY(panneau.getHeight() / panneau.getPrefHeight());
        canvas.setScaleX(panneau.getWidth() / panneau.getPrefWidth());


    }
    @FXML
    public void onButtonPLS(){
        test.getLemmingsList().forEach(l->{
            if(l.isSelected()){
                l.setState(LemmingsStates.Pls);
            }
        } );
    }
    @FXML
    public void onButtonConstruct(){
        test.getLemmingsList().forEach(l->{
            if(l.isSelected()){
                l.setState(LemmingsStates.Construct);
            }
        } );
    }


    @FXML
    public void onButtonVomir(){
        test.getLemmingsList().forEach(l->{
            if(l.isSelected()){
                l.setState(LemmingsStates.Vomit);
            }
        } );
    }

    @FXML
    public void accelWorld(){
        timeSetter.setTimeSpeed(2);
    }
}

