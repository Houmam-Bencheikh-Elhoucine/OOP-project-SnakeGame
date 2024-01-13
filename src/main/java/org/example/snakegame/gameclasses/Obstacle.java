package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends GameObject{
    public Obstacle(int x, int y, int w, int h){
        this.position = new Vec2(x, y);
        this.dims = new Vec2(w, h);
        this.collider = new Rectangle(x- (double) w /2, y- (double) h /2, w, h);
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        gc.fillRect(position.x - dims.x/2, position.y - dims.y/2, dims.x, dims.y);
    }
}
