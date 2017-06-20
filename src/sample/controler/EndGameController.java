package sample.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.model.GameSession;
import sample.model.StudentData;

import java.io.IOException;

import static sample.controler.SceneSwitcher.switchToScene;

/**
 * controller for the endgame
 * @author William
 * @since 20/06/2017
 */
public class EndGameController
{
    @FXML
    private Button endGameBackButton;

    @FXML
    private Label endGameMentionLabel;

    @FXML
    private Label endGameScoreLabel;


    /**
     * Start method called at the begining. Used to set everything in the rend game screen
     * @param session the game session on which the game finished
     */
    public void start(GameSession session)
    {
        int maxLevel = MainGameController.MAXLEVEL;
        /*the score*/
        double score = 0;
        /*the number of lemmings who validated their semester*/
        int lemmingsOK = 0;

        for(StudentData d : session.getStudents())
        {
            if(d.getExamsAttended() >= maxLevel - 1)
            {
                ++lemmingsOK;
                score += d.getExamsAttended();
            }
        }
        /*the proportion of student which should validate to have won the game*/
        double proportion = session.getStudents().size()/2;

        for(StudentData d : session.getStudents())
        {
            if(d.getExamsAttended() >= maxLevel - 1)
            {
                score += d.getExamsAttended() * (lemmingsOK/proportion);
            }
        }
        if(lemmingsOK >= Math.floor(proportion))
        {
            endGameMentionLabel.setText("Congrats !");
        }
        else
        {
            endGameMentionLabel.setText("You are a disgrace.");
        }


        endGameScoreLabel.setText("Score : " + score);
    }


    /**
     * go back to the main menu screen when the endGameBackButton is clicked
     * @param event the click event
     */
    @FXML
    private void backToMainMenu(ActionEvent event)
    {
        Stage s = (Stage)((Button)event.getSource()).getScene().getWindow();
        try
        {
            switchToScene("mainMenu",s);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }





}
