package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Created by naej on 12/05/17.
 */
public class AnimatedSprite extends Sprite{

    private boolean isLooping;
    private ArrayList<WritableImage> listOfImages;
    private int currentImage = 0;
    private int framerate = 6;
    private double currentTime =0;

    public AnimatedSprite(String url){
        super();
        listOfImages = new ArrayList<WritableImage>();
        try {
            URI uri = this.getClass().getResource(url).toURI();
            Path myPath;


            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                myPath = fileSystem.getPath(url);
            } else {
                myPath = Paths.get(uri);
            }

            Stream<Path> walk = Files.walk(myPath, 1);
            ArrayList<String> urls = new ArrayList<String>();

            for (Iterator<Path> it = walk.iterator(); it.hasNext();){
                String temp = it.next().getFileName().toString();
                if (temp.endsWith(".png")) {
                    urls.add(url+"/" + temp);
                }
            }

            urls.sort(new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return s.compareTo(t1);
                }
            });

            for(String nameOfImage:urls){
                System.out.println(nameOfImage);
                Image image = new Image(nameOfImage);
                listOfImages.add(new WritableImage(image.getPixelReader(),(int)image.getWidth(),(int)image.getHeight()));

            }

        } catch (Exception e) {
            System.out.println("Can't instantiate AnimatedSprite !!!");
            System.exit(1);
        }


        renderedImage = new ImageView(listOfImages.get(currentImage));
    }


    public AnimatedSprite(String url,boolean isLooping){
        super();
        listOfImages = new ArrayList<WritableImage>();
        try {
            URI uri = this.getClass().getResource(url).toURI();
            Path myPath;


            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                myPath = fileSystem.getPath(url);
            } else {
                myPath = Paths.get(uri);
            }

            Stream<Path> walk = Files.walk(myPath, 1);
            ArrayList<String> urls = new ArrayList<String>();

            for (Iterator<Path> it = walk.iterator(); it.hasNext();){
                String temp = it.next().getFileName().toString();
                if (temp.endsWith(".png")) {
                    urls.add(url+"/" + temp);
                }
            }

            urls.sort(new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return s.compareTo(t1);
                }
            });

            for(String nameOfImage:urls){
                System.out.println(nameOfImage);
                Image image = new Image(nameOfImage);
                listOfImages.add(new WritableImage(image.getPixelReader(),(int)image.getWidth(),(int)image.getHeight()));

            }

        } catch (Exception e) {
            System.out.println("Can't instantiate AnimatedSprite !!!");
            System.exit(1);
        }


        renderedImage = new ImageView(listOfImages.get(currentImage));
        this.isLooping = isLooping;
    }

    public AnimatedSprite(ArrayList<String> urls){
        super();
        listOfImages = new ArrayList<WritableImage>();
        for (String url: urls){
            System.out.println(url);
            Image image = new Image(url);
            listOfImages.add(new WritableImage(image.getPixelReader(),(int)image.getWidth(),(int)image.getHeight()));
        }
        renderedImage = new ImageView(listOfImages.get(currentImage));
    }

    public void update(double deltaTime){
        currentTime += deltaTime;
        if (isLooping && currentTime > getAnimationLength()){
            currentTime -= getAnimationLength();
        }
        if (!isEnded()) {
            currentImage = (int) ((currentTime / getAnimationLength()) * listOfImages.size());
         //   System.out.println(currentImage);

            renderedImage.setImage(listOfImages.get(currentImage));
        }
    }

    public double getAnimationLength(){
        return listOfImages.size()*1.0/framerate;
    }

    public boolean isEnded(){
        return currentTime > getAnimationLength();
    }
}
