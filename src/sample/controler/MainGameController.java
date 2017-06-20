package sample.controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.model.*;
import sample.view.Drawer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;
import java.net.URISyntaxException;

import static sample.controler.SceneSwitcher.switchToScene;
import static sample.model.SoundManager.getSoundManager;


/**
 * Class used to control and run the main game
 * @author Jean
 */
public class MainGameController {
    /**The canvas where the game is drawn  */
    @FXML
    private Canvas canvas;

    /**The panel where all the elements are, used to compute the resizing     */
    @FXML
    private Pane panneau;

    /**The differents buttons */
    @FXML
    private Button Vomit;
    @FXML
    private Button Pls;
    @FXML
    private Button Construct;

    /**The model of the level */
    private LoadableLevel currentLevel;

    /**The object used to draw in the canvas */
    private Drawer drawer;

    /**The object that will call the update function, allowing us to set the time speed */
    private MainGameUpdater timeSetter;

    /**The save we will load */
    private GameSession savegame;

    /**Boolean used to check if the game will normally be ended, used to dedug some things */
    private boolean ended = false;


    public final static int MAXLEVEL = 3;
    /**
     * Method used to initialise the game
     * @param savegame The save we will load
     */
    public void start(GameSession savegame) throws IOException, URISyntaxException, LineUnavailableException, UnsupportedAudioFileException
    {
        ended = false;
        this.savegame = savegame;
        drawer = Drawer.getDrawer();
        drawer.setCanvas(canvas);
        if (timeSetter == null) {
            timeSetter = new MainGameUpdater();
            timeSetter.start(this);
        }
        switch (savegame.getLevel()){
            case 1:
                currentLevel = new LoadableLevel(savegame,"/resources/levels/lvl1");
                break;
            case 2:
                currentLevel = new LoadableLevel(savegame,"/resources/levels/lvl2");
                break;
            case 3:
                currentLevel = new LoadableLevel(savegame,"/resources/levels/lvl3");
                break;
            default:
                currentLevel = new LoadableLevel(savegame,"/resources/levels/lvl3");
                break;

        }


        Pls.setText("Pls :"+ currentLevel.Pls);
        Construct.setText("Construct :"+ currentLevel.Construct);
        Vomit.setText("Vomit :"+ currentLevel.Vomits);

        getSoundManager().setBGM("/resources/Sound/bgm.wav");
        getSoundManager().playBGM();



    }

    /**
     * The game updater function
     * @param deltaTime the time passed since the last frame, in seconds
     */
    public void update(double deltaTime) {
       //System.out.println("FPS : "+ 1/deltaTime );
       autoSetCanvasDim();
       currentLevel.update(deltaTime);
       drawer.draw();
        if (ended)
            System.out.println("wut");

    }

    /**
     * Actions that will take effect on click
     * @param e the mouse event
     */
    public void onMouseClick (MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            currentLevel.toggleSelect(new Vector(e.getX(), e.getY()));
        } else if(e.isSecondaryButtonDown()) {
            currentLevel.deselect(new Vector(e.getX(),e.getY()));
        }
    }

    /**
     * Fuction used to resize automatically the canvas
     */
    public void autoSetCanvasDim(){

        if (panneau.getWidth()>panneau.getHeight()) {
            canvas.setScaleX(panneau.getHeight() / panneau.getPrefHeight());

            canvas.setScaleY(panneau.getHeight() / panneau.getPrefHeight());

        }else{
            canvas.setScaleY(panneau.getWidth() / panneau.getPrefWidth());

            canvas.setScaleX(panneau.getWidth() / panneau.getPrefWidth());

        }
    }

    @Deprecated
    public void dumbAutoSetCanvasDim(){

        canvas.setScaleY(panneau.getHeight() / panneau.getPrefHeight());
        canvas.setScaleX(panneau.getWidth() / panneau.getPrefWidth());
    }

    /**
     * That will happen if you press on the PLS button
     */
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

    /**
     * That will happen if you press on the Construct button
     */
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


    /**
     * That will happen if you press on the Vomit button
     */
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


    /**
     * That will happen if you press on the ">> x2" button
     */
    @FXML
    public void accelWorld(){
        timeSetter.setTimeSpeed(2);
    }

    /**
     * Function used to end a game
     * @throws Exception
     */
    public void quitLevel() throws Exception {
        Drawer.getDrawer().clearDrawer();
        if (savegame.getLevel()<MAXLEVEL) {
            savegame.setLevel(savegame.getLevel() + 1);
            FXMLLoader loader = switchToScene("interLevel", (Stage) canvas.getScene().getWindow());
            ((InterLevelController) loader.getController()).start(savegame);
        }
        else
        {
            FXMLLoader loader = switchToScene("endGame", (Stage) canvas.getScene().getWindow());
            ((EndGameController) loader.getController()).start(savegame);
        }

        ended = true;
        timeSetter.stop();
        System.gc();
        System.runFinalization();
    }

    /**
     * Keyboard shortcuts
     * @param e keyboard event
     */
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
            case "²"://tout sélectionner
                currentLevel.getLemmingsList().forEach(l->l.setSelected(false));
                break;
            case "+":
                accelWorld();
                break;
            case "i": //inversion de la sélection
                currentLevel.getLemmingsList().forEach(l->l.setSelected(!(l.isSelected())));
            default:break;
        }
    }
}
