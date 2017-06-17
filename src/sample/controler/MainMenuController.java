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

/**
 * @author William
 * @since 15/05/2017
 */
public class MainMenuController
{
    private static final String FXMLfolder = "sample/view/" ;

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


    /**
     * Method switching to another scene in the same stage
     * @param sceneName String corresponding to the name of the FXML, without file extension
     * @param stage the stage on which we operate. Gotta find a better way to access it.
     * @return loader the FXML loader of the scene
    */
    @FXML
    private FXMLLoader switchToScene(String sceneName, Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        /*FileInputStream fin = new FileInputStream(FXMLfolder + sceneName +".fxml");*/
        Parent root = loader.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(FXMLfolder + sceneName +".fxml"));
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.show();
        /*fin.close();*/
        return loader;
    }

}
