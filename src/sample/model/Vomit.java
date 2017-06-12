package sample.model;

import javafx.scene.canvas.GraphicsContext;
import sample.view.Drawer;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * Created by yann on 09/06/17.
 */
public class Vomit extends PhysicalObject implements Collidable {
    Boolean isFalling;
    HitBox hitBox;
    TreeMap<Boolean,AnimatedSprite> animation;

    public Vomit() {
        this.animation = new TreeMap<Boolean,AnimatedSprite>();
        this.isFalling = true;
        this.hitBox = new HitBox("/resources/Lemming/vomi/hitboxes/falling.png");
        this.animation.put(true,new AnimatedSprite("/resources/Lemming/vomi/falling/"));
        this.animation.put(false,new AnimatedSprite("/resources/Lemming/vomi/falling/dissolving"));
        Drawer.getDrawer().addSomethingToDraw(this);
    }

    public void update(double deltaTime, Level level){
        if(isFalling && !this.willBeColliding(futurePosition(deltaTime),level.getTerrain())){
            this.speed = speed.add(this.forces.mulScal(deltaTime));
            this.position = position.add(this.speed.mulScal(deltaTime));
        }else if(isFalling){
            this.isFalling = false;
        }else {
            if (!corrupt(level)){
                removethis(level);
            }
        }

        this.animation.get(isFalling).update(deltaTime);

    }
    private void removethis(Level level){
        Drawer.getDrawer().deleteSomethigToDraw(this);
        level.getVomits().remove(this);
    }

    private boolean corrupt(Level level) {
        for (Collidable other:level.getTerrain()) {
            for (int x = 0; x < this.hitBox.getImage().getWidth(); x++) {
                for (int y = 0; y < this.hitBox.getImage().getHeight(); y++) {
                    if (other.isInHitbox(new Vector(x + position.getX(), y + position.getY()))) {
                        if (!corruptPoint(other, new Vector(x + position.getX(), y + position.getY()))) {
                            return false;
                        }
                    }

                }

            }
        }
        return true;
    }

    private boolean corruptPoint(Collidable other,Vector pos) {

        if (other.getClass() == Lemmings.class) {
            ((Lemmings) other).setState(LemmingsStates.LeavePls);
            return false;
        } else {
            ((HitBox) other).deletePoint(pos);
            return true;
        }

    }
        /*
        for (int x =0;x<this.renderedImage.getImage().getWidth();x++){
            for (int y =0;y<this.renderedImage.getImage().getHeight();y++){
                if (this.getPixelColor(new Vector(x,y)).getOpacity() != 0){
                    if (other.isInHitbox(new Vector( x+pos.getX(),y+pos.getY() ))){
                        return true;
                    }
                }
            }
        }*/


    @Override
    public void draw(GraphicsContext gc) {
        this.animation.get(isFalling).draw(gc);
    }

    @Override
    public float getLayer() {
        return this.hitBox.getLayer();
    }

    @Override
    public boolean willBeColliding(Vector pos, HashSet<Collidable> others) {
        return this.hitBox.willBeColliding(pos,others);
    }

    @Override
    public boolean willBeColliding(Vector pos, Collidable other) {
        return this.hitBox.willBeColliding(pos,other);
    }

    @Override
    public boolean isInHitbox(Vector pos) {
        return this.hitBox.isInHitbox(pos);
    }

    @Override
    public double getCollisionDepthY(Vector pos, Collidable other) {
        return this.hitBox.getCollisionDepthY(pos,other);
    }

    @Override
    public double getCollisionDepthY(Vector pos, HashSet<Collidable> others) {
        return this.hitBox.getCollisionDepthY(pos,others);
    }


}