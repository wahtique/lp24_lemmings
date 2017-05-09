package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class MainGameController {
    @FXML
    private Canvas canvas;


    GraphicsContext gc;
    MainGameUpdater timeSetter;

    public void start(){
        gc = canvas.getGraphicsContext2D();

        timeSetter = new MainGameUpdater();
        timeSetter.start(this);



    }

    public void update(double deltaTime) {
        System.out.println("FPS : "+ 1/deltaTime );
    }
}