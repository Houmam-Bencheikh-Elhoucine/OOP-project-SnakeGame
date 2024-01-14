package org.example.snakegame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.controllerclasses.RandGen;
import org.example.snakegame.gameclasses.*;
import org.example.snakegame.gamescenes.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;
import static org.example.snakegame.globals.GameParameters.*;

public class HelloApplication extends Application {

    Canvas c;
    Group root = new Group();
    long gameTime = 0;
    List<GameObject> elements = new LinkedList<>();
    SnakeAbstract player;
    SnakeAbstract player2;
    //SnakeAbstract aiSnake;
    @Override
    public void start(Stage stage) throws IOException{
        stage.setTitle("Snake Game");
        SceneManager.goToMenu(stage);
    }
    @Override
    public void stop(){
        //p.stop();
        Platform.exit();
    }
    public static void main(String[] args) {
        launch();
    }

}
