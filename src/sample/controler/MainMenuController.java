package sample.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.model.GameSession;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static sample.controler.SceneSwitcher.switchToScene;
import static sample.model.SoundManager.getSoundManager;

/**
 * Class used to control the menu : navigation, loading saves, launching games, changing parameters...
 * @author William
 * @since 15/05/2017
 */

public class MainMenuController
{

    /*references to FXML elements*/
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
    @FXML
    private TextField settingsSFXTextField;
    @FXML
    private TextField settingsBGMTextField;
    @FXML
    private Button settingsSFXPlusButton;
    @FXML
    private Button settingsBGMPlusButton;
    @FXML
    private Button settingsSFXMinusButton;
    @FXML
    private Button settingsBGMMinusButton;
    @FXML
    private Button loadGameChooseButton;
    /**reference to the GameSession we will launch*/
    private GameSession savegame;
    @FXML
    private TextField loadGameNameTextField;
    @FXML
    private TextField loadGameLevelTextField;
    @FXML
    private TextField newGameNameTextField;
    @FXML
    private Button getLoadGameLoadButton;


    public MainMenuController() throws IOException
    {

    }

    @FXML
    private void initialze() throws IOException
    {
    }

    /**
     * Method detecting which button activated and switching to the right scene
     * @param event event triggering the method
    * */
    @FXML
    private void switchSceneOnButtonAction(ActionEvent event) throws IOException
    {
        //we start by selecting detecting which FXML to use
        Button source = (Button) event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        if (source == newGameButton )
        {
            switchToScene("newGameMenu", stage);
        }
        else if (source == loadGameButton)
        {
            savegame = null;
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
        else if(source == newGameLaunch || source == loadGameLoadButton)
        {
            FXMLLoader loader = switchToScene("mainGame",stage);
            stage.setMaximized(true);

            try {
                ((MainGameController) loader.getController()).start(savegame);
            } catch (URISyntaxException | UnsupportedAudioFileException | LineUnavailableException e) {
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
     * increment the SFX volume of +0.1
     */
    @FXML
    private void plusSFXVolume()
    {
        getSoundManager().setSFXVolume(getSoundManager().getSFXVolume() + 0.1);
        settingsSFXTextField.setText(Double.toString(getSoundManager().getSFXVolume()));
    }

    /**
     * increment the SFX volume of -0.1
     */
    @FXML
    private void minusSFXVolume()
    {
        getSoundManager().setSFXVolume(getSoundManager().getSFXVolume() - 0.1);
        settingsSFXTextField.setText(Double.toString(getSoundManager().getSFXVolume()));
    }


    /**
     * increment the BGM volume of +0.1
     */
    @FXML
    private void plusBGMVolume()
    {
        getSoundManager().setSFXVolume(getSoundManager().getBGMVolume() + 0.1);
        settingsSFXTextField.setText(Double.toString(getSoundManager().getBGMVolume()));
    }

    /**
     * increment the SFX volume of -0.1
     */
    @FXML
    private void minusBGMVolume()
    {
        getSoundManager().setSFXVolume(getSoundManager().getBGMVolume() - 0.1);
        settingsSFXTextField.setText(Double.toString(getSoundManager().getBGMVolume()));
    }


    /**
     * Method allowing to choose the saveGame with a FileChoose popup
     */
    @FXML
    private void chooseSaveGame()
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select your save game");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("game save file","*.kkk"));
        File f = fc.showOpenDialog(new Stage());
        if(f.getName().endsWith(".kkk"))
        {
            savegame = new GameSession(f);
            loadGameNameTextField.setText(savegame.getPlayerName());
            loadGameLevelTextField.setText(""+savegame.getLevel());

        }
        else
        {
            loadGameNameTextField.setText("ERROR");
            loadGameLevelTextField.setText("ERROR");
        }

    }

    /**Method triggered by the newGameLaunchButton. Start a new game with the name specified in newGameNAmeTextField
     * if no name is specified, we use the Van Damme.
     * The GameSession is saved at the default (and only for now) save directory
     * @param event click that triggered the event*/
    @FXML
    private void startNewGame(ActionEvent event) throws IOException
    {
        String name;
        if(newGameNameTextField.getText().equalsIgnoreCase(""))
        {
            name = "JeanClaudeVanDamme";
        }
        else
        {
            name = newGameNameTextField.getText();
        }
        try
        {
            savegame = new GameSession(name,7);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        savegame.saveGameSession();
        switchSceneOnButtonAction(event);
    }

    /**Start a game from an already existing GameSession selected with the chooseSaveGame method.
     * If no saveGAme is already selected, we gently inquire about the health of the user.
     * @param event the click which triggered this method*/
    @FXML
    private void loadGame(ActionEvent event) throws IOException
    {
        if(savegame != null)
        {
            loadGameLoadButton.setText("Load");
            switchSceneOnButtonAction(event);
        }
        else
        {
            loadGameLoadButton.setText("U DRUNK ?");
        }
    }


}
