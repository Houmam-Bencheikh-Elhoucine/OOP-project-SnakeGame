package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.controllerclasses.RandGen;

public class Snake extends GameObject {
    int speed = 20;
    Vec2 velocity = new Vec2(0, 0);
    SnakeSection head;

    public Snake(int x, int y, int w, int h) {

        this.c = Color.rgb(RandGen.randInt(255), RandGen.randInt(255), RandGen.randInt(255));
        this.head = new SnakeSection(x, y, w, h, (int) c.getRed() * 255, (int) c.getGreen() * 255, (int) c.getBlue() * 255);
    }
    @Override
    public void update() {
        if (Input.isKeyPressed(KeyCode.UP)) {
//            System.out.println("Pressed UP");
            velocity.x = 0;
            velocity.y = -speed;

        }
        else if (Input.isKeyPressed(KeyCode.DOWN)) {
//            System.out.println("Pressed DOWN");
            velocity.x = 0;
            velocity.y = speed;

            }
        else if (Input.isKeyPressed(KeyCode.LEFT)) {
//            System.out.println("Pressed LEFT");
            velocity.y = 0;
            velocity.x = -speed;
        }
        else if (Input.isKeyPressed(KeyCode.RIGHT)) {
//            System.out.println("Pressed RIGHT");
            velocity.y = 0;
            velocity.x = speed;
        }
        /*else{
            velocity.y = 0;
            velocity.x = 0;

        }*/
        head.position = head.position.add(velocity);
    }

    @Override
    public void draw(GraphicsContext gc) {// will be applied to all the sections
        this.head.draw(gc);// will be replaced with a loop iterating over all the sections
    }
}
