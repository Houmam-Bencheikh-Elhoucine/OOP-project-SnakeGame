package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GameLabel extends GameObject{
    String text = "";
    boolean visible;
    boolean centered;
    int fontSize;
    public GameLabel(String txt, boolean visible, boolean centered, Vec2 position, int fontSize){
        this.position = position;
        this.text = txt;
        this.centered = centered;
        this.visible = visible;
        this.fontSize = fontSize;
    }
    public void setText(String s){
        this.text = s;
    }
    public void show(){
        this.visible = true;
    }
    public void hide(){
        this.visible = false;
    }
    @Override
    public void draw(GraphicsContext gc){
        if(visible){
            gc.setFill(Color.BLUE);
            gc.setFont(new Font(fontSize));
            if(centered) {
                gc.setTextAlign(TextAlignment.CENTER);
            }
            else{ gc.setTextAlign(TextAlignment.LEFT);}
            gc.fillText(text, position.x, position.y);
        }
    }
}
