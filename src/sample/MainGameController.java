package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
      //  System.out.println(loadedImg.getPixelReader().getColor(0,0));
        System.out.println(loadedImg.getWidth());


    }

    public void update(double deltaTime) {
       // System.out.println("FPS : "+ 1/deltaTime );
    }

    public void onMouseClick (MouseEvent e){
        //System.out.println("Clic at "+ e.getX() + " : " + e.getY());

        //this is how you can print an image on the canvas

        centeredDrawImage(loadedImg,e.getX(),e.getY(),gc);
        ImageView test = new ImageView(loadedImg);

    }

    public void centeredDrawImage(Image img, double x, double y, GraphicsContext gc){
        gc.drawImage(img,x-img.getWidth()/2,y-img.getHeight()/2);
    }
}