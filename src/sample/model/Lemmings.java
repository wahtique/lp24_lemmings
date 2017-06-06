package sample.model;

import javafx.scene.canvas.GraphicsContext;
import sample.view.DrawAble;

import java.util.HashSet;

/**
 * Created by yann on 12/05/17.
 */
public class Lemmings extends PhysicalObject implements DrawAble{
    private HitBox feet;
    private HitBox body;
    private LemmingsStates state;
    private StudentData lemData;

    public Lemmings(Vector position, Vector speed, Vector forces, String feet, String body) {
        super(position, speed, forces);
        this.feet = new HitBox(feet);
        this.body = new HitBox(body);
        this.lemData = new StudentData();
        this.state = LemmingsStates.Walk;
    }

    public void setPositionHitbox(Vector p){
        super.setPosition(p);
        this.feet.setPosition(p);
        this.body.setPosition(p);
    }

    public void update(double deltaTime, HashSet<HitBox> terrain){
        switch (state){
            case Walk:
                if(isGrounded(terrain,deltaTime)){
                    if (!this.body.willBeColliding(this.futurePosition(deltaTime),terrain)){
                        this.speed.setX(speed.add(this.forces.mulScal(deltaTime)).getX());
                        this.position.setX(position.add(this.speed.mulScal(deltaTime)).getX());
                        this.position.setY(this.position.getY()-this.feet.getCollisionDepthY(this.position,terrain));
                    }else{
                 //       System.out.println("Collide: " +this.toString());
                        this.speed.setX(-this.speed.getX());
                        this.speed = speed.add(this.forces.mulScal(deltaTime));
                        this.position = position.add(this.speed.mulScal(deltaTime));
                        this.position.setY(this.position.getY()-this.feet.getCollisionDepthY(this.position,terrain));
                    }
                }else{
                            this.state = LemmingsStates.Falling;
                        }break;
            case Falling: if(!isGrounded(terrain,deltaTime)){
                            this.speed.setY(speed.add(forces.mulScal(deltaTime)).getY());
                            this.position.setY(position.add(this.speed.mulScal(deltaTime)).getY());
                        }else {
                            if(this.speed.getY() < 10000000){
                                this.state = LemmingsStates.Walk;
                            }else {
                                this.state = LemmingsStates.AnimPls;
                            }
                        }break;
            case AnimPls: if(true){
                this.state = LemmingsStates.Pls;
                terrain.add(this.body);
            }else{

            }break;
            case Pls: break;
            case LeavePls: if(true) break;
            case Vomit: break;
            default: break;
        }
        //System.out.println(this.toString());
        this.setPositionHitbox(this.position);
    }

    public boolean isGrounded(HashSet<HitBox> terrain, double deltatime){
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
        this.body.draw(gc);
        this.feet.draw(gc);
    }

    public float getLayer(){
        return 0;
    }

    public String toString(){
        return "[ " +this.state +" pos:" +this.position.toString() + " ]";
    }
}
