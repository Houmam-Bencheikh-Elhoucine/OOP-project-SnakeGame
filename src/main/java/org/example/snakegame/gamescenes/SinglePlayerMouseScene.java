package org.example.snakegame.gamescenes;

import com.almasb.fxgl.core.math.Vec2;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.controllerclasses.RandGen;
import org.example.snakegame.gameclasses.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.snakegame.globals.GameParameters.*;

public class SinglePlayerMouseScene extends GameScene{
    Stage stage;
    MousePlayerSnake player;
    Group root;
    Canvas c;
    long gameTime;
    List<GameObject> elements;
    AnimationTimer timer;
    int collCountMax = 100;
    float maxObstacles = 0;
    int collCount = 0;
    int obsCount = 0;
    boolean gameEnded;
    boolean reload = false;
    GameLabel labelScore = new GameLabel("", true, false, new Vec2(20, 20), 20);
    GameLabel labelDeathMessage = new GameLabel("", false, true, new Vec2(WIDTH/2, HEIGHT/2-50), 50);

    public static SinglePlayerMouseScene create(Stage stage) {
        score1.set(0);
        score2.set(0);
        Group root = new Group();
        Canvas c = new Canvas(WIDTH, HEIGHT);
        return new SinglePlayerMouseScene(stage, c, root);
    }

    private SinglePlayerMouseScene(Stage stage, Canvas c, Group root) {
        super(root);
        this.stage = stage;
        this.root = root;
        this.elements = new LinkedList<>();
        this.gameEnded = false;
        this.reload = false;
        this.player = new MousePlayerSnake(10 + (float) MAP_WIDTH / 4, 10 + (float) MAP_HEIGHT / 2, 20, 20);
        this.elements.add(this.player);
        labelScore.show();
        labelDeathMessage.hide();
        this.c = c;
        for (int i = 0; i < collCountMax; i++) {
            this.elements.add(new Food(RandGen.randInt(WIDTH/2, MAP_WIDTH - WIDTH/2), RandGen.randInt(HEIGHT/2, MAP_HEIGHT - HEIGHT/2),
                    RandGen.randInt(1, 5)));
        }
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
        this.setOnMouseMoved(e->Input.setNewMousePos((float) e.getSceneX() ,(float) e.getSceneY()));
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
