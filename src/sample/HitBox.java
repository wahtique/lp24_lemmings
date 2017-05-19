package sample;

import javafx.scene.paint.Color;

/**
 * Created by naej on 12/05/17.
 */
public class HitBox extends Sprite {

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

    public boolean willBeColliding(Vector pos,HitBox other){

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
}
