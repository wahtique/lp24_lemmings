package sample.model;

import sample.view.DrawAble;

import java.util.HashSet;

/**
 * Interface used to normalise collidable objects
 * @author Yann
 */
public interface Collidable extends DrawAble {
    boolean willBeColliding(Vector pos, HashSet<Collidable> others);
    boolean isInHitbox(Vector pos);
    double getCollisionDepthY(Vector pos, HashSet<Collidable> others);

}
