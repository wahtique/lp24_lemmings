package sample.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.model.GameSession;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static sample.controler.SceneSwitcher.switchToScene;

/**Controller class for the interLevel scene
 * @author William
 * @since 19/06/2017
 */
public class InterLevelController
{
    /**Button allowing to replay the current level*/
    @FXML
    private Button interLevelReplayButton;
    /**Button leading to the next level.*/
    @FXML
    private Button interLevelNextButton;

    /**Button leading back to the Main menu*/
    @FXML
    private Button interLevelMainMenuButton;
    /**Current GameSession used in the level just played*/
    private GameSession session;

    @FXML
    private Label interLevelStatsLabel;

    /**method initializing the scene; Should be called first
     * @param session current GameSession*/
    public void start(GameSession session)
    {
        this.session = session;
        interLevelStatsLabel.setText(session.toDisplayString());

    }

    /**Method to replay the level we just left. It should reload the GameSession so that the changes applied when played doesn't exist anymore
     * @param event the click event*/
    @FXML
    private void replayLevel(ActionEvent event) throws IOException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException
    {
        session = new GameSession(new File("savedGames/"+session.getPlayerName()+".kkk"));
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = switchToScene("mainGame",stage);
        stage.setMaximized(true);
        ((MainGameController) loader.getController()).start(session);
    }

    /**Method allowing to play the next Level. Save the current GameSession before using the same in the next.
     * @param event click event*/
    @FXML
    private void nextLevel(ActionEvent event) throws URISyntaxException, UnsupportedAudioFileException, LineUnavailableException, IOException
    {
        session.saveGameSession();
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = switchToScene("mainGame",stage);
        stage.setMaximized(true);
        ((MainGameController) loader.getController()).start(session);
    }

    /**Method leading back to the main Menu. By default, save the current game
     * @param event click event*/
    @FXML
    private void backToMainMenu(ActionEvent event)
    {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try
        {
            switchToScene("mainMenu",stage);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }







}
