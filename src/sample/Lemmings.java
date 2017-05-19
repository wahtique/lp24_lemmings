package sample;

/**
 * Created by yann on 12/05/17.
 */
public class Lemmings extends PhysicalObject {
    HitBox feet;
    HitBox body;
    LemmingsStates state;
    StudentData lemData;

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

    public void update(double deltaTime){
        switch (state){
            case Walk: if(!isGrounded()){ //add wall collision
                            this.position = position.add(this.speed.mulScal(deltaTime));
                        }else{
                            this.state = LemmingsStates.Falling;
                        }break;
            case Falling: if(!isGrounded()){
                            //this.speed.setX(this.speed.getX()*deltaTime);
                            this.speed = speed.add(this.forces.mulScal(deltaTime));
                            this.position = position.add(this.speed.mulScal(deltaTime));
                        }else {
                            this.state = LemmingsStates.Walk;
                        }break;
            default: break;
        }
    }

    public boolean isGrounded(){
        return true;
    }

}
