package org.example.snakegame.controllerclasses;

import com.almasb.fxgl.core.math.Vec2;

import static org.example.snakegame.globals.GameParameters.*;

public class Camera {
    private static Vec2 offset = new Vec2(0, 0);

    public static void update(Vec2 newoff){
        offset = newoff.copy();
    }

    public static float getX(){

        return offset.x - (float) WIDTH/2;
    }
    public static float getY(){
        return offset.y - (float) HEIGHT /2;
    }
}
