package sample.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.view.DrawAble;
import sample.view.Drawer;

import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yann on 12/05/17.
 */
public class Lemmings extends PhysicalObject implements DrawAble, Collidable {
    private double time = 0.1;
    private double ttime=0;
    private double nbbrick=0;
    private Level level;
    private HitBox feet;
    private HitBox body;
    private LemmingsStates state;
    private StudentData lemData;
    private TreeMap<LemmingsStates, AnimatedSprite> animation;
    private boolean isFlipped=false;
    private boolean isSelected = false;

    @Deprecated
    public Lemmings(Vector position, Vector speed, Vector forces, String feet, String body, Level level) {
        super(position, speed, forces);
        this.feet = new HitBox(feet);
        this.body = new HitBox(body);
        this.level = level;
        this.body.replaceColor(Color.rgb(0, 255, 0), Color.rgb(255, 255, 255), 150);

        // System.out.println(this.body.areColorsEqualsPrecision(Color.rgb(0,255,0),Color.rgb(0,250,0),1));
        this.lemData = new StudentData();
        this.state = LemmingsStates.Walk;
        addAnimations();
    }
    public Lemmings(Vector position, Vector speed, String feet, String body, Level level) {
        super(position, speed);
        this.feet = new HitBox(feet);
        this.body = new HitBox(body);
        this.body.replaceColor(Color.rgb(0, 255, 0), Color.rgb(255, 255, 255), 150);
        this.level = level;
        // System.out.println(this.body.areColorsEqualsPrecision(Color.rgb(0,255,0),Color.rgb(0,250,0),1));
        this.lemData = new StudentData();
        this.state = LemmingsStates.Walk;
        addAnimations();

    }

    private void addAnimations(){
        animation = new TreeMap<LemmingsStates, AnimatedSprite>();
        animation.put(LemmingsStates.Walk, new AnimatedSprite("/resources/Lemming/Anim/walk"));
        animation.put(LemmingsStates.Falling, new AnimatedSprite("/resources/Lemming/Anim/falling"));
        animation.put(LemmingsStates.Pls, new AnimatedSprite("/resources/Lemming/Anim/PLS"));
        animation.put(LemmingsStates.LeavePls, new AnimatedSprite("/resources/Lemming/Anim/relevePLS"));
        animation.put(LemmingsStates.Construct, new AnimatedSprite("/resources/Lemming/Anim/construct"));
        animation.put(LemmingsStates.Vomit, new AnimatedSprite("/resources/Lemming/Anim/animVomit"));
    }


    public void setPositionHitbox(Vector p) {
        super.setPosition(p);
        this.feet.setPosition(p);
        this.body.setPosition(p);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void update(double deltaTime) {
        if (!isExited()) {
            switch (state) {
                case Walk:
                    forward(deltaTime, level);
                    break;
                case Falling:
                    falling(deltaTime);
                    break;
                case LeavePls:
                    leavePLS(deltaTime);
                    break;
                case Vomit:
                    vomit(deltaTime);
                    break;
                case Construct:
                    construct(deltaTime);
                    break;
                default:
                    break;
            }

            //System.out.println(this.toString());
            this.animation.get(state).update(deltaTime);
            this.animation.get(state).setPosition(this.position);
            this.setPositionHitbox(this.position);
        }
    }

    public boolean isGrounded(HashSet<Collidable> terrain, double deltatime) {
        return this.feet.willBeColliding(this.futurePosition(deltatime), terrain);
    }

    public void goToPLS(){
        this.state = LemmingsStates.Pls;
        this.body = new HitBox("resources/Lemming/hitboxes/pls/body.png");
        this.feet = new HitBox("resources/Lemming/hitboxes/pls/vomidetect.png");
        level.getTerrain().add(this);
        this.animation.get(state).reset();
    }

    public void vomit(double deltatime){
        if (this.animation.get(state).isEnded()){
            Vomit vomi;
            if (isFlipped){
                vomi= new Vomit(this.position, new Vector(-50,0));
                vomi.flipX();

            }else {
                vomi= new Vomit(this.position, new Vector(50,0));
            }
            level.getVomits().add(vomi);
            this.animation.get(state).reset();
            this.state = LemmingsStates.Walk;
        }
    }

    public void leavePLS(double deltatime){
        if (this.animation.get(state).isEnded()) {
            this.state = LemmingsStates.Walk;
            this.body = new HitBox("resources/Lemming/hitboxes/walk/body.png");
            this.feet = new HitBox("resources/Lemming/hitboxes/walk/feets.png");
            level.getTerrain().remove(this);
        }
    }

    public void falling(double deltaTime){
        if (!isGrounded(level.getTerrain(), deltaTime)) {
            this.speed.setY(speed.add(forces.mulScal(deltaTime)).getY());
            this.position.setY(position.add(this.speed.mulScal(deltaTime)).getY());
        } else {

            if (this.speed.getY() < 300) {
                this.state = LemmingsStates.Walk;
            } else {
                goToPLS();
            }
            this.speed.setY(0);
        }
    }

    public void construct( double deltaTime){
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
    }

    public LemmingsStates getState() {
        return state;
    }

    public boolean setState(LemmingsStates state) {
        if(this.state == LemmingsStates.Walk){
            switch (state){
                case Pls: goToPLS();
                    break;
                case Vomit: this.state=state;
                    break;
                case Construct: this.state=state;
                    break;
            }

            return true;
        }else if(this.state==LemmingsStates.Pls && state==LemmingsStates.LeavePls) {
            this.state = LemmingsStates.LeavePls;
            return true;
        }else {
            return false;
        }

    }

    public StudentData getLemData() {
        return lemData;
    }

    public void setLemData(StudentData lemData) {
        this.lemData = lemData;
    }

    public void draw(GraphicsContext gc) {
        this.animation.get(state).draw(gc);
        if(isSelected){
            this.body.draw(gc);
        }
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
            if (anim.getKey() != LemmingsStates.Pls)
                anim.getValue().flipX();
        }
        this.body.flipX();
        this.feet.flipX();
        isFlipped = !isFlipped;
    }

    public boolean isExited(){
        if(this.willBeColliding(position,level.getExit())){
            level.getGarbageLemmings().add(this);
            Drawer.getDrawer().deleteSomethingToDraw(this);
            return true;
        }else {
            return false;
        }
    }
}