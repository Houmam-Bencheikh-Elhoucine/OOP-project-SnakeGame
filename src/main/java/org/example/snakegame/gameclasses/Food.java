package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.snakegame.controllerclasses.RandGen;

public class Food extends Collectible{
    int value = 0;
    public Food(float x, float y, int value){
        c = Color.rgb(RandGen.randInt(150, 255),
                RandGen.randInt(150, 255),
                RandGen.randInt(150, 255));
        this.position = new Vec2(x, y);
        this.dims = new Vec2(10*value, 10*value);
        this.value = value;
        type = "Food";
    }

    public int getValue() {
        return value;
    }


}
