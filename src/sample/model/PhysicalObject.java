package sample.model;


/**
 * Created by yann on 12/05/17.
 */
public class PhysicalObject {
    protected static final Vector gravite = new Vector(0,100);

    protected Vector position;
    protected Vector speed;
    protected Vector forces;


    public PhysicalObject(Vector position, Vector speed, Vector forces) {
        this.position = position;
        this.speed = speed;
        this.forces = forces;
    }

    public PhysicalObject(Vector position, Vector speed) {
        this.position = position;
        this.speed = speed;
        this.forces = new Vector(0,0);
        this.forces = this.forces.add(gravite);
    }

    public PhysicalObject(Vector position) {
        this.position = position;
        this.speed = new Vector(0,0);
        this.forces = new Vector(0,0);
        this.forces = this.forces.add(gravite);
    }

    public PhysicalObject() {
        this.position = new Vector(0,0);
        this.speed = new Vector(0, 0);
        this.forces = new Vector(0, 0);
        this.forces = this.forces.add(gravite);
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getSpeed() {
        return speed;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }

    public Vector getForces() {
        return forces;
    }

    public void setForces(Vector forces) {
        this.forces = forces;
    }

    public void addForces(Vector f){
        this.forces.add(f);
    }

    public void addSpeed(Vector s){
        this.speed.add(s);
    }

    public void update(double deltaTime){
        this.speed = speed.add(this.forces.mulScal(deltaTime));
        this.position = position.add(this.speed.mulScal(deltaTime));
    }

    public Vector futurePosition( double deltaTime){
        return position.add(this.speed.add(this.forces.mulScal(deltaTime)).mulScal(deltaTime));
    }
}
