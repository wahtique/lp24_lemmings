package sample.controler;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.model.*;
import sample.view.Drawer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static sample.model.SoundManager.getSoundManager;


public class MainGameController {
    @FXML
    private Canvas canvas;

    @FXML
    private Pane panneau;

    @FXML
    private Button Vomit;
    @FXML
    private Button Pls;
    @FXML
    private Button Construct;


    // private Color currentColor = Color.color(1, 0.0078, 0);
    private LoadableLevel currentLevel;

    private Drawer drawer;

    private AnimatedSprite anim;
    MainGameUpdater timeSetter;




    public void start(GameSession savegame) throws IOException, URISyntaxException, LineUnavailableException, UnsupportedAudioFileException
    {

        drawer = Drawer.getDrawer();
        drawer.setCanvas(canvas);
        if (timeSetter == null) {
            timeSetter = new MainGameUpdater();
            timeSetter.start(this);
        }
        currentLevel = new LoadableLevel(savegame,"/resources/levels/testlevel1");
        Lemmings roger = new Lemmings(new Vector(120,20), new Vector(50,0), currentLevel);
        Lemmings paniou = new Lemmings(new Vector(50,20), new Vector(50,0), currentLevel);
        currentLevel.getLemmingsNotSpawned().add(roger);
        currentLevel.getLemmingsNotSpawned().add(paniou);


        Pls.setText("Pls :"+ currentLevel.Pls);
        Construct.setText("Construct :"+ currentLevel.Construct);
        Vomit.setText("Vomit :"+ currentLevel.Vomits);

        getSoundManager().setBGM("/resources/Sound/bgm.wav");
        getSoundManager().playBGM();

        getSoundManager().setSFXVolume(1);


    }

    public void update(double deltaTime) {
       //System.out.println("FPS : "+ 1/deltaTime );
       autoSetCanvasDim();
//       anim.update(deltaTime);
       currentLevel.update(deltaTime);
       drawer.draw();
     //  currentLevel.drawLevel(canvas.getGraphicsContext2D());

    }

    public void onMouseClick (MouseEvent e) {
        //  currentLevel.getLemmingsList().stream().findFirst().get().setPosition(new Vector(e.getX(),e.getY()));
        if (e.isPrimaryButtonDown()) {
            //currentLevel.getVomits().add(new Vomit(new Vector(e.getX(), e.getY())));
            currentLevel.select(new Vector(e.getX(), e.getY()));
        } else if(e.isSecondaryButtonDown()) {
            currentLevel.deselect(new Vector(e.getX(),e.getY()));
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
        currentLevel.getLemmingsList().forEach(l->{
            if(l.isSelected() && currentLevel.Pls > 0){
                if (l.setState(LemmingsStates.Pls)){
                    currentLevel.Pls -= 1;
                    Pls.setText("Pls :"+ currentLevel.Pls);

                }
            }
        } );
    }
    @FXML
    public void onButtonConstruct(){
        currentLevel.getLemmingsList().forEach(l->{
            if(l.isSelected()&& currentLevel.Construct > 0){
                if (l.setState(LemmingsStates.Construct)){
                    currentLevel.Construct -= 1;
                    Construct.setText("Construct :"+ currentLevel.Construct);


                }

            }
        } );
    }


    @FXML
    public void onButtonVomir(){
        currentLevel.getLemmingsList().forEach(l->{
            if(l.isSelected() && currentLevel.Vomits > 0){
                if (l.setState(LemmingsStates.Vomit)) {
                    try {
                        getSoundManager().playSFX("/resources/Sound/sfxVomi.wav");
                    } catch (Exception e) {

                    }
                    currentLevel.Vomits -= 1;
                    Vomit.setText("Vomit :"+ currentLevel.Vomits);
                }


            }
        } );
    }

    @FXML
    public void accelWorld(){
        timeSetter.setTimeSpeed(2);
    }

    public void restartLevel() throws Exception {
        /*Drawer.getDrawer().clearDrawer();
        *//*start();*/
    }
}
