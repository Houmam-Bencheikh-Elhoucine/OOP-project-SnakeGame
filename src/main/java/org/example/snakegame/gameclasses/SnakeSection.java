package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.snakegame.controllerclasses.RandGen;

public class SnakeSection extends GameObject {

    public SnakeSection(int x, int y, int w, int h) {
        this.position = new Vec2(x, y);
        this.dims = new Vec2(w, h);
        this.c = Color.rgb(RandGen.randInt(255), RandGen.randInt(255), RandGen.randInt(255));
    }

    public SnakeSection(int x, int y, int w, int h, int r, int g, int b) {
        this.position = new Vec2(x, y);
        this.dims = new Vec2(w, h);
        this.c = Color.rgb(r, g, b);
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(c);
        gc.fillRect(position.x - dims.x/2,
                position.y - dims.y/2,
                dims.x, dims.y); // display the rectangle in a position relative to the center of the object
    }
}
