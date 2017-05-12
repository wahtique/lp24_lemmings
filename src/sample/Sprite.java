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
    protected Vector position;

    public Sprite(){
        renderedImage = null;
        writeImage = null;
        position = new Vector(0,0);
    }

    public Sprite(String url){
        setImage(url);
        position = new Vector(0,0);
    }

    protected Vector fromCanvasToLocal(Vector v){
        return v.add(position.mulScal(-1));
    }


    public boolean modifyPixelCanvasRef(Vector pos,Color color){
        return modifyPixel(fromCanvasToLocal(pos),color);
    }

    public void drawImage(GraphicsContext gc){
        gc.drawImage( renderedImage.getImage(), position.getX(), position.getY());
    }

    public void setPosition(Vector p){
         position = p;
    }

    public Color getPixelColorCanvasRef(Vector pos){


        return getPixelColor(fromCanvasToLocal(pos));
    }

    public void flipX(){
        //TODO
        //renderedImage.setScaleX(-renderedImage.getScaleX());
    }
    public float getLayer(){
        return layer;
    }
    public void setLayer(float layer){
        this.layer = layer;
    }

    public void setImage(String url){
        Image image = new Image(url);
        writeImage = new WritableImage(image.getPixelReader(),(int)image.getWidth(),(int)image.getHeight());
        renderedImage = new ImageView(writeImage);
    }

    protected boolean modifyPixel(Vector pos,Color color){

        if (isInImage(pos)) {
            writeImage.getPixelWriter().setColor((int)pos.getX(),(int)pos.getY(), color);
            return true;
        }else{
            return false;
        }

    }
    protected boolean isInImage(Vector pos){
        if (pos.getX() <0 || pos.getY() <0){
            return false;
        }else {
            if (writeImage.getWidth() > pos.getX() && writeImage.getHeight() > pos.getY() ) {
                return true;
            } else {
                return false;
            }
        }
    }

    protected Color getPixelColor(Vector pos) {
        if (isInImage(pos)) {
            return writeImage.getPixelReader().getColor((int)pos.getX(),(int) pos.getY());

        }else{
            return Color.color(0,0,0,0);
        }

    }
    public Vector getPosition(){
        return position;
    }

}
