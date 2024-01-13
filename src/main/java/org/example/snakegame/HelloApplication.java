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

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class HelloApplication extends Application {
    final int WIDTH = 800;
    final int HEIGHT = 600;
    Canvas c;
    Group root = new Group();
    long gameTime = 0;
    List<GameObject> elements = new LinkedList<>();
    SnakeAbstract player;
    SnakeAbstract player2;
    SnakeAbstract aiSnake;
    @Override
    public void start(Stage stage) throws IOException{
        /*HashMap<String, KeyCode> inputKeys = new HashMap<>();
        inputKeys.put("up", KeyCode.Z);
        inputKeys.put("down", KeyCode.S);
        inputKeys.put("left", KeyCode.Q);
        inputKeys.put("right", KeyCode.D);
        player = new KeyboardPlayerSnake(10+ (float) WIDTH /2, 10+ (float) HEIGHT /2, 20, 20, inputKeys);
        HashMap<String, KeyCode> inputKeys2 = new HashMap<>();
        inputKeys2.put("up", KeyCode.UP);
        inputKeys2.put("down", KeyCode.DOWN);
        inputKeys2.put("left", KeyCode.LEFT);
        inputKeys2.put("right", KeyCode.RIGHT);
        player2 = new KeyboardPlayerSnake(10+ (float) WIDTH /4, 10+ (float) HEIGHT /4, 20, 20, inputKeys2);
        aiSnake = new AiSnake(10+ (float) WIDTH /8, 10+ (float) HEIGHT /8, 20, 20);
        */
        player = new MousePlayerSnake(10+ (float) WIDTH /2, 10+ (float) HEIGHT /2, 20, 20);
        c = new Canvas(800, 600);
        stage.setTitle("Snake Game - Part 1");
        GraphicsContext gc = c.getGraphicsContext2D();

        // render stuff in gc
        elements.add(player);
//        elements.add(player2);
//        elements.add(aiSnake);
        for(int i = 0; i < RandGen.randInt(2, 15); i++){
            elements.add(new Obstacle(RandGen.randInt(WIDTH), RandGen.randInt(HEIGHT),
                    RandGen.randInt(1, 15)*10, RandGen.randInt(1, 15)*10));
        }
        for(int i = 0; i < 20; i++){
            elements.add(new Food(RandGen.randInt(WIDTH), RandGen.randInt(HEIGHT),
                    RandGen.randInt(1, 10)));
        }
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                /*
                 main loop:
                  read inputs
                  update objects
                  draw to canvas
                  render
                 */
                double delta = (l - gameTime) / 1000000000.0;// to be used later
                gc.clearRect(0, 0, WIDTH, HEIGHT);
                for (GameObject go : elements) {
                    go.update(delta);
                    if(go instanceof SnakeAbstract) {
                        for (GameObject oth: elements){
                            if(((SnakeAbstract) go).Collided(oth)){
                                ((SnakeAbstract) go).addCollider(oth);
                                System.out.println("we have a collision");
                            }
                        }
                    }
                    go.draw(gc);
                    // check for collisions
                    /*
                    elements.remove(player);
                    player = new KeyboardPlayerSnake(10+ (float) WIDTH /2, 10+ (float) HEIGHT /2, 20, 20, inputKeys);
                    player2 = new KeyboardPlayerSnake(10+ (float) WIDTH /2, 10+ (float) HEIGHT /4, 20, 20, inputKeys2);
                    System.out.println("spawned!!");
                    elements.add(player);
                    elements.add(player2);*/
                    }

                elements = elements.stream().filter(e->{
                    if (e instanceof Food){
                        return !((Food)e).collected;
                    }
                    return true;
                }).collect(Collectors.toList());
                // clean input buffer
                Input.clean();
                gameTime = l;
            }
        };


        timer.start();
        root.getChildren().add(c);
        Scene s = new Scene(root);
        s.setOnKeyPressed(e-> Input.add(e.getCode(), true));
        s.setOnMouseMoved(e->Input.setNewMousePos((float) e.getX() ,(float) e.getY()));
        stage.setScene(s);

        stage.setResizable(false);

        stage.show();

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
