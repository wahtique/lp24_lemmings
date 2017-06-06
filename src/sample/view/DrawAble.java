package sample.view;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created by naej on 06/06/17.
 */
public interface DrawAble {
    void draw(GraphicsContext gc);
    float getLayer();
}
