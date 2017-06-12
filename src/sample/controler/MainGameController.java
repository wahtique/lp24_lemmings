package sample.controler;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.model.*;
import sample.view.Drawer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;


public class MainGameController {
    @FXML
    private Canvas canvas;

    @FXML
    private Pane panneau;

   // private Color currentColor = Color.color(1, 0.0078, 0);
    private Level test = new Level(new HashSet<Collidable>(), new HashSet<Lemmings>());

    private Drawer drawer;

    private AnimatedSprite anim;

    public void start() throws IOException, URISyntaxException {
        drawer = Drawer.getDrawer();
        drawer.setCanvas(canvas);
        Sprite bg = new Sprite("resources/levels/testlevel1/bg-5.png");
        bg.setLayer(-10);
        drawer.addSomethingToDraw(bg);

        MainGameUpdater timeSetter = new MainGameUpdater();
        timeSetter.start(this);


        test.getTerrain().add(new HitBox("resources/levels/testlevel1/terrain1.png"));
        test.getTerrain().forEach(o->drawer.addSomethingToDraw(o));
        Lemmings roger = new Lemmings(new Vector(120,20), new Vector(50,0), new Vector(0,100),
                                        "resources/Lemming/hitboxes/walk/feets.png",
                                        "resources/Lemming/hitboxes/walk/body.png");
        test.getLemmingsList().add(roger);

        test.getLemmingsList().forEach(o->drawer.addSomethingToDraw(o));


    }

    public void update(double deltaTime) {
       //System.out.println("FPS : "+ 1/deltaTime );
       autoSetCanvasDim();
//       anim.update(deltaTime);
       test.update(deltaTime);
       drawer.draw();
     //  test.drawLevel(canvas.getGraphicsContext2D());

    }

    public void onMouseClick (MouseEvent e){
        test.getLemmingsList().stream().findFirst().get().setPosition(new Vector(e.getX(),e.getY()));

        /*
        double time = System.nanoTime();
        //FORMULA: (int)(mouse.getX()/gcScale-imagePositionX)-1 (all coordinates are canvas relative) gcScale should be left on 1, and you should modify only CanvasScale
//        System.out.println(e.getX()+" : "+e.getY());
        if (e.isPrimaryButtonDown()) {
//            omaia.modifyPixelCanvasRef(new Vector ((int) ((e.getX())), (int) ((e.getY()))), currentColor);
           // omaia.flipX();
            star.setPosition(new Vector(e.getX(),e.getY()));
            Vector temp = null;
            temp = omaia.getHigherCollidingPoint(omaia.position,star);
            if (temp != null){
                System.out.println(temp);
            }


            System.out.println("temps passé ds la boucle : " + (double)(System.nanoTime()-time)/1000000000.0);
        }else if (e.isSecondaryButtonDown()){
//            currentColor = omaia.getPixelColorCanvasRef(new Vector ((int)e.getX(),(int)e.getY()));
            System.out.println(omaia.isInHitbox(new Vector(e.getX(),e.getY())));
            System.out.println(e.getX() +" : " + e.getY());
            timeSetter.setTimeSpeed(2);
        }
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        omaia.draw(gc);
        star.draw(gc);
*/
    }
    public void autoSetCanvasDim(){

        if (panneau.getWidth()>panneau.getHeight()) {
            canvas.setScaleX(panneau.getHeight() / panneau.getPrefHeight());

            canvas.setScaleY(panneau.getHeight() / panneau.getPrefHeight());

        }else{
            canvas.setScaleY(panneau.getWidth() / panneau.getPrefWidth());

            canvas.setScaleX(panneau.getWidth() / panneau.getPrefWidth());

        }
    }
    public void dumbAutoSetCanvasDim(){

        canvas.setScaleY(panneau.getHeight() / panneau.getPrefHeight());
        canvas.setScaleX(panneau.getWidth() / panneau.getPrefWidth());


    }
}

