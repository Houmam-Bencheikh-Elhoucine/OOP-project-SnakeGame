package org.example.snakegame.gamescenes;

import com.almasb.fxgl.core.math.Vec2;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.controllerclasses.RandGen;
import org.example.snakegame.gameclasses.*;
import org.example.snakegame.globals.GameParameters;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.snakegame.globals.GameParameters.*;

public class SinglePlayerScene extends GameScene {
    Stage stage;
    KeyboardPlayerSnake player;
    HashMap<String, KeyCode> inputKeys;
    Group root;
    Canvas c;
    long gameTime;
    List<GameObject> elements;
    AnimationTimer timer;
    int collCountMax = 100;
    int maxObstacles = 0;
    int maxAi = 2;
    int aiCount = 0;
    int collCount = 0;
    int obsCount = 0;
    boolean gameEnded;
    boolean reload = false;
    GameLabel labelScore = new GameLabel("", true, false, new Vec2(20, 20), 20);
    GameLabel labelDeathMessage = new GameLabel("", false, true, new Vec2(WIDTH/2, HEIGHT/2-50), 50);
    public static SinglePlayerScene create(Stage stage) {
        score1.set(0);
        score2.set(0);
        HashMap<String, KeyCode> inputKeys = new HashMap<>();
        inputKeys.put("up", KeyCode.Z);
        inputKeys.put("down", KeyCode.S);
        inputKeys.put("left", KeyCode.Q);
        inputKeys.put("right", KeyCode.D);
        Group root = new Group();
        Canvas c = new Canvas(WIDTH, HEIGHT);
        return new SinglePlayerScene(stage, c, root, inputKeys);
    }

    private SinglePlayerScene(Stage stage, Canvas c, Group root, HashMap<String, KeyCode> inputKeys) {
        super(root);
        this.stage = stage;
        this.inputKeys = inputKeys;
        this.root = root;
        this.elements = new LinkedList<>();
        this.gameEnded = false;
        this.reload = false;
        this.player = new KeyboardPlayerSnake(10 + (float) WIDTH/4, 10 + (float) MAP_HEIGHT / 2, 20, 20, score1, true, inputKeys);
        labelScore.show();
        labelDeathMessage.hide();
        //new MousePlayerSnake(10 + (float) MAP_WIDTH / 4, 10 + (float) MAP_HEIGHT / 2, 20, 20);
        this.elements.add(labelScore);
        this.elements.add(this.player);

        this.c = c;
        for (int i = 0; i < collCountMax; i++) {
            this.elements.add(new Food(RandGen.randInt(WIDTH/2, MAP_WIDTH - WIDTH/2), RandGen.randInt(HEIGHT/2, MAP_HEIGHT - HEIGHT/2),
                    RandGen.randInt(1, 5)));
        }
        for (int i = 0; i < maxAi; i++) {
            this.elements.add(new AiSnake(RandGen.randInt(WIDTH), RandGen.randInt(HEIGHT), 25, 25));
        }
        aiCount = maxAi;
        collCount = collCountMax;

    }

    public void startScene() {
        GraphicsContext gc = this.c.getGraphicsContext2D();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //System.out.println("handle!!"+gameEnded);

                //System.out.println("working");
                /*
                 main loop:
                  read inputs
                  update objects
                  draw to canvas
                  render
                 */
                double delta = (l - gameTime) / 1000000000.0;// to be used later
                gc.clearRect(0, 0, MAP_WIDTH, MAP_HEIGHT);
                for (GameObject go : elements) {
                    go.update(delta);
                    if (go instanceof SnakeAbstract) {
                        for (GameObject oth : elements) {
                            if (((SnakeAbstract) go).Collided(oth)) {
                                ((SnakeAbstract) go).addCollider(oth);
                            }
                        }
                    }
                    go.draw(gc);

                    labelScore.setText("Score: "+score1.get());
                }
                if (player.isDead()) {
                    elements.add(labelDeathMessage);
                    labelDeathMessage.show();
                    labelDeathMessage.setText("You died!\n your score: "+score1.get()+"\nPress any key to restart");
                    labelScore.hide();
                    gameEnded = true;
                }
                elements = elements.stream().filter(e -> {
                    if(e.getType().equals("Snake")){
                        return !((SnakeAbstract)e).isDead();
                    }
                    if (e instanceof Food) {
                        if (((Food) e).collected) {
                            collCount--;
                            return false;
                        }
                        return true;
                    }
                    return true;
                }).collect(Collectors.toList());
                // clean input buffer
                for (int i = collCount; i < collCountMax; i++) {
                    elements.add(new Food(RandGen.randInt(MAP_WIDTH), RandGen.randInt(MAP_HEIGHT),
                            RandGen.randInt(1, 5)));
                    collCount++;
                }
                for (;obsCount < maxObstacles; obsCount++) {
                    GameObject toRem = elements.get(RandGen.randInt(elements.size()));
                    if(toRem.getType().equals("Obstacle")){
                        elements.remove(toRem);
                    }
                    elements.add(new Obstacle(RandGen.randInt(WIDTH/2, MAP_WIDTH - WIDTH/2), RandGen.randInt(HEIGHT/2, MAP_HEIGHT - HEIGHT/2),
                            RandGen.randInt(5, 20) * 10, RandGen.randInt(5, 20) * 10));
                }
                Input.clean();
                maxObstacles = score1.get();
                maxAi = score1.get() + 2;
                gameTime = l;
            }
        };

        this.timer = timer;
        timer.start();
        this.root.getChildren().add(this.c);

        this.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ESCAPE){
                SceneManager.goToMenu(stage);
            }
            if(gameEnded && !reload){
                gameEnded = false;
                reloadScene();
            }
            Input.add(e.getCode(), true);
        });
        this.setOnMouseEntered(e->Input.setNewMousePos((float) e.getX(), (float) e.getY()));
        this.setOnMouseMoved(e->Input.setNewMousePos((float) e.getX() ,(float) e.getY()));
        stage.setScene(this);
        stage.setResizable(false);
        stage.show();
    }

    public void reloadScene(){
        SceneManager.goToSinglePlayer(this.stage);
    }
    public void stopTimer(){
        timer.stop();
    }
}
