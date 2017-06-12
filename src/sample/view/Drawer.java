package sample.view;

import javafx.scene.canvas.Canvas;

import java.util.TreeSet;

/**
 * Created by naej on 06/06/17.
 */
public class Drawer {
    private Drawer(){}

    private TreeSet<DrawAble> toDraw = new TreeSet<>(new DrawAbleComparator());
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
          /*  for (DrawAble tod : toDraw){
                tod.draw(cv.getGraphicsContext2D());
            }*/
        }else{
            System.out.println("NO CANVAS");
        }
    }

    public void addSomethingToDraw(DrawAble something){
        toDraw.add(something);
    }
    public void deleteSomethigToDraw(DrawAble something){
        toDraw.remove(something);
    }
    public void clearDrawer(){
        toDraw.clear();
    }
}
