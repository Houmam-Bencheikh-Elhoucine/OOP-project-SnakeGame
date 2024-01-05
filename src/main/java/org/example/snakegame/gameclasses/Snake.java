package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.controllerclasses.RandGen;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Snake extends GameObject {
    int speed = 20;
    Vec2 velocity = new Vec2(0, 0);
    List<SnakeSection> sections;
    public Snake(int x, int y, int w, int h) {

        this.c = Color.rgb(RandGen.randInt(255), RandGen.randInt(255), RandGen.randInt(255));
        sections = new LinkedList<>();
        for (int i = 0; i <= 10; i++) {
            sections.add(new SnakeSection(x - w*i, y, w, h, (int) (c.getRed() * 255), (int) (c.getGreen() * 255), (int) (c.getBlue() * 255)));
        }

    }
    @Override
    public void update() {
        if (Input.isKeyPressed(KeyCode.UP) && velocity.y == 0) {
            System.out.println("Pressed UP");
            velocity.x = 0;
            velocity.y = -speed;

        }
        else if (Input.isKeyPressed(KeyCode.DOWN) && velocity.y == 0) {
            System.out.println("Pressed DOWN");
            velocity.x = 0;
            velocity.y = speed;

            }
        else if (Input.isKeyPressed(KeyCode.LEFT) && velocity.x == 0) {
            System.out.println("Pressed LEFT");
            velocity.y = 0;
            velocity.x = -speed;
        }
        else if (Input.isKeyPressed(KeyCode.RIGHT) && velocity.x == 0) {
            System.out.println("Pressed RIGHT");
            velocity.y = 0;
            velocity.x = speed;
        }
        /*else{
            velocity.y = 0;
            velocity.x = 0;

        }*/

        AtomicReference<Vec2> prev_pos = new AtomicReference<>(null);
        // get the reference of the previous section
        sections.forEach(e->{
            if (prev_pos.get() == null){
                Vec2 pos = e.position;
                e.position = e.position.add(velocity);
                prev_pos.set(pos);
            }
            else{
                Vec2 pos = e.position;
                e.position = prev_pos.get();
                prev_pos.set(pos);
            }
        });

    }

    @Override
    public void draw(GraphicsContext gc) {// will be applied to all the sections
        for (SnakeSection s: sections){
            System.out.println(s.position.toString());
            s.draw(gc);
        }
    }
}
