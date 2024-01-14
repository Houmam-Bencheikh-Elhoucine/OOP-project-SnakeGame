package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.globals.GameParameters;

public class MousePlayerSnake extends SnakeAbstract{

    public MousePlayerSnake(float x, float y, float w, float h) {
        super(x, y, w, h, true, GameParameters.score1);
        this.velocity.x = speed;
    }

    @Override
    void updateVelocity() {
        Vec2 mousePos = Input.getMousePos();
        this.velocity = mousePos.sub(position);
        this.velocity.normalizeLocal();
        this.velocity.mulLocal(speed);
//        System.out.println(this.velocity);
    }
    @Override
    public void update(double delta){
        super.update(delta);
        //System.out.println("position: "+this.position+" velocity = "+velocity);
    }
}
