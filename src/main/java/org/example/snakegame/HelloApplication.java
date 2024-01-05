package org.example.snakegame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.gameclasses.AI;
import org.example.snakegame.gameclasses.GameObject;
import org.example.snakegame.gameclasses.Snake;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;

public class HelloApplication extends Application {
    final int WIDTH = 800;
    final int HEIGHT = 600;
    Canvas c;
    Group root = new Group();
    long gameTime = 0;
    List<GameObject> elements = new LinkedList<>();
    Snake player = new Snake(10, 10, 20, 20);
    //AI p = new AI(player);
    int step = 0;
    @Override
    public void start(Stage stage) throws IOException {
        c = new Canvas(800, 600);
        stage.setTitle("Snake Game - Part 1");
        GraphicsContext gc = c.getGraphicsContext2D();

        // render stuff in gc
        elements.add(player);
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

                try {
                    sleep(100-(l/1000000)%100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                double delta = (l - gameTime) / 1000000000.0;// to be used later
                for (GameObject go : elements) {
                    gc.clearRect(0, 0, WIDTH, HEIGHT);
                    go.update();
                    go.draw(gc);
                }

                // clean input buffer
                Input.clean();
                gameTime = l;
                step ++;
            }
        };

        timer.start();
        root.getChildren().add(c);
        Scene s = new Scene(root);
        s.setOnKeyPressed(e-> Input.add(e.getCode(), true));

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
