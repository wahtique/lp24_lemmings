package sample.controler;

import javafx.animation.AnimationTimer;
import sample.controler.MainGameController;

/**
 * Created by naej on 08/05/17.
 */
public class MainGameUpdater extends AnimationTimer{

    private float timeSpeed = 1;
    private float previousTime;
    private float startTime;

    private MainGameController mainGame;

    public void start(){
        super.start();
        previousTime = System.nanoTime();
        startTime = previousTime;
    }

    public void start(MainGameController mainGame){
        super.start();
        this.mainGame = mainGame;
        previousTime = System.nanoTime();
        startTime = previousTime;
    }

    public void handle(long currentNanoTime) {
        double deltaTime = (currentNanoTime - previousTime) / 1000000000.0 * timeSpeed;

        //Here call function you want to use at each update
        if (mainGame != null) {
            mainGame.update(deltaTime);
        }

        previousTime = currentNanoTime;
    }

    public float getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(float timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public double getAbsoluteTimeSpend(){
        return (System.nanoTime() - startTime) / 1000000000.0;
    }

    public void setMaingame(MainGameController main){
        this.mainGame = main;
    }


}
