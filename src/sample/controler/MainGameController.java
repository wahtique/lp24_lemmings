package sample.controler;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
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
    private Level test;

    private Drawer drawer;

    private AnimatedSprite anim;
    MainGameUpdater timeSetter;




    public void start() throws IOException, URISyntaxException, LineUnavailableException, UnsupportedAudioFileException
    {

        drawer = Drawer.getDrawer();
        drawer.setCanvas(canvas);
        if (timeSetter == null) {
            timeSetter = new MainGameUpdater();
            timeSetter.start(this);
        }
        test = new Level("/resources/levels/testlevel1",new ArrayList<Lemmings>());
        Lemmings roger = new Lemmings(new Vector(120,20), new Vector(50,0),test);
        Lemmings paniou = new Lemmings(new Vector(50,20), new Vector(50,0),test);
        test.getLemmingsNotSpawned().add(roger);
        test.getLemmingsNotSpawned().add(paniou);


        Pls.setText("Pls :"+test.Pls);
        Construct.setText("Construct :"+test.Construct);
        Vomit.setText("Vomit :"+test.Vomits);

        getSoundManager().setBGM("/resources/Sound/bgm.wav");
        getSoundManager().playBGM();

        getSoundManager().setSFXVolume(1);


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
            if(l.isSelected() && test.Pls > 0){
                if (l.setState(LemmingsStates.Pls)){
                    test.Pls -= 1;
                    Pls.setText("Pls :"+test.Pls);

                }
            }
        } );
    }
    @FXML
    public void onButtonConstruct(){
        test.getLemmingsList().forEach(l->{
            if(l.isSelected()&& test.Construct > 0){
                if (l.setState(LemmingsStates.Construct)){
                    test.Construct -= 1;
                    Construct.setText("Construct :"+test.Construct);


                }

            }
        } );
    }


    @FXML
    public void onButtonVomir(){
        test.getLemmingsList().forEach(l->{
            if(l.isSelected() && test.Vomits > 0){
                if (l.setState(LemmingsStates.Vomit)) {
                    try {
                        getSoundManager().playSFX("/resources/Sound/sfxVomi.wav");
                    } catch (Exception e) {

                    }
                    test.Vomits -= 1;
                    Vomit.setText("Vomit :"+test.Vomits);
                }


            }
        } );
    }

    @FXML
    public void accelWorld(){
        timeSetter.setTimeSpeed(2);
    }

    public void restartLevel() throws Exception {
        Drawer.getDrawer().clearDrawer();
        start();
    }

    public void onKeyPressed(KeyEvent e){

        System.out.println(e.getText());
        switch (e.getText()){
            case "a":
                onButtonPLS();
                break;
            case "z":
                onButtonConstruct();
                break;
            case "e":
                onButtonVomir();
                break;
            case "²":
                test.getLemmingsList().forEach(l->l.setSelected(false));
                break;
            default:break;
        }
    }
}
