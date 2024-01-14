package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.snakegame.controllerclasses.Camera;
import org.example.snakegame.controllerclasses.RandGen;

public class SnakeSection extends GameObject {
    public Circle collider;

    public SnakeSection(float x, float y, float w, double deadZone, int r, int g, int b) {
        this.position = new Vec2(x, y);
        this.dims = new Vec2(w, w);
        this.type = "SnakeSection";
        this.collider = new Circle(x, y, (double)w/2 * (1-deadZone));
        this.c = new Color(r/255.0, g/255.0, b/255.0, 1.0);
    }
    void update(){
        this.collider.setCenterX(position.x);
        this.collider.setCenterY(position.y);
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(c);
        //System.out.println(c.getRed()+" "+c.getGreen()+" "+c.getGreen());
        gc.fillOval(position.x - dims.x/2 - Camera.getX(),
                position.y - dims.y/2 - Camera.getY(),
                dims.x, dims.y); // display the rectangle in a position relative to the center of the object

    }
}
