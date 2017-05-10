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


public class MainGameController {
    @FXML
    private Canvas canvas;

    @FXML
    private BorderPane panneau;

    private Image loadedImg;
    private WritableImage modifiable;

    GraphicsContext gc;
    MainGameUpdater timeSetter;

    public void start(){
        gc = canvas.getGraphicsContext2D();

        timeSetter = new MainGameUpdater();
        timeSetter.start(this);

        //this is how loading images works
        loadedImg = new Image("resources/images/omaia.png");
        modifiable = new WritableImage(loadedImg.getPixelReader(),(int)loadedImg.getWidth(),(int)loadedImg.getHeight());

        //  System.out.println(loadedImg.getPixelReader().getColor(0,0));
        System.out.println(loadedImg.getWidth());
        //TODO: dynamic scaling with the resize of the window
        canvas.setScaleX(2);
        canvas.setScaleY(2);

        //gc.scale(2, 2);
        notCenteredDrawImage(new ImageView(modifiable),10,10,gc);

    }

    public void update(double deltaTime) {
       // System.out.println("FPS : "+ 1/deltaTime );
       autoSetCanvasDim();
    }

    public void onMouseClick (MouseEvent e){
        PixelWriter writer = modifiable.getPixelWriter();


        // writer.setColor((int)(e.getX() -200),(int)(e.getY()-200), Color.color(1, 0.0078, 0));
        //writer.setColor((int)(loadedImg.getWidth())-1,(int)(loadedImg.getHeight())-1, Color.color(1, 0.0078, 0));
        
        //FORMULA: (int)(mouse.getX()/gcScale-imagePositionX)-1 (all coordinates are canvas relative) gcScale should be left on 1, and you should modify only CanvasScale
        writer.setColor((int)((e.getX()-30)),(int)((e.getY()-30)), Color.color(1, 0.0078, 0));

        notCenteredDrawImage(new ImageView(modifiable),30,30,gc);
        //System.out.println("Clic at "+ e.getX() + " : " + e.getY());
        System.out.println("Width: "+ loadedImg.getWidth() + " Heigth : " + loadedImg.getHeight());
    //    System.out.println(""+ loadedImg.getPixelReader(). + " : " + e.getY());

        //this is how you can print an image on the canvas

    //    centeredDrawImage(new ImageView(modifiable),e.getX(),e.getY(),gc);
     //   ImageView test = new ImageView(loadedImg);

    }

    public void centeredDrawImage(Image img, double x, double y, GraphicsContext gc){
        gc.drawImage(img,x-img.getWidth()/2,y-img.getHeight()/2);
    }
    public void centeredDrawImage(ImageView img, double x, double y, GraphicsContext gc){
        img.setX(x-img.getImage().getWidth()/2);
        img.setY(y-img.getImage().getHeight()/2);
        gc.drawImage(img.getImage(),img.getX(),img.getY());
    }
    public void notCenteredDrawImage(ImageView img, double x, double y, GraphicsContext gc){
        img.setX(x);
        img.setY(y);
        gc.drawImage(img.getImage(),img.getX(),img.getY());
    }

    public void autoSetCanvasDim(){
        System.out.println((panneau.getHeight() / panneau.getPrefHeight()) * (panneau.getPrefHeight() / panneau.getPrefWidth()) );
        if (panneau.getWidth()>panneau.getHeight()) {
            canvas.setScaleX(panneau.getHeight() / panneau.getPrefHeight());

            canvas.setScaleY(panneau.getHeight() / panneau.getPrefHeight());

        }else{
            canvas.setScaleY(panneau.getWidth() / panneau.getPrefWidth());

            canvas.setScaleX(panneau.getWidth() / panneau.getPrefWidth());

        }
    }
    public void dumbAutoSetCanvasDim(){
        System.out.println( panneau.getPrefHeight() / panneau.getPrefWidth());
        canvas.setScaleY(panneau.getHeight() / panneau.getPrefHeight());
        canvas.setScaleX(panneau.getWidth() / panneau.getPrefWidth());


    }
}