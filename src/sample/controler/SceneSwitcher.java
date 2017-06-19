package sample.controler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by naej on 19/06/17.
 */
public class SceneSwitcher {
    private static final String FXMLfolder = "sample/view/" ;


    /**
     * Method switching to another scene in the same stage
     * @param sceneName String corresponding to the name of the FXML, without file extension
     * @param stage the stage on which we operate. Gotta find a better way to access it.
     * @return loader the FXML loader of the scene
     */
    public static FXMLLoader switchToScene(String sceneName, Stage stage) throws IOException
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
