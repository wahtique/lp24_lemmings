package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;



public class MainGameController {
    @FXML
    private Canvas canvas;

    @FXML
    private BorderPane panneau;

    private Color currentColor = Color.color(1, 0.0078, 0);
    private HitBox test;
    private HitBox star;

    GraphicsContext gc;
    MainGameUpdater timeSetter;

    public void start(){
        gc = canvas.getGraphicsContext2D();

        timeSetter = new MainGameUpdater();
        timeSetter.start(this);

        test = new HitBox("resources/images/omaia.png");
        star = new HitBox("resources/images/star.png");
        test.setPosition(new Vector(100,100));
        star.setPosition(new Vector(9,9));
        test.drawImage(gc);
        star.drawImage(gc);


    }

    public void update(double deltaTime) {
       //System.out.println("FPS : "+ 1/deltaTime );
       autoSetCanvasDim();
    }

    public void onMouseClick (MouseEvent e){
        double time = System.nanoTime();
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        //FORMULA: (int)(mouse.getX()/gcScale-imagePositionX)-1 (all coordinates are canvas relative) gcScale should be left on 1, and you should modify only CanvasScale
//        System.out.println(e.getX()+" : "+e.getY());
        if (e.isPrimaryButtonDown()) {
//            test.modifyPixelCanvasRef(new Vector ((int) ((e.getX())), (int) ((e.getY()))), currentColor);
            star.setPosition(new Vector(e.getX(),e.getY()));
            System.out.println("\n"+test.willBeColliding(test.position,star));

            System.out.println("temps passé ds la boucle : " + (double)(System.nanoTime()-time)/1000000000.0);
        }else if (e.isSecondaryButtonDown()){
//            currentColor = test.getPixelColorCanvasRef(new Vector ((int)e.getX(),(int)e.getY()));
            System.out.println(test.isInHitbox(new Vector(e.getX(),e.getY())));

        }
        test.drawImage(gc);
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