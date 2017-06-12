package sample.model;

import javafx.scene.canvas.GraphicsContext;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * Created by yann on 09/06/17.
 */
public class Vomit extends PhysicalObject implements Collidable {
    Boolean fall;
    HitBox hitBox;
    TreeMap<Boolean,AnimatedSprite> animation;

    public Vomit() {
        this.animation = new TreeMap<Boolean,AnimatedSprite>();
        this.fall = true;
        this.hitBox = new HitBox("/resources/Lemming/vomi/hitboxes/falling.png")
        this.animation.put(true,new AnimatedSprite("/resources/Lemming/vomi/falling/"));
        this.animation.put(false,new AnimatedSprite("/resources/Lemming/vomi/falling/dissolving"));
    }

    public void update(double deltaTime, Level level){
        if(this.willBeColliding(futurePosition(deltaTime),level.getTerrain())){
            this.speed = speed.add(this.forces.mulScal(deltaTime));
            this.position = position.add(this.speed.mulScal(deltaTime));
        }

    }

    @Override
    public void draw(GraphicsContext gc) {
        this.animation.get(fall).draw(gc);
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
