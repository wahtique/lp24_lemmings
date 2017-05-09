package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class MainGameController {
    @FXML
    private Canvas canvas;

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
        notCenteredDrawImage(new ImageView(modifiable),10,10,gc);

    }

    public void update(double deltaTime) {
       // System.out.println("FPS : "+ 1/deltaTime );
    }

    public void onMouseClick (MouseEvent e){
        PixelWriter writer = modifiable.getPixelWriter();


        // writer.setColor((int)(e.getX() -200),(int)(e.getY()-200), Color.color(1, 0.0078, 0));
        //writer.setColor((int)(loadedImg.getWidth())-1,(int)(loadedImg.getHeight())-1, Color.color(1, 0.0078, 0));
        System.out.println((int)(e.getX())-1);
        //FORMULA: (int)(mouse.getX()-imagepositionX)-1 (all coordinates are canvas relative)
        writer.setColor((int)(e.getX()-30)-1,(int)(e.getY()-30)-1, Color.color(1, 0.0078, 0));

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
}