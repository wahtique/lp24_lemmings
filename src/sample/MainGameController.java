package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class MainGameController {
    @FXML
    private Canvas canvas;

    @FXML
    private BorderPane panneau;

    private Color currentColor = Color.color(1, 0.0078, 0);
    private HitBox omaia;
    private HitBox star;

    GraphicsContext gc;
    MainGameUpdater timeSetter;

    private AnimatedSprite anim;

    public void start(){
        gc = canvas.getGraphicsContext2D();

        timeSetter = new MainGameUpdater();
        timeSetter.start(this);

        omaia = new HitBox("resources/images/omaia.png");
        star = new HitBox("resources/images/star.png");
        ArrayList<String> urls = new ArrayList<String>();
        urls.add("resources/Anim/taiste/taiste0000.png");
        urls.add("resources/Anim/taiste/taiste0001.png");
        urls.add("resources/Anim/taiste/taiste0002.png");
        urls.add("resources/Anim/taiste/taiste0003.png");


        //anim = new AnimatedSprite("resources/Anim/taiste");
        anim = new AnimatedSprite(urls);
     //   anim.flipX();

        omaia.setPosition(new Vector(100,100));
        star.setPosition(new Vector(9,9));
        omaia.drawImage(gc);
        star.drawImage(gc);


    }

    public void update(double deltaTime) {
       //System.out.println("FPS : "+ 1/deltaTime );
       autoSetCanvasDim();
       //gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
       anim.update(deltaTime);
       anim.drawImage(gc);
    }

    public void onMouseClick (MouseEvent e){
        double time = System.nanoTime();
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        //FORMULA: (int)(mouse.getX()/gcScale-imagePositionX)-1 (all coordinates are canvas relative) gcScale should be left on 1, and you should modify only CanvasScale
//        System.out.println(e.getX()+" : "+e.getY());
        if (e.isPrimaryButtonDown()) {
//            omaia.modifyPixelCanvasRef(new Vector ((int) ((e.getX())), (int) ((e.getY()))), currentColor);
            omaia.flipX();
            star.setPosition(new Vector(e.getX(),e.getY()));
            System.out.println("\n"+ omaia.willBeColliding(omaia.position,star));

            System.out.println("temps passé ds la boucle : " + (double)(System.nanoTime()-time)/1000000000.0);
        }else if (e.isSecondaryButtonDown()){
//            currentColor = omaia.getPixelColorCanvasRef(new Vector ((int)e.getX(),(int)e.getY()));
            System.out.println(omaia.isInHitbox(new Vector(e.getX(),e.getY())));

        }
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        omaia.drawImage(gc);
        star.drawImage(gc);

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