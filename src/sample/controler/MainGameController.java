package sample.controler;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sample.model.*;
import sample.view.Drawer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;

import static sample.model.SoundManager.getSoundManager;


public class MainGameController {
    @FXML
    private Canvas canvas;

    @FXML
    private Pane panneau;

   // private Color currentColor = Color.color(1, 0.0078, 0);
    private Level test = new Level(new HashSet<HitBox>(), new HashSet<Lemmings>());

    private Drawer drawer;

    private AnimatedSprite anim;

    private SoundManager sm;

    public void start() throws IOException, URISyntaxException, LineUnavailableException, UnsupportedAudioFileException
    {
        drawer = Drawer.getDrawer();
        drawer.setCanvas(canvas);
        Sprite bg = new Sprite("resources/images/testlevel/bg.png");
        bg.setLayer(-10);
        drawer.addSomethingToDraw(bg);

        MainGameUpdater timeSetter = new MainGameUpdater();
        timeSetter.start(this);


        test.getTerrain().add(new HitBox("resources/images/LevelTest.png"));
        test.getTerrain().forEach(o->drawer.addSomethingToDraw(o));
        Lemmings roger = new Lemmings(new Vector(120,20), new Vector(10,0), new Vector(0,10),"resources/images/Lfeet.png","resources/images/testLemming.png");
        Lemmings roger2 = new Lemmings(new Vector(50,20), new Vector(10,0), new Vector(0,10),"resources/images/Lfeet.png","resources/images/Lbody.png");
        test.getLemmingsList().add(roger);
        test.getLemmingsList().add(roger2);
        test.getLemmingsList().forEach(o->drawer.addSomethingToDraw(o));


        //anim = new AnimatedSprite("/resources/Anim/taiste",true);
        anim = new AnimatedSprite("/resources/Lemming/Anim/walk");
        anim.replaceColor(Color.rgb(0,255,0), Color.rgb(0,0,0),100);
        anim.setLayer(2);
        drawer.addSomethingToDraw(anim);


        getSoundManager().setBGM("/resources/Sound/bgm.wav");
        getSoundManager().playBGM();

    }

    public void update(double deltaTime) {
       //System.out.println("FPS : "+ 1/deltaTime );
       autoSetCanvasDim();
       anim.update(deltaTime);
       test.update(deltaTime);
       drawer.draw();
     //  test.drawLevel(canvas.getGraphicsContext2D());

    }

    public void onMouseClick (MouseEvent e) throws IOException
    {
        System.out.println("click");
        test.getLemmingsList().stream().findFirst().get().setPosition(new Vector(e.getX(),e.getY()));
        getSoundManager().playSFX("/resources/Sound/tuturu.wav");
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


