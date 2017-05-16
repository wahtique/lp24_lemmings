package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by naej on 12/05/17.
 */
public class AnimatedSprite extends Sprite{

    private boolean isLooping;
    private ArrayList<WritableImage> listOfImages;
    private int currentImage = 0;
    private int framerate = 6;
    private double currentTime =0;

    public AnimatedSprite(String url){
        super();
        listOfImages = new ArrayList<WritableImage>();
        File folder = new File(url);
        if (folder.listFiles() != null) {
            for (File fich : folder.listFiles()) {
                Image image = new Image(fich.getPath());
                listOfImages.add(new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight()));
            }
        }else{
            System.out.println("tamere");
        }
        renderedImage = new ImageView(listOfImages.get(currentImage));
    }

    public AnimatedSprite(ArrayList<String> urls){
        super();
        listOfImages = new ArrayList<WritableImage>();
        for (String url: urls){
            Image image = new Image(url);
            listOfImages.add(new WritableImage(image.getPixelReader(),(int)image.getWidth(),(int)image.getHeight()));
        }
        renderedImage = new ImageView(listOfImages.get(currentImage));
    }
   /* public boolean modifyPixel(int x,int y,Color color){
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

*//*
    public void drawImage(GraphicsContext gc) {
        if (!isFlipped) {
            gc.drawImage(renderedImage.getImage(), renderedImage.getX(), renderedImage.getY());
        }else{
            gc.drawImage(renderedImage.getImage(), renderedImage.getX(),renderedImage.getY(), renderedImage.getImage().getWidth(), renderedImage.getImage().getHeight(), renderedImage.getImage().getWidth(),0,-renderedImage.getImage().getWidth(),renderedImage.getImage().getHeight());

        }
    }
*/
    public void update(double deltaTime){
        currentTime += deltaTime;
        if (currentTime > getAnimationLength()){
            currentTime -= getAnimationLength();
        }
        currentImage = (int) ( (currentTime/getAnimationLength())*listOfImages.size());
        System.out.println(currentImage);
        renderedImage.setImage(listOfImages.get(currentImage));

    }

    public double getAnimationLength(){
        return listOfImages.size()*1.0/framerate;
    }

}
