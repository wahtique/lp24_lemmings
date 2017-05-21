package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

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


    public MainMenuController()
    {
    }

    @FXML
    private void initialze(){}

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
            switchToScene("sample",stage);
        }
        else
        {
            //should be only the diverse back to main menu button handled in this last case
            switchToScene("MainMenu",(Stage)source.getScene().getWindow());
        }

    }


    /**
     * Method switching to another scene in the same stage
     * @paranm sceneName String corresponding to the name of the FXML, without file extension
     * @param stage the stage on which we operate. Gotta find a better way to access it.
    */
    @FXML
    private void switchToScene(String sceneName, Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fin = new FileInputStream("src/sample/" + sceneName +".fxml");
        Parent root = loader.load(fin);
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.show();
        fin.close();
    }

}
