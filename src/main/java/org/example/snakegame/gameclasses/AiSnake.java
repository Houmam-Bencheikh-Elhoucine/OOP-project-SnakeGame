package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.controllerclasses.RandGen;

public class AiSnake extends SnakeAbstract{
    int timeSpan = 60;
    private float t;

    public AiSnake(float x, float y, float w, float h) {
        super(x, y, w, h, false, null);
        velocity.x = speed;
        t = 0;
    }

    void updateVelocity(){
        if(t > timeSpan){
            t = 0;
            this.velocity = Vec2.fromAngle(RandGen.randDouble(velocity.angle()-90, velocity.angle() + 90)).mul(speed);
            timeSpan = RandGen.randInt(5, 10) * 60;
        }
        t++;
    }
}
