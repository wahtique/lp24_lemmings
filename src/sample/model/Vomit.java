package sample.model;

import javafx.scene.canvas.GraphicsContext;
import sample.view.Drawer;

import java.util.HashSet;
import java.util.TreeMap;

/**
 * Created by yann on 09/06/17.
 */
public class Vomit extends PhysicalObject implements Collidable {
    private Boolean isFalling;
    private HitBox hitBoxFalling;
    private HitBox hitBoxDissolv;
    private TreeMap<Boolean,AnimatedSprite> animation;

    private double inTheAirCount = 0;

    public Vomit(Vector pos){
        super(pos);
        this.animation = new TreeMap<Boolean,AnimatedSprite>();
        this.isFalling = true;
        this.hitBoxFalling = new HitBox("/resources/Lemming/vomi/hitboxes/falling.png");
        this.hitBoxDissolv = new HitBox("resources/Lemming/vomi/hitboxes/dissolve.png");
        this.animation.put(true,new AnimatedSprite("/resources/Lemming/vomi/falling"));
        this.animation.put(false,new AnimatedSprite("/resources/Lemming/vomi/dissolving"));
        Drawer.getDrawer().addSomethingToDraw(this);
        setPosition(pos);
    }

    public void update(double deltaTime, Level level){
//        this.speed = speed.add(this.forces.mulScal(deltaTime));
//        setPosition( position.add(this.speed.mulScal(deltaTime)));


        if(isFalling && !this.willBeColliding(futurePosition(deltaTime),level.getTerrain())){
            this.speed = speed.add(this.forces.mulScal(deltaTime));
            this.position = position.add(this.speed.mulScal(deltaTime));
        }else if(isFalling){
            this.isFalling = false;
        }else {
            this.speed = new Vector(0,100);
            this.position = position.add(this.speed.mulScal(deltaTime));
            setPosition(position);

            if (!corrupt(level)){
                inTheAirCount += deltaTime;
                if(inTheAirCount >= 0.1)
                    removethis(level);

            }else{
                inTheAirCount =0;
            }
        }

        setPosition(position);

        this.animation.get(isFalling).update(deltaTime);


    }

    @Override
    public void setPosition(Vector pos){
        this.position = pos;
        this.animation.get(isFalling).setPosition(position);
        this.hitBoxFalling.setPosition(pos);
        this.hitBoxDissolv.setPosition(pos);

    }

    private void removethis(Level level){
      //  System.out.println("Am i removed ?");
       // System.out.println(this);
        Drawer.getDrawer().deleteSomethingToDraw(this);
        level.removeVomit(this);
    }

    private boolean corrupt(Level level) {
        boolean iAminTheAir = true;
        for (Collidable other:level.getTerrain()) {
            for (int x = 0; x < this.hitBoxDissolv.getImage().getWidth(); x++) {
                for (int y = 0; y < this.hitBoxDissolv.getImage().getHeight(); y++) {
                    if(this.hitBoxDissolv.isInHitbox(new Vector(x + position.getX(), y + position.getY()))){
                        if (other.isInHitbox(new Vector(x + position.getX(), y + position.getY()))) {
                            if (!corruptPoint(other, new Vector(x + position.getX(), y + position.getY()))) {
                                return false;
                            }
                            iAminTheAir = false;
                        }

                    }
                }

            }
        }
        return !iAminTheAir;
    }

    private boolean corruptPoint(Collidable other,Vector pos) {

        if (other.getClass() == Lemmings.class) {
            ((Lemmings) other).setState(LemmingsStates.LeavePls);
            System.out.println("LemmingAwaken");
            return false;
        } else {
            ((HitBox) other).deletePoint(pos);
            //  System.out.println("Dissolving");
            return true;

        }

    }

    public void flipX(){

        this.animation.get(isFalling).flipX();
        this.animation.get(!isFalling).flipX();
        this.hitBoxFalling.flipX();
        this.hitBoxDissolv.flipX();
    }

    @Override
    public void draw(GraphicsContext gc) {
        this.animation.get(isFalling).draw(gc);
       // this.hitBoxFalling.draw(gc);
    }

    @Override
    public float getLayer() {
        return this.hitBoxFalling.getLayer();
    }

    @Override
    public boolean willBeColliding(Vector pos, HashSet<Collidable> others) {
        return this.hitBoxFalling.willBeColliding(pos,others);
    }

    @Override
    public boolean willBeColliding(Vector pos, Collidable other) {
        return this.hitBoxFalling.willBeColliding(pos,other);
    }

    @Override
    public boolean isInHitbox(Vector pos) {
        return this.hitBoxFalling.isInHitbox(pos);
    }

    @Override
    public double getCollisionDepthY(Vector pos, Collidable other) {
        return this.hitBoxFalling.getCollisionDepthY(pos,other);
    }

    @Override
    public double getCollisionDepthY(Vector pos, HashSet<Collidable> others) {
        return this.hitBoxFalling.getCollisionDepthY(pos,others);
    }


}