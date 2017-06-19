package sample.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            stage.setMaximized(true);

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


    @FXML
    private void plusSFXVolume(ActionEvent event)
    {
        getSoundManager().setSFXVolume(getSoundManager().getSFXVolume() + 0.1);
        settingsSFXTextField.setText(Double.toString(getSoundManager().getSFXVolume()));
    }

    @FXML
    private void minusSFXVolume(ActionEvent event)
    {
        getSoundManager().setSFXVolume(getSoundManager().getSFXVolume() - 0.1);
        settingsSFXTextField.setText(Double.toString(getSoundManager().getSFXVolume()));
    }


    @FXML
    private void plusBGMVolume(ActionEvent event)
    {
        getSoundManager().setSFXVolume(getSoundManager().getBGMVolume() + 0.1);
        settingsSFXTextField.setText(Double.toString(getSoundManager().getBGMVolume()));
    }

    @FXML
    private void minusBGMVolume(ActionEvent event)
    {
        getSoundManager().setSFXVolume(getSoundManager().getBGMVolume() - 0.1);
        settingsSFXTextField.setText(Double.toString(getSoundManager().getBGMVolume()));
    }

    @FXML
    private void chooseSaveGame(ActionEvent event)
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
        switchSceneOnButtonAction(event);
    }

}
