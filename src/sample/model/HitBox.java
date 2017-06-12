package sample.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashSet;

/**
 * Created by naej on 12/05/17.
 */
public class HitBox extends Sprite implements Collidable {

    public HitBox(String url){
        super(url);
    }

    public boolean isInHitbox(Vector pos){
       // System.out.print(".");
        return (this.getPixelColorCanvasRef(pos).getOpacity() != 0);

    }

    public boolean deletePoint(Vector pos){
        return this.modifyPixelCanvasRef(pos, Color.color(0,0,0,0)) ;
    }

    public boolean willBeColliding(Vector pos, Collidable other){

        for (int x =0;x<this.renderedImage.getImage().getWidth();x++){
            for (int y =0;y<this.renderedImage.getImage().getHeight();y++){
                if (this.getPixelColor(new Vector(x,y)).getOpacity() != 0){
                    if (other.isInHitbox(new Vector( x+pos.getX(),y+pos.getY() ))){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean willBeColliding(Vector pos, HashSet<Collidable> others){

        for (int x =0;x<this.renderedImage.getImage().getWidth();x++){
            for (int y =0;y<this.renderedImage.getImage().getHeight();y++){
                if (this.getPixelColor(new Vector(x,y)).getOpacity() != 0){
                    Vector temp = new Vector( x+pos.getX(),y+pos.getY() );
                    if (others.stream().anyMatch(o->o.isInHitbox(temp))){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public boolean flipX(){
        System.out.println("WARNING : the hitbox is not flipped !");
        return false;
    }

    public Vector getHigherCollidingPoint(Vector pos, HitBox other){
        Vector temp =null;
        for (int x =0;x<this.renderedImage.getImage().getWidth();x++){
            for (int y =0;y<this.renderedImage.getImage().getHeight();y++){
                if (this.getPixelColor(new Vector(x,y)).getOpacity() != 0){
                    if (other.isInHitbox(new Vector( x+pos.getX(),y+pos.getY() ))){
                        if (temp ==null || y+pos.getY() < temp.getY()){
                            temp = new Vector( x+pos.getX(),y+pos.getY() );
                        }
                    }
                }
            }
        }
        return temp;
    }
    public double getCollisionDepthY(Vector pos, Collidable other){
        Vector higher =null;
        for (int y =0;y<this.renderedImage.getImage().getHeight();y++){
            for (int x =0;x<this.renderedImage.getImage().getWidth();x++){
                if (this.getPixelColor(new Vector(x,y)).getOpacity() != 0){
                    if (other.isInHitbox(new Vector( x+pos.getX(),y+pos.getY() ))){

                        higher = new Vector( x,y );
                        break;


                    }
                }

            }
            if (higher != null){
                break;
            }

        }
        if (higher != null) {
           // System.out.println(""+ (renderedImage.getImage().getHeight() - higher.getY()));
            return (renderedImage.getImage().getHeight() - higher.getY());
        }else {
            return 0;
        }
    }
    public double getCollisionDepthY(Vector pos, HashSet<Collidable> others){
        Vector higher =null;

        for (int y =0;y<this.renderedImage.getImage().getHeight();y++){
            for (int x =0;x<this.renderedImage.getImage().getWidth();x++){
                if (this.getPixelColor(new Vector(x,y)).getOpacity() != 0){
                    Vector temp = new Vector( x+pos.getX(),y+pos.getY() );
                    if (others.stream().anyMatch(o->o.isInHitbox(temp))){
                        higher = new Vector(x,y);
                        break;
                    }

                }
            }
            if (higher != null){
                break;
            }
        }
        if (higher != null) {
            //System.out.println("" +(this.renderedImage.getImage().getHeight() - higher.getY()));
            return renderedImage.getImage().getHeight() - higher.getY();
        }else{
            return 0;
        }


    }
    public Image getImage(){
        return renderedImage.getImage();
    }

}
