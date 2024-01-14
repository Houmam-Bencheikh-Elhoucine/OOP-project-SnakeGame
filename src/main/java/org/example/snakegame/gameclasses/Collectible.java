package org.example.snakegame.gameclasses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.snakegame.controllerclasses.Camera;

public abstract class Collectible extends GameObject {
    public boolean collected = false;

    @Override
    public void draw(GraphicsContext gc){
        if(!collected){
            gc.setFill(c);
            gc.fillOval(position.x-dims.x/2- Camera.getX(), position.y-dims.y/2-Camera.getY(), dims.x, dims.y);
        }
    }

    public boolean isCollected() {
        return collected;
    }
}
