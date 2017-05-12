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

    protected float layer;
    protected ImageView renderedImage;
    protected WritableImage writeImage;

    public Sprite(){
        renderedImage = null;
        writeImage = null;
    }

    public Sprite(String url){
        Image image = new Image(url);
        writeImage = new WritableImage(image.getPixelReader(),(int)image.getWidth(),(int)image.getHeight());
        renderedImage = new ImageView(writeImage);
    }




    public boolean modifyPixelCanvasRef(int x,int y,Color color){
        x = (int) (x- renderedImage.getX());
        y = (int) (y- renderedImage.getY());

        return modifyPixel(x,y,color);
    }

    public void drawImage(GraphicsContext gc){
        gc.drawImage( renderedImage.getImage(), renderedImage.getX(), renderedImage.getY());
    }

    public void setPosition(double x, double y){
         renderedImage.setX(x);
         renderedImage.setY(y);
    }

    public Color getPixelColorCanvasRef(int x,int y){
        x = (int) (x- renderedImage.getX());
        y = (int) (y- renderedImage.getY());

        return getPixelColor(x,y);
    }

    public void flipX(){
        //TODO
        //renderedImage.setScaleX(-renderedImage.getScaleX());
    }


    protected boolean modifyPixel(int x,int y,Color color){

        if (isInImage(x,y)) {
            writeImage.getPixelWriter().setColor(x, y, color);
            return true;
        }else{
            return false;
        }

    }
    protected boolean isInImage(int x,int y){
        if (x<0 || y<0){
            return false;
        }else {
            if (writeImage.getWidth() > x && writeImage.getHeight() > y) {
                return true;
            } else {
                return false;
            }
        }
    }

    protected Color getPixelColor(int x, int y) {
        if (isInImage(x,y)) {
            return writeImage.getPixelReader().getColor(x, y);

        }else{
            return Color.color(0,0,0,0);
        }

    }

}
