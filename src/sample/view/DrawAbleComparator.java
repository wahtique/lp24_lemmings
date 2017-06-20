package sample.view;

import java.util.Comparator;

/**Comparator to order the drawable object by layer
 * Created by naej on 06/06/17.
 */
public class DrawAbleComparator implements Comparator<DrawAble> {
    /**
     * compare method
     * @param o first Drawable object
     * @param t1 seconde Drawable object
     * @return 0 if they are on the same layer, a negative if o is on a layer behind tl and a positive otherwise
     */
    @Override
    public int compare(DrawAble o, DrawAble t1) {
       /* if( Float.compare(o.getLayer(),t1.getLayer()) >= 0){
            return 1;
        }else{
            return -1;
        }*/
       return Float.compare(o.getLayer(),t1.getLayer());


    }
}
