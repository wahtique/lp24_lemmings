package sample.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.model.GameSession;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static sample.controler.SceneSwitcher.switchToScene;

/**
 * @author William
 * @since 19/06/2017
 */
public class InterLevelController
{
    @FXML
    private Button interLevelReplayButton;
    @FXML
    private Button interLevelNextButton;
    @FXML
    private Button interLevelMainMenuButton;

    private GameSession session;

    public void start(GameSession session)
    {
        this.session = session;
    }

    @FXML
    private void replayLevel(ActionEvent event) throws IOException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException
    {
        session = new GameSession(new File("savedGames/"+session.getPlayerName()+".kkk"));
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = switchToScene("mainGame",stage);
        stage.setMaximized(true);
        ((MainGameController) loader.getController()).start(session);
    }

    @FXML
    private void nextLevel(ActionEvent event) throws URISyntaxException, UnsupportedAudioFileException, LineUnavailableException, IOException
    {
        session.saveGameSession();
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = switchToScene("mainGame",stage);
        stage.setMaximized(true);
        ((MainGameController) loader.getController()).start(session);
    }

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
