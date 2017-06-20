package sample.view;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface used to normalise drawable objects
 * @author Jean
 */
public interface DrawAble {
    void draw(GraphicsContext gc);
    float getLayer();
}
