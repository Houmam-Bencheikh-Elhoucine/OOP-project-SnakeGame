package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import org.example.snakegame.controllerclasses.RandGen;

public class AiSnake extends SnakeAbstract{
    int timeSpan = 1;
    private float t = 0;

    public AiSnake(float x, float y, float w, float h) {
        super(x, y, w, h, false, null);
    }

    private void changeDirection(){
        velocity = sections.get(0).position.sub(new Vec2(RandGen.randDouble(position.x - 100, position.x + 100),
                RandGen.randDouble(position.y - 100, position.y + 100))).normalize().mul(speed);
    }
    @Override
    void updateVelocity(){

    }
    public void update(float delta){
        super.update(delta);
        t += delta;
        if(t > timeSpan){
            changeDirection();
            t = 0;
        }
    }

}
