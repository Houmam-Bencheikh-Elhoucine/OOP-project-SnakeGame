package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import org.example.snakegame.controllerclasses.Input;
public class MousePlayerSnake extends SnakeAbstract{

    public MousePlayerSnake(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    void updateVelocity() {
        Vec2 mousePos = Input.getMousePos();
        this.velocity = mousePos.sub(position).normalize().mul(speed);
    }
}
