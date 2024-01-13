package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class GameObject {
    Vec2 position; // the position of the center of the image
    Vec2 dims; // the dimensions of the image (width, height)
    Color c;// the color of the section
    String type = "GameObject";
    Shape collider;
    public String getType(){
        return type;
    }
    public void update(double delta) {
    }

    void setup() {
    }

    public void draw(GraphicsContext gc) {
    }

}
