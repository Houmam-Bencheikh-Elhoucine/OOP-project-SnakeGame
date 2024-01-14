package org.example.snakegame.gamescenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
public abstract class GameScene extends Scene{
    public GameScene(Parent root){
        super(root);
    }
    public abstract void startScene();
    public abstract void stopTimer();

}
