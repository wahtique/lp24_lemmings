package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;


public class MainGameController {
    @FXML
    private Canvas canvas;

    private Image loadedImg;


    GraphicsContext gc;
    MainGameUpdater timeSetter;

    public void start(){
        gc = canvas.getGraphicsContext2D();

        timeSetter = new MainGameUpdater();
        timeSetter.start(this);

        //this is how loading images works
        loadedImg = new Image("resources/images/omaia.png");



    }

    public void update(double deltaTime) {
       // System.out.println("FPS : "+ 1/deltaTime );
    }

    public void onMouseClick (MouseEvent e){
        //System.out.println("Clic at "+ e.getX() + " : " + e.getY());

        //this is how you can print an image on the canvas
        gc.drawImage(loadedImg,e.getX(),e.getY());
    }
}