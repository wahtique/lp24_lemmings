package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


/**
 * Created by naej on 11/05/17.
 */
public class Sprite {
    private ImageView baseImage;
    private WritableImage writeImage;


    public Sprite(String url){
        Image image = new Image(url);
        writeImage = new WritableImage(image.getPixelReader(),(int)image.getWidth(),(int)image.getHeight());
        baseImage = new ImageView(writeImage);
    }
    public boolean modifyPixel(int x,int y,Color color){
        if (x<0 || y<0){
            return false;
        }else {
            if (writeImage.getWidth() > x && writeImage.getHeight() > y) {
                writeImage.getPixelWriter().setColor(x, y, color);
                return true;
            } else {
                return false;
            }
        }

    }
    public boolean modifyPixelCanvasRef(int x,int y,Color color){
        x = (int) (x-baseImage.getX());
        y = (int) (y-baseImage.getY());

        return modifyPixel(x,y,color);
    }

    public void drawImage(GraphicsContext gc){
        gc.drawImage(baseImage.getImage(),baseImage.getX(),baseImage.getY());
    }

    public void setPosition(double x, double y){
        baseImage.setX(x);
        baseImage.setY(y);
    }



}
