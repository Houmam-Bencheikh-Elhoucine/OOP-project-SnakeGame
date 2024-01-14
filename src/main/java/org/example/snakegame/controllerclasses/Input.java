package org.example.snakegame.controllerclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.HashSet;

/**
 * this class is for handling Input events
 * it stores a key if it pressed
 * then the programmer asks if any button is pressed or not...
 * attributes:
 * keys: HashMap: holds all the keys and wether they were pressed or not
 */
public class Input {
    private static HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private static final Vec2 mousePos = new Vec2(0, 0);
    public static void add(KeyCode k, boolean b){
        keys.put(k, b);
    }
    public static void clean(){
        keys = new HashMap<>();
    }
    public static boolean isKeyPressed(KeyCode k){
        Boolean b = keys.get(k);
        if(b == null){
            return false;
        }
        return b;
    }
    public static void setNewMousePos(float x, float y){
        mousePos.x = x;
        mousePos.y = y;
        //System.out.println(mousePos);
    }
    public static Vec2 getMousePos(){
        return mousePos.copy();
    }
}
