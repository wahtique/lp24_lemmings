package sample.model;

import javafx.scene.canvas.GraphicsContext;
import sample.view.DrawAble;

import java.util.HashSet;
import java.util.TreeMap;

/**
 * Created by yann on 12/05/17.
 */
public class Lemmings extends PhysicalObject implements DrawAble, Collidable{
    private HitBox feet;
    private HitBox body;
    private LemmingsStates state;
    private StudentData lemData;
    private TreeMap<LemmingsStates,AnimatedSprite> animation;

    public Lemmings(Vector position, Vector speed, Vector forces, String feet, String body) {
        super(position, speed, forces);
        this.feet = new HitBox(feet);
        this.body = new HitBox(body);
        this.lemData = new StudentData();
        this.state = LemmingsStates.Walk;
        animation = new TreeMap<LemmingsStates,AnimatedSprite>();
        animation.put(LemmingsStates.Walk, new AnimatedSprite("/resources/Lemming/walk/",true));
        animation.put(LemmingsStates.Falling, new AnimatedSprite("/resources/Anim/taiste/", true));
    }

    public void setPositionHitbox(Vector p){
        super.setPosition(p);
        this.feet.setPosition(p);
        this.body.setPosition(p);
    }

    public void update(double deltaTime, Level level){
        switch (state){
            case Walk:
                if(isGrounded(level.getTerrain(),deltaTime)){
                    if (!this.body.willBeColliding(this.futurePosition(deltaTime),level.getTerrain())){
                        this.speed.setX(speed.add(this.forces.mulScal(deltaTime)).getX());
                        this.position.setX(position.add(this.speed.mulScal(deltaTime)).getX());
                        this.position.setY(this.position.getY()-this.feet.getCollisionDepthY(this.position,level.getTerrain()));
                    }else{
                 //       System.out.println("Collide: " +this.toString());             Il se retourne docilement:
                        this.speed.setX(-this.speed.getX());
                        this.speed = speed.add(this.forces.mulScal(deltaTime));
                        this.position = position.add(this.speed.mulScal(deltaTime));
                        this.position.setY(this.position.getY()-this.feet.getCollisionDepthY(this.position,level.getTerrain()));
                        this.animation.get(state).flipX();
                    }
                }else{
                            this.state = LemmingsStates.Falling;
                        }break;
            case Falling: if(!isGrounded(level.getTerrain(),deltaTime)){
                            this.speed.setY(speed.add(forces.mulScal(deltaTime)).getY());
                            this.position.setY(position.add(this.speed.mulScal(deltaTime)).getY());
                        }else {
                            if(this.speed.getY() < 10000000){
                                this.state = LemmingsStates.Walk;
                                this.speed.setY(0);
                            }else {
                                this.state = LemmingsStates.Pls;
                                level.getLemmingsList().remove(this);
                                level.getTerrain().add(this);
                                this.animation.get(state).reset();
                            }
                        }break;
            case LeavePls: if( this.animation.get(state).isEnded()){
                this.state = LemmingsStates.Walk;
            };
            case Vomit: break;
            default: break;
        }
        //System.out.println(this.toString());
        this.animation.get(state).update(deltaTime);
        this.animation.get(state).setPosition(this.position);
        this.setPositionHitbox(this.position);
    }

    public boolean isGrounded(HashSet<Collidable> terrain, double deltatime){
        return this.feet.willBeColliding(this.futurePosition(deltatime),terrain);
    }
    public void forward(){}

    public LemmingsStates getState() {
        return state;
    }

    public void setState(LemmingsStates state) {
        this.state = state;
    }

    public StudentData getLemData() {
        return lemData;
    }

    public void setLemData(StudentData lemData) {
        this.lemData = lemData;
    }

    public void draw(GraphicsContext gc){
        this.animation.get(state).draw(gc);
    }

    public float getLayer(){
        return 0;
    }

    public boolean willBeColliding(Vector pos, HashSet<Collidable> others){
        return this.body.willBeColliding(pos, others);
    }
    public boolean willBeColliding(Vector pos, Collidable other){
        return this.body.willBeColliding(pos, other);

    }
    public boolean isInHitbox(Vector pos){
        return this.body.isInHitbox(pos);
    }

    public double getCollisionDepthY(Vector pos, Collidable other){
        return this.body.getCollisionDepthY(pos,other);
    }

    public double getCollisionDepthY(Vector pos, HashSet<Collidable> others){
        return this.body.getCollisionDepthY(pos,others);
    }

    public String toString(){
        return "[ " +this.state +" pos:" +this.position.toString() + " ]";
    }
}

