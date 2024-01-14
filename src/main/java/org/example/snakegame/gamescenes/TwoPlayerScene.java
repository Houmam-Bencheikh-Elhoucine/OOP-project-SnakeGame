package org.example.snakegame.gamescenes;

import com.almasb.fxgl.core.math.Vec2;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.example.snakegame.controllerclasses.Camera;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.controllerclasses.RandGen;
import org.example.snakegame.gameclasses.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.snakegame.globals.GameParameters.*;
import static org.example.snakegame.globals.GameParameters.MAP_HEIGHT;

public class TwoPlayerScene extends GameScene {
    Stage stage;
    KeyboardPlayerSnake player1;
    KeyboardPlayerSnake player2;
    HashMap<String, KeyCode> inputKeys;
    HashMap<String, KeyCode> inputKeys2;
    Group root;
    Canvas c;
    long gameTime;
    List<GameObject> elements;
    AnimationTimer timer;
    int collCountMax = 10;
    int collCount = 0;
    boolean gameEnded;
    boolean reload;
    GameLabel labelScore = new GameLabel("", true, false, new Vec2(40, 40), 20);

    GameLabel labelWinner = new GameLabel("", false, true, new Vec2(WIDTH/2, HEIGHT/2-50), 50);
    public static TwoPlayerScene create(Stage stage) {

        HashMap<String, KeyCode> inputKeys = new HashMap<>();
        inputKeys.put("up", KeyCode.Z);
        inputKeys.put("down", KeyCode.S);
        inputKeys.put("left", KeyCode.Q);
        inputKeys.put("right", KeyCode.D);
        HashMap<String, KeyCode> inputKeys2 = new HashMap<>();
        inputKeys2.put("up", KeyCode.UP);
        inputKeys2.put("down", KeyCode.DOWN);
        inputKeys2.put("left", KeyCode.LEFT);
        inputKeys2.put("right", KeyCode.RIGHT);

        Group root = new Group();
        Canvas c = new Canvas(WIDTH, HEIGHT);
        return new TwoPlayerScene(stage, c, root, inputKeys, inputKeys2);
    }

    private TwoPlayerScene(Stage stage, Canvas c, Group root, HashMap<String, KeyCode> inputKeys, HashMap<String, KeyCode> inputKeys2) {
        super(root);
        this.stage = stage;
        this.inputKeys = inputKeys;
        this.inputKeys2 = inputKeys2;
        this.root = root;
        this.elements = new LinkedList<>();
        this.gameEnded = false;
        this.reload = false;
        this.player1 = new KeyboardPlayerSnake(10 + (float) WIDTH / 4, 10 + HEIGHT/2, 20, 20, score1, false, inputKeys);
        this.player2 = new KeyboardPlayerSnake(10 + (float) 3* WIDTH / 4, 10 + HEIGHT/2, 20, 20, score2, false, inputKeys2);

        Camera.update(new Vec2(WIDTH/2, HEIGHT/2));
        //new MousePlayerSnake(10 + (float) MAP_WIDTH / 4, 10 + (float) MAP_HEIGHT / 2, 20, 20);
        this.elements.add(labelScore);
        this.elements.add(labelWinner);
        this.elements.add(this.player1);
        this.elements.add(this.player2);
        this.c = c;
        this.elements.add(new Obstacle(0, 0, 20, HEIGHT*2));
        this.elements.add(new Obstacle(0, 0, WIDTH*2, 20));
        this.elements.add(new Obstacle(WIDTH-10, 0, 20, HEIGHT*2));
        this.elements.add(new Obstacle(0, HEIGHT-10, WIDTH*2, 20));
        for (int i = 0; i < 2; i++){
            elements.add(new AiSnake(RandGen.randInt(WIDTH), RandGen.randInt(HEIGHT), 25, 25));
        }
        /*for (int i = 0; i < RandGen.randInt(10, maxObstacles); i++) {
            elements.add(new Obstacle(RandGen.randInt(WIDTH), RandGen.randInt(HEIGHT),
                    RandGen.randInt(5, 10) * 10, RandGen.randInt(5, 10) * 10));
        }*/

        for (int i = 0; i < collCountMax; i++) {
            this.elements.add(new Food(RandGen.randInt(WIDTH), RandGen.randInt(HEIGHT),
                    RandGen.randInt(1, 5)));
        }

        collCount = collCountMax;

    }

    public void startScene() {
        GraphicsContext gc = this.c.getGraphicsContext2D();
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
                gc.clearRect(0, 0, MAP_WIDTH, MAP_HEIGHT);
                elements.forEach(go->{
                    if(!go.getType().equals("Snake") || !gameEnded) {
                        go.update(delta);
                    }
                    if (go instanceof SnakeAbstract) {
                        for (GameObject oth : elements) {
                            if (((SnakeAbstract) go).Collided(oth)) {
                                ((SnakeAbstract) go).addCollider(oth);
                            }
                        }
                    }
                    go.draw(gc);

                });

                if (player1.isDead()) {
                    labelWinner.setText("Player 2 Won!!!\nPlayer 1 scored: "+score1.get()+"\nPlayer2 scored: "+score2.get()+"\nPress any key to restart");
                    labelWinner.show();
                    labelScore.hide();
                    gameEnded = true;
                }
                if (player2.isDead()) {
                    labelWinner.setText("Player 1 Won!!!\nPlayer 1 scored: "+score1.get()+"\nPlayer2 scored: "+score2.get()+"\nPress any key to restart");
                    labelWinner.show();
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
                    elements.add(new Food(RandGen.randInt(WIDTH), RandGen.randInt(HEIGHT),
                            RandGen.randInt(1, 10)));
                    collCount++;
                }
                labelScore.setText("Player1: "+score1.get()+"\nPlayer2: "+score2.get());
                Input.clean();
                gameTime = l;
            }
        };

        this.timer = timer;
        timer.start();
        this.root.getChildren().add(this.c);

        this.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ESCAPE){
                SceneManager.goToMenu(stage);
                return;
            }
            if(gameEnded){
                if (!reload) {
                    reload = true;
                    gameEnded = false;
                    reloadScene();
                }
                return;
            }
            Input.add(e.getCode(), true);
        });
        this.setOnMouseMoved(e->Input.setNewMousePos((float) e.getX() ,(float) e.getY()));
        stage.setScene(this);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stopTimer() {
        timer.stop();
    }

    public void reloadScene(){
        //System.out.println("reload");
        SceneManager.goToTwoPlayers(this.stage);
    }

}
