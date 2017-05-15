package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        loader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent root = (Parent) loader.load(getClass().getResource("sample.fxml").openStream());

        primaryStage.setTitle("Hello World");

        primaryStage.setScene(new Scene(root, 600, 400));

        primaryStage.show();

        ((MainGameController) loader.getController()).start();

    }


    public static void main(String[] args) throws Exception
    {
        GameSession g = new GameSession("William",3);
        System.out.println(g);
        g.saveGameSession();

        GameSession gbis = new GameSession("William");
        System.out.println(gbis);
    }


}
