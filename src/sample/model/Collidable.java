package sample.model;

import sample.view.DrawAble;

import java.util.HashSet;

/**Interface defining how collidable objects should look like
 * Created by yann on 09/06/17.
 */
//TODO document this
public interface Collidable extends DrawAble {
    boolean willBeColliding(Vector pos, HashSet<Collidable> others);
    boolean willBeColliding(Vector pos, Collidable other);
    boolean isInHitbox(Vector pos);
    double getCollisionDepthY(Vector pos, Collidable other);
    double getCollisionDepthY(Vector pos, HashSet<Collidable> others);

}
