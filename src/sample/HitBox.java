package sample;

import javafx.scene.paint.Color;

/**
 * Created by naej on 12/05/17.
 */
public class HitBox extends Sprite {

    public HitBox(String url){
        super(url);
    }

    public boolean isInHitbox(int x,int y){
        return !(this.getPixelColorCanvasRef(x,y).getOpacity() == 0);
    }

    public boolean deletePoint(int x,int y){
        return this.modifyPixelCanvasRef(x,y, Color.color(0,0,0,0)) ;
    }

    public boolean willBeColliding(int posX,int posY,HitBox other){

        for (int x =0;x<this.renderedImage.getImage().getWidth();x++){
            for (int y =0;y<this.renderedImage.getImage().getHeight();y++){
                if (this.getPixelColor(x,y).getOpacity() != 0){
                    if (other.isInHitbox(x+posX,y+posY)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
