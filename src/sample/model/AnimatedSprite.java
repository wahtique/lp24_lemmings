package sample.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Created by naej on 12/05/17.
 */
//TODO document this
public class AnimatedSprite extends Sprite {

    private boolean isLooping;
    private ArrayList<WritableImage> listOfImages;
    private int currentImage = 0;
    private int framerate = 6;
    private double currentTime = 0;

    public AnimatedSprite(String url) {
        super();
        listOfImages = new ArrayList<WritableImage>();

        try {
            URI uri = this.getClass().getResource(url).toURI();

            Path myPath;


            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem;
                try {
                    fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                }catch (Exception e){
                    fileSystem = FileSystems.getFileSystem(uri);
                }
                myPath = fileSystem.getPath(url);
            } else {
                myPath = Paths.get(uri);
            }

            Stream<Path> walk = Files.walk(myPath, 1);
            ArrayList<String> urls = new ArrayList<String>();
            String config = "";

            for (Iterator<Path> it = walk.iterator(); it.hasNext(); ) {
                String temp = it.next().getFileName().toString();
          //      System.out.println(temp);
                if (temp.endsWith(".png")) {
                    urls.add(url + "/" + temp);
                   // System.out.println(temp);
                } else if (temp.endsWith(".txt")) {
                    config = url + "/" + temp;
                   // System.out.println(config);
                }
            }

            urls.sort(new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return s.compareTo(t1);
                }
            });

            for (String nameOfImage : urls) {
                //System.out.println(nameOfImage);
                Image image = new Image(nameOfImage);
                listOfImages.add(new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight()));

            }

            if (!config.equals("")) {
              //  System.out.println(config);
                BufferedReader input = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(config)));
                String line = input.readLine();
                while (line != null) {
                    //System.out.println(line);

                    String[] words = line.split("=");
                    if (words[0].contentEquals("framerate")) {
                        framerate = Integer.parseInt(words[1]);
                        //System.out.println("framerate="+framerate);
                    } else if (words[0].contentEquals("looping")) {
                        isLooping = words[1].equals("true");
                        //System.out.println("isLooping="+isLooping);
                    }

                    line = input.readLine();
                }
//                System.out.println(input.readLine());
//                System.out.println(input.readLine());

            }

        } catch (Exception e) {
            System.out.println("Can't instantiate AnimatedSprite !!!");
            e.printStackTrace();
        }


        renderedImage = new ImageView(listOfImages.get(currentImage));
    }

    @Deprecated
    public AnimatedSprite(ArrayList<String> urls) {
        super();
        listOfImages = new ArrayList<WritableImage>();
        for (String url : urls) {
            System.out.println(url);
            Image image = new Image(url);
            listOfImages.add(new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight()));
        }
        renderedImage = new ImageView(listOfImages.get(currentImage));
    }

    public void update(double deltaTime) {
        currentTime += deltaTime;
        if (isLooping && currentTime > getAnimationLength()) {
            currentTime -= getAnimationLength();
        }
        if (!isEnded()) {
            currentImage = (int) ((currentTime / getAnimationLength()) * listOfImages.size());
            //   System.out.println(currentImage);

            renderedImage.setImage(listOfImages.get(currentImage));
        }
    }

    public double getAnimationLength() {
        return listOfImages.size() * 1.0 / framerate;
    }

    public boolean isEnded() {
        return currentTime > getAnimationLength();
    }

    @Override
    public void replaceColor(Color startColor, Color replacementColor, int tolerance) {
        for (WritableImage image : listOfImages) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    if (areColorsEqualsTolerance(image.getPixelReader().getColor(x, y), startColor, tolerance)) {
                        image.getPixelWriter().setColor(x, y, replacementColor);
                    }
                }
            }
        }
    }

    public void reset() {
        currentTime = 0;
        currentImage = 0;
        renderedImage.setImage(listOfImages.get(currentImage));

    }
}