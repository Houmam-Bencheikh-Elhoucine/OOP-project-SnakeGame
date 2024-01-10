package org.example.snakegame.gameclasses;

import javafx.scene.input.KeyCode;
import org.example.snakegame.controllerclasses.Input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyboardPlayerSnake extends SnakeAbstract{
    HashMap<String, KeyCode> inputKeys;
    public KeyboardPlayerSnake(float x, float y, float w, float h, HashMap<String, KeyCode> inputKeys){
        super(x, y, w, h);
        // input keys must have the following form
        /*
         * {"up": keyUP, "down": keyDown, "left": keyLeft, "right": keyRight}
         */
        this.inputKeys = new HashMap<>(inputKeys);
    }
    void updateVelocity(){
        if(Input.isKeyPressed(inputKeys.get("up"))){
            if(velocity.y == 0) {
                velocity.y = -speed;
                velocity.x = 0;
            }
        }
        else if(Input.isKeyPressed(inputKeys.get("down"))){
            if(velocity.y == 0) {
                velocity.y = speed;
                velocity.x = 0;
            }
        }
        else if(Input.isKeyPressed(inputKeys.get("left"))){
            if(velocity.x == 0) {
                velocity.x = -speed;
                velocity.y = 0;
            }
        }
        else if(Input.isKeyPressed(inputKeys.get("right"))){
            if(velocity.x == 0) {
                velocity.x = speed;
                velocity.y = 0;
            }
        }
    }
}
