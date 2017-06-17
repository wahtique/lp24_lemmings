package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.SoundManager;

public class Main extends Application
{

  //  private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/mainMenu.fxml"));
        primaryStage.setTitle("UTBM DRUNKARDS EXTREM SUCCESS PLAN");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

    }


    public static void main(String[] args) throws Exception
    {
       /* GameSession g = new GameSession("William",3);
        System.out.println(g);
        g.saveGameSession();

        GameSession gbis = new GameSession("William");
        System.out.println(gbis);*/
        launch(args);
    }


}