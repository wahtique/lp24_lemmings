package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by naej on 12/05/17.
 */
public class AnimatedSprite extends Sprite{
    //TODO doing it

    private boolean isLooping;
    private ArrayList<WritableImage> listOfImages;
    private int currentImage = 0;
    private float framerate = 6;
    private double currentTime =0;

    public AnimatedSprite(String url){
        Image image = new Image(url);
        listOfImages.add(new WritableImage(image.getPixelReader(),(int)image.getWidth(),(int)image.getHeight()));
        renderedImage = new ImageView(listOfImages.get(currentImage));
    }
    public boolean modifyPixel(int x,int y,Color color){
        if (x<0 || y<0){
            return false;
        }else {
            if (listOfImages.get(currentImage).getWidth() > x && listOfImages.get(currentImage).getHeight() > y) {
                listOfImages.get(currentImage).getPixelWriter().setColor(x, y, color);
                return true;
            } else {
                return false;
            }
        }

    }
    public boolean modifyPixelCanvasRef(int x,int y,Color color){
        x = (int) (x- renderedImage.getX());
        y = (int) (y- renderedImage.getY());

        return modifyPixel(x,y,color);
    }


    public void drawImage(GraphicsContext gc){
        gc.drawImage(renderedImage.getImage(), renderedImage.getX(), renderedImage.getY());
    }

    public void setPosition(double x, double y){
        renderedImage.setX(x);
        renderedImage.setY(y);
    }

    public void update(double deltaTime){
        currentTime += deltaTime;
        if (currentTime > getAnimationLength()){
            currentTime -= getAnimationLength();
        }

    }

    public double getAnimationLength(){
        return listOfImages.size()*1.0/framerate;
    }

}
