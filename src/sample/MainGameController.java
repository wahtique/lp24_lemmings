package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;


public class MainGameController {
    @FXML
    private Canvas canvas;

    @FXML
    private BorderPane panneau;

    private Color currentColor = Color.color(1, 0.0078, 0);
    private Level test = new Level(new HashSet<HitBox>(), new HashSet<Lemmings>());


    GraphicsContext gc;
    MainGameUpdater timeSetter;

    private AnimatedSprite anim;

    public void start() throws IOException, URISyntaxException {
        gc = canvas.getGraphicsContext2D();

        timeSetter = new MainGameUpdater();
        timeSetter.start(this);
        test.getTerrain().add(new HitBox("resources/images/LevelTest.png"));
        Lemmings roger = new Lemmings(new Vector(120,20), new Vector(10,0), new Vector(0,1),"resources/images/Lfeet.png","resources/images/Lbody.png");
        test.getLemmingsList().add(roger);

        String url = "/resources/Anim/taiste";

        anim = new AnimatedSprite("/resources/Anim/taiste");


    }

    public void update(double deltaTime) {
       //System.out.println("FPS : "+ 1/deltaTime );
       autoSetCanvasDim();
       gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
       test.update(deltaTime);
       test.drawLevel(gc);

    }

    public void onMouseClick (MouseEvent e){
        test.getLemmingsList().stream().findFirst().get().setPosition(new Vector(e.getX(),e.getY()));
        /*
        double time = System.nanoTime();
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
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
        omaia.drawImage(gc);
        star.drawImage(gc);
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