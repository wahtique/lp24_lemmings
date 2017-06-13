package sample.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.view.DrawAble;
import sample.view.Drawer;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yann on 12/05/17.
 */
public class Lemmings extends PhysicalObject implements DrawAble, Collidable {
    double time = 0.1;
    double ttime=0;
    double nbbrick=0;
    private HitBox feet;
    private HitBox body;
    private LemmingsStates state;
    private StudentData lemData;
    private TreeMap<LemmingsStates, AnimatedSprite> animation;
    private boolean isFlipped=false;
    private Level level;

    @Deprecated
    public Lemmings(Vector position, Vector speed, Vector forces, String feet, String body) {
        super(position, speed, forces);
        this.feet = new HitBox(feet);
        this.body = new HitBox(body);
        this.body.replaceColor(Color.rgb(0, 255, 0), Color.rgb(255, 255, 255), 150);

        // System.out.println(this.body.areColorsEqualsPrecision(Color.rgb(0,255,0),Color.rgb(0,250,0),1));
        this.lemData = new StudentData();
        this.state = LemmingsStates.Walk;
        animation = new TreeMap<LemmingsStates, AnimatedSprite>();
        animation.put(LemmingsStates.Walk, new AnimatedSprite("/resources/Lemming/Anim/walk/"));
        System.out.println("created walk");
        animation.put(LemmingsStates.Falling, new AnimatedSprite("/resources/Lemming/Anim/falling/"));
        animation.put(LemmingsStates.Pls, new AnimatedSprite("/resources/Lemming/Anim/PLS/"));
        animation.put(LemmingsStates.LeavePls, new AnimatedSprite("/resources/Lemming/Anim/relevePLS/"));
        animation.put(LemmingsStates.Construct, new AnimatedSprite("/resources/Lemming/Anim/construct/"));
    }
    @Deprecated
    public Lemmings(Vector position, Vector speed, String feet, String body) {
        super(position, speed);
        this.feet = new HitBox(feet);
        this.body = new HitBox(body);
        this.body.replaceColor(Color.rgb(0, 255, 0), Color.rgb(255, 255, 255), 150);

        // System.out.println(this.body.areColorsEqualsPrecision(Color.rgb(0,255,0),Color.rgb(0,250,0),1));
        this.lemData = new StudentData();
        this.state = LemmingsStates.Walk;
        animation = new TreeMap<LemmingsStates, AnimatedSprite>();
        animation.put(LemmingsStates.Walk, new AnimatedSprite("/resources/Lemming/Anim/walk"));
        animation.put(LemmingsStates.Falling, new AnimatedSprite("/resources/Lemming/Anim/falling"));
        animation.put(LemmingsStates.Pls, new AnimatedSprite("/resources/Lemming/Anim/PLS"));
        animation.put(LemmingsStates.LeavePls, new AnimatedSprite("/resources/Lemming/Anim/relevePLS"));
        animation.put(LemmingsStates.Construct, new AnimatedSprite("/resources/Lemming/Anim/construct"));
    }
    public Lemmings(Vector position, Vector speed, String feet, String body,Level level) {
        super(position, speed);
        this.feet = new HitBox(feet);
        this.body = new HitBox(body);
        this.body.replaceColor(Color.rgb(0, 255, 0), Color.rgb(255, 255, 255), 150);

        // System.out.println(this.body.areColorsEqualsPrecision(Color.rgb(0,255,0),Color.rgb(0,250,0),1));
        this.lemData = new StudentData();
        this.state = LemmingsStates.Walk;
        animation = new TreeMap<LemmingsStates, AnimatedSprite>();
        animation.put(LemmingsStates.Walk, new AnimatedSprite("/resources/Lemming/Anim/walk"));
        animation.put(LemmingsStates.Falling, new AnimatedSprite("/resources/Lemming/Anim/falling"));
        animation.put(LemmingsStates.Pls, new AnimatedSprite("/resources/Lemming/Anim/PLS"));
        animation.put(LemmingsStates.LeavePls, new AnimatedSprite("/resources/Lemming/Anim/relevePLS"));
        animation.put(LemmingsStates.Construct, new AnimatedSprite("/resources/Lemming/Anim/construct"));
        this.level = level;
    }
    public void setPositionHitbox(Vector p) {
        super.setPosition(p);
        this.feet.setPosition(p);
        this.body.setPosition(p);
    }

    public void update(double deltaTime, Level level) {

        switch (state) {
            case Walk:
               forward(deltaTime,level);
                break;
            case Falling:
                if (!isGrounded(level.getTerrain(), deltaTime)) {
                    this.speed.setY(speed.add(forces.mulScal(deltaTime)).getY());
                    this.position.setY(position.add(this.speed.mulScal(deltaTime)).getY());
                } else {

                    if (this.speed.getY() < 300) {
                        this.state = LemmingsStates.Walk;
                    } else {
                        this.state = LemmingsStates.Pls;
                        this.body = new HitBox("resources/Lemming/hitboxes/pls/body.png");
                        this.feet = new HitBox("resources/Lemming/hitboxes/pls/vomidetect.png");
                        level.getTerrain().add(this);
                        this.animation.get(state).reset();
                    }
                    this.speed.setY(0);
                }
                break;
            case LeavePls:
                if (this.animation.get(state).isEnded()) {
                    this.state = LemmingsStates.Walk;
                    this.body = new HitBox("resources/Lemming/hitboxes/walk/body.png");
                    this.feet = new HitBox("resources/Lemming/hitboxes/walk/feets.png");
                    level.getTerrain().remove(this);
                }break;
            case Vomit:
                if (this.animation.get(state).isEnded()){
                    Vomit vomi = new Vomit(position);
                    if (isFlipped)
                        vomi.flipX();

                    this.animation.get(state).reset();
                }
                break;
            case Construct:
                forward(deltaTime,level);
                if(nbbrick<=5) {
                    forward(deltaTime,level);
                    if (ttime <= 0) {
                        HitBox brick = new HitBox("/resources/Lemming/brique.png");
                        brick.setPosition(new Vector(position.getX(),position.getY()));
                        if(isFlipped)
                            brick.flipX();
                        level.getTerrain().add(brick);
                        Drawer.getDrawer().addSomethingToDraw(brick);
                        ttime = time;
                        nbbrick++;
                    } else {
                        ttime = ttime - deltaTime;

                    }
                }else {
                    nbbrick = 0;
                    this.state=LemmingsStates.Walk;
                }
            default:
                break;
        }
        //System.out.println(this.toString());
        this.animation.get(state).update(deltaTime);
        this.animation.get(state).setPosition(this.position);
        this.setPositionHitbox(this.position);
    }

    public boolean isGrounded(HashSet<Collidable> terrain, double deltatime) {
        return this.feet.willBeColliding(this.futurePosition(deltatime), terrain);
    }


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

    public void draw(GraphicsContext gc) {
        this.animation.get(state).draw(gc);
    }

    public float getLayer() {
        return 0;
    }

    public boolean willBeColliding(Vector pos, HashSet<Collidable> others) {
        return this.body.willBeColliding(pos, others);
    }

    public boolean willBeColliding(Vector pos, Collidable other) {
        return this.body.willBeColliding(pos, other);

    }

    public boolean isInHitbox(Vector pos) {
        return this.body.isInHitbox(pos);
    }

    public double getCollisionDepthY(Vector pos, Collidable other) {
        return this.body.getCollisionDepthY(pos, other);
    }

    public double getCollisionDepthY(Vector pos, HashSet<Collidable> others) {
        return this.body.getCollisionDepthY(pos, others);
    }

    public String toString() {
        return "[ " + this.state + " pos:" + this.position.toString() + " ]";
    }

    private void forward(double deltaTime, Level level) {
        if (isGrounded(level.getTerrain(), deltaTime)) {
            if (!this.body.willBeColliding(this.futurePosition(deltaTime), level.getTerrain())) {
                this.speed.setX(speed.add(this.forces.mulScal(deltaTime)).getX());
                this.position.setX(position.add(this.speed.mulScal(deltaTime)).getX());
                this.position.setY(this.position.getY() - this.feet.getCollisionDepthY(this.position, level.getTerrain()));
            } else {
                //       System.out.println("Collide: " +this.toString());             Il se retourne docilement:
                this.speed.setX(-this.speed.getX());
                this.speed = speed.add(this.forces.mulScal(deltaTime));
                this.position = position.add(this.speed.mulScal(deltaTime));
                this.position.setY(this.position.getY() - this.feet.getCollisionDepthY(this.position, level.getTerrain()));
                this.flipX();
            }
        } else {
            this.state = LemmingsStates.Falling;
        }
    }


    public void flipX(){
        for(Map.Entry<LemmingsStates, AnimatedSprite> anim : animation.entrySet()){
            anim.getValue().flipX();
        }
        isFlipped = !isFlipped;
    }
}