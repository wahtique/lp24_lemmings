package sample.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import sample.view.DrawAble;

/**
 * Base class used to handle images in a simple way
 * @author Jean
 */
//TODO document this
public class Sprite implements DrawAble {
    protected boolean isFlipped;
    protected float layer;
    protected ImageView renderedImage;
    protected WritableImage writeImage;
    protected Vector position;

    public Sprite(){
        renderedImage = null;
        writeImage = null;
        position = new Vector(0,0);
        isFlipped = false;
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

    public void draw(GraphicsContext gc) {
        if (!isFlipped) {
            gc.drawImage(renderedImage.getImage(), position.getX(), position.getY());
        }else{
            gc.drawImage(renderedImage.getImage(),
                    0, 0,
                    renderedImage.getImage().getWidth(), renderedImage.getImage().getHeight(),
                    position.getX()+renderedImage.getImage().getWidth(), position.getY(),
                    -renderedImage.getImage().getWidth(),renderedImage.getImage().getHeight());
        }
    }

    public void setPosition(Vector p){
         position = p;
    }

    public Color getPixelColorCanvasRef(Vector pos){


        return getPixelColor(fromCanvasToLocal(pos));
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

    public boolean flipX(){
        isFlipped = !isFlipped;
        return isFlipped;
    }

    public void replaceColor(Color startColor,Color replacementColor,int tolerance){

        for (int x =0;x<this.renderedImage.getImage().getWidth();x++){
            for (int y =0;y<this.renderedImage.getImage().getHeight();y++){
                if (areColorsEqualsTolerance(this.getPixelColor(new Vector(x,y)),startColor, tolerance)){
                    this.modifyPixel(new Vector(x,y),replacementColor);
                }
            }
        }
    }
    public boolean areColorsEqualsTolerance(Color c1,Color c2,int tolerance){
        if (c1.equals(c2)){
            return true;
        }else if(   areIntEqualsTolerance((int) (c1.getBlue()*255),   (int) (c2.getBlue()*255),tolerance)  &&
                    areIntEqualsTolerance((int) (c1.getGreen()*255),  (int) (c2.getGreen()*255),tolerance)  &&
                    areIntEqualsTolerance((int) (c1.getRed()*255),    (int) (c2.getRed()*255),tolerance)    ){
         //   System.out.println("b :" + c1.getBlue() + " + " + c2.getBlue() + " = " + areIntEqualsTolerance((int) c1.getBlue(),   (int) c2.getBlue(),tolerance));
           // System.out.println("g :" + areIntEqualsTolerance((int) c1.getGreen(),   (int) c2.getGreen(),tolerance));
            //System.out.println("r :" + areIntEqualsTolerance((int) c1.getRed(),   (int) c2.getRed(),tolerance));

            return true;
        }else{
            return false;
        }
    }/*
    public boolean areColorsEqualsPrecision(Color c1,Color c2,int precision){
        if (c1.equals(c2)){
            return true;
        }else if((int) (c1.getBlue()* precision) == (int) (c2.getBlue()* precision) &&
                (int) (c1.getRed()* precision) == (int) (c2.getRed()* precision) &&
                (int) (c1.getGreen()* precision) == (int) (c2.getGreen()* precision) ){
            c1.ge
            return true;
        }else{
            return false;
        }
    }*/
    public boolean areIntEqualsTolerance(int i1,int i2,int tolerance){
        if (i1 == i2){
            return true;
        }else if(i1 >= i2 - tolerance && i1 <= i2 + tolerance){
            return true;
        }else{
            return false;
        }
    }
}

