package sample.view;

import java.util.Comparator;

/**
 * Created by naej on 06/06/17.
 */
//TODO document this
public class DrawAbleComparator implements Comparator<DrawAble> {
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
