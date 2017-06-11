package sample.model;

import javafx.scene.canvas.GraphicsContext;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * Created by yann on 09/06/17.
 */
public class Vomit extends PhysicalObject implements Collidable {
    Boolean Fall;
    HitBox hitBox;
    TreeMap<Boolean,AnimatedSprite> animation;

    public Vomit() {
        this.animation = new TreeMap<Boolean,AnimatedSprite>();
        this.animation.put(true,new AnimatedSprite(""));
        this.animation.put(false,new AnimatedSprite(""));
    }

    public void update(double deltaTime){
        this.speed = speed.add(this.forces.mulScal(deltaTime));
        this.position = position.add(this.speed.mulScal(deltaTime));
    }

    @Override
    public void draw(GraphicsContext gc) {
        this.animation.get(Fall).draw(gc);
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
