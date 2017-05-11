package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.omg.CORBA.Environment;


public class MainGameController {
    @FXML
    private Canvas canvas;

    @FXML
    private BorderPane panneau;

    private Image loadedImg;
    private WritableImage modifiable;
    private Sprite test;

    GraphicsContext gc;
    MainGameUpdater timeSetter;

    public void start(){
        gc = canvas.getGraphicsContext2D();

        timeSetter = new MainGameUpdater();
        timeSetter.start(this);

        test = new Sprite("resources/images/omaia.png");
        test.setPosition(10,10);

        test.drawImage(gc);


    }

    public void update(double deltaTime) {
       // System.out.println("FPS : "+ 1/deltaTime );
       autoSetCanvasDim();
    }

    public void onMouseClick (MouseEvent e){

        //FORMULA: (int)(mouse.getX()/gcScale-imagePositionX)-1 (all coordinates are canvas relative) gcScale should be left on 1, and you should modify only CanvasScale
//        System.out.println(e.getX()+" : "+e.getY());
        test.modifyPixelCanvasRef((int)((e.getX())),(int)((e.getY())), Color.color(1, 0.0078, 0));

        // writer.setColor((int)((e.getX()-30)),(int)((e.getY()-30)), Color.color(1, 0.0078, 0));

      //  notCenteredDrawImage(new ImageView(modifiable),30,30,gc);
        test.drawImage(gc);
    }


    public void autoSetCanvasDim(){

        if (panneau.getWidth()>panneau.getHeight()) {
            canvas.setScaleX(panneau.getHeight() / panneau.getPrefHeight());

            canvas.setScaleY(panneau.getHeight() / panneau.getPrefHeight());

        }else{
            canvas.setScaleY(panneau.getWidth() / panneau.getPrefWidth());

            canvas.setScaleX(panneau.getWidth() / panneau.getPrefWidth());

        }
    }
    public void dumbAutoSetCanvasDim(){

        canvas.setScaleY(panneau.getHeight() / panneau.getPrefHeight());
        canvas.setScaleX(panneau.getWidth() / panneau.getPrefWidth());


    }
}