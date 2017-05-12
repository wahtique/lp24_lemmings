package sample;

/**
 * Created by yann on 12/05/17.
 */
public class Lemmings extends PhysicalObject {
    HitBox feet;
    HitBox body;
    float marks;

    public Lemmings(Vector position, Vector speed, Vector forces, String feet, String body) {
        super(position, speed, forces);
        this.feet = new HitBox(feet);
        this.body = new HitBox(body);
        this.marks = 10;
    }

    public void setPositionHitbox(Vector p){
        super.setPosition(p);

    }
}
