package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.controllerclasses.RandGen;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public abstract class SnakeAbstract extends GameObject {
    int speed = 50;
    Vec2 velocity = new Vec2(0, 0);
    Vec2 collisionDeadZone;
    boolean dead = false;
    List<SnakeSection> sections;
    public List<GameObject> colliders;
    public SnakeAbstract(float x, float y, float w, float h) {
        this.dead = false;
        this.dims = new Vec2(w, h);// useful for adding new sections
        this.position = new Vec2(x, y);
        collisionDeadZone = dims.mul(0.2);// collision dead zone is useful for improving gameplay through not detecting collisions at pixel rate...
        this.c = Color.rgb(RandGen.randInt(150), RandGen.randInt(150), RandGen.randInt(150));
        this.type = "Snake";
        sections = new LinkedList<>();
        colliders = new LinkedList<>();
        for (int i = 0; i < 50; i++) {
            sections.add(new SnakeSection(x - (w/2)*i, y, w, h, (int) (c.getRed() * 255), (int) (c.getGreen() * 255), (int) (c.getBlue() * 255)));
        }
    }
    abstract void updateVelocity();// this method gets modified according to the needs of the subclasses
    @Override
    public void update(double delta) {
        // update velocity
        if(!dead) {
            updateVelocity();
        }
        else{
            velocity = new Vec2(0, 0);
        }
        AtomicReference<Vec2> prevPos = new AtomicReference<>(null);
        // get the reference of the previous section
        sections.forEach(e->{
            if (prevPos.get() == null){
                e.position = e.position.add(velocity.mul(delta));
                prevPos.set(e.position);
            }
            else{
                e.position = prevPos.get().add(
                        e.position.add(
                                prevPos.get().negate()
                        ).normalize().mul(dims.x/2));
                prevPos.set(e.position);
            }
        });
        if(isColliding()){
            for(GameObject go: colliders){
                if(go instanceof Food){

                    ((Collectible) go).collected = true;
                    grow(((Food) go).getValue());
                }
                if((go instanceof SnakeAbstract)||(go instanceof Obstacle)){
                    System.out.println(kill());
                }
            }
        }
//        colliders.clear();
    }

    @Override
    public void draw(GraphicsContext gc) {
        // will be applied to all the sections
        //System.out.println(position.toString());
        for (SnakeSection s: sections){
            s.draw(gc);
        }
    }

    // collisionX, verifies overlaps accross X axis
    boolean collisionX(GameObject other){
        return
                Math.abs(this.position.x - other.position.x) < (this.dims.x + other.dims.x)/2.0 - collisionDeadZone.x;
    }
    // collisionY, checks overlaps accros Y axis
    boolean collisionY(GameObject other){
        return
                Math.abs(this.position.y - other.position.y) < (this.dims.y + other.dims.y)/2.0 - collisionDeadZone.y;
    }
    boolean isCollidingWithOther(GameObject other){
        if (other.getType().equals("Snake")){
            for(SnakeSection s : ((SnakeAbstract)other).sections){
                //collision with a snake is when the head hits the body of the other snake
                if(collisionY(s) && collisionX(s))return true;
            }
            return false;
        }
        return collisionX(other) && collisionY(other);
    }
    public boolean Collided(GameObject other){
        if (other == this){
            return false;
        }
        return isCollidingWithOther(other);
    }
    public boolean isColliding(){
        return !colliders.isEmpty();
    }
    public void addCollider(GameObject other){
        colliders.add(other);
    }
    public void grow(int scs){
        int sz = sections.size();
        Vec2 drop = sections.get(sz-2).position;
        for (int i = 0; i < scs; i++){
            sections.add(new SnakeSection(drop.x, drop.y, dims.x, dims.y,
                    (int) (c.getRed() * 255),
                    (int) (c.getGreen() * 255),
                    (int) (c.getBlue() * 255)));
        }
    }
    public boolean isDead(){
        return dead;
    }
    public int kill(){// returns the number of points the player collected
        this.dead = true;
        return sections.size();
    }
}
