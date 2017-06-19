package sample.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.model.SoundManager;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

import static sample.controler.SceneSwitcher.switchToScene;

/**
 * @author William
 * @since 15/05/2017
 */
public class MainMenuController
{


    @FXML
    private Button newGameButton;
    @FXML
    private Button loadGameButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button creditsButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button newGameBackButton;
    @FXML
    private Button loadGameLoadButton;
    @FXML
    private Button loadGameBackButton;
    @FXML
    private Button settingsBackButton;
    @FXML
    private Button creditsScreenBackButton;
    @FXML
    private Button newGameLaunch;


    public MainMenuController() throws IOException
    {

    }

    @FXML
    private void initialze() throws IOException
    {
    }

    /**
     * Method detecting which button activated and calling switchToScene with the right parameter
     * @param event event triggering the method
    * */
    @FXML
    private void switchSceneOnButtonAction(ActionEvent event) throws IOException
    {
        /*sm.playSFX("/resources/Sound/bgm.wav");*/
        //we start by selecting detecting which FXML to use
        Button source = (Button) event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        if (source == newGameButton )
        {
            switchToScene("newGameMenu", stage);
        }
        else if (source == loadGameButton)
        {
            switchToScene("loadGameMenu",stage);
        }
        else if(source == settingsButton)
        {
            switchToScene("settingsMenu",stage);
        }
        else if(source == creditsButton)
        {
            switchToScene("creditsScreen",stage);
        }
        else if(source == quitButton)
        {
            stage.close();
        }
        else if(source == newGameLaunch)
        {
            FXMLLoader loader = switchToScene("mainGame",stage);

            try {
                ((MainGameController) loader.getController()).start();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e)
            {
                e.printStackTrace();
            } catch (LineUnavailableException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            //should be only the diverse back to main menu button handled in this last case
            switchToScene("mainMenu",(Stage)source.getScene().getWindow());
        }

    }





}
