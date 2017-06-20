package sample.view;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Class used to print drawable in a simple way
 * @author Jean
 */
public class Drawer {
    private Drawer(){}

    private ArrayList<DrawAble> toDraw = new ArrayList<>();
    private Canvas cv = null;

    private static class DrawerHolder{
        private final static Drawer instance = new Drawer();
    }

    public static Drawer getDrawer(){
        return DrawerHolder.instance;
    }

    public void setCanvas(Canvas canvas){
        cv = canvas;
    }

    public void draw(){
        if (cv != null){
            cv.getGraphicsContext2D().clearRect(0,0,cv.getWidth(),cv.getHeight());
            toDraw.forEach(o->o.draw(cv.getGraphicsContext2D()));

        }else{
            System.out.println("NO CANVAS");
        }
    }

    public void addSomethingToDraw(DrawAble something){
        toDraw.add(something);
        toDraw.sort(new DrawAbleComparator());
    }
    public void deleteSomethingToDraw(DrawAble something){
      //  System.out.println(toDraw);
        toDraw.remove(something);
        //System.out.println(toDraw);

    }
    public void clearDrawer(){
        toDraw.clear();
    }
}
