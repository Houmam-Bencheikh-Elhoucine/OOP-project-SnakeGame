package org.example.snakegame.gameclasses;

import javafx.scene.input.KeyCode;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.controllerclasses.RandGen;


import static java.lang.Thread.sleep;

public class AI {
    Snake s;
    boolean running = true;
    Thread t = new Thread(()-> {
        try {
            this.play();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    });
    KeyCode[] keyCodes = {KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN,  KeyCode.RIGHT};
    public AI(Snake s){
        this.s = s;
        t.start();
    }

    public void play() throws InterruptedException {// AI plays on another thread
        Input.add(keyCodes[3], true);
        while(running){
            try{
                sleep(RandGen.randInt(500, 1000));
            }catch(InterruptedException e){
                throw new InterruptedException(e.getMessage());
            }
            int k = RandGen.randInt(4);
            Input.add(keyCodes[k], true);
        }
    }
    public void stop(){
        running = false;
    }
}
