package sample;

/**
 * Created by naej on 12/05/17.
 */
public class HitBox extends Sprite {
    public boolean isInHitbox(int x,int y){
        return !(this.getPixelColorCanvasRef(x,y).getOpacity() == 0);
    }
}
