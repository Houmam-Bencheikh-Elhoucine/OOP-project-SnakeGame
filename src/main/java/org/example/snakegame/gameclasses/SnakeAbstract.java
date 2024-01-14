package org.example.snakegame.gameclasses;

import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.example.snakegame.controllerclasses.Camera;
import org.example.snakegame.controllerclasses.Input;
import org.example.snakegame.controllerclasses.RandGen;
import org.example.snakegame.globals.GameParameters;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.example.snakegame.globals.GameParameters.*;

public abstract class SnakeAbstract extends GameObject {
    float speed = 100;
    Vec2 velocity = new Vec2(0, 0);
    double collisionDeadZone;
    boolean dead;
    List<SnakeSection> sections;
    public List<GameObject> colliders;
    boolean isPlayer = false;
    AtomicInteger scoreCnt;
    public SnakeAbstract(float x, float y, float w, float h, boolean player, AtomicInteger scoreCnt) {
        this.dead = false;
        isPlayer = player;
        this.scoreCnt = scoreCnt;
        this.dims = new Vec2(w, h);// useful for adding new sections
        this.position = new Vec2(x, y);
        collisionDeadZone = 0.2;// collision dead zone is useful for improving gameplay through not detecting collisions at pixel rate...
        this.c = Color.rgb(RandGen.randInt(150), RandGen.randInt(150), RandGen.randInt(150));
        this.type = "Snake";
        sections = new LinkedList<>();
        colliders = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            sections.add(new SnakeSection(x - (w/2)*i, y, w, collisionDeadZone, (int) (c.getRed() * 255), (int) (c.getGreen() * 255), (int) (c.getBlue() * 255)));
        }
    }
    abstract void updateVelocity();// this method gets modified according to the needs of the subclasses
    @Override
    public void update(double delta) {
        // update velocity
        if(!dead) {
            //System.out.print("not dead ");
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
                if(isPlayer) {
                    Camera.update(e.position);
                    wrap();
                }
            }
            else{
                e.position = prevPos.get().add(
                        e.position.add(
                                prevPos.get().negate()
                        ).normalize().mul(dims.x/2));
                prevPos.set(e.position);
            }
            e.update();
        });
        if(isColliding()){
            for(GameObject go: colliders){
                if(go instanceof Food){
                    ((Collectible) go).collected = true;
                    grow(((Food) go).getValue());
                }
                if((go instanceof SnakeAbstract)||(go instanceof Obstacle)){
                    kill();
                }
            }
        }
        colliders.clear();
    }

    @Override
    public void draw(GraphicsContext gc) {
        // will be applied to all the sections
        //System.out.println(position.toString());
        for (SnakeSection s: sections){
            s.draw(gc);
        }
    }

    void wrap(){
        SnakeSection head = sections.get(0);
        if(head.position.x < 0){
            sections.forEach(e->{
                e.position.x += MAP_WIDTH;
            });
        }
        if(head.position.y < 0){
            sections.forEach(e->{
                e.position.y += MAP_HEIGHT;
            });
        }
        if(head.position.x > MAP_WIDTH){
            sections.forEach(e->{
                e.position.x -= MAP_WIDTH;
            });
        }
        if(head.position.y > MAP_HEIGHT){
            sections.forEach(e->{
                e.position.y -= MAP_HEIGHT;
            });
        }

    }
    boolean isCollidingWithSelf(){
        return false;/*
        SnakeSection head = this.sections.get(0);
        for (int i = 2; i < sections.size(); i++){
            // we ignore the first 3 sections and we focus on the rest
            if(sections.get(i).collider != null){
                if(sections.get(i).collider.getBoundsInLocal().intersects(head.collider.getBoundsInLocal())) return true;
            }
        }
        return false;*/
    }
    boolean isCollidingWithOther(GameObject other){
        SnakeSection head = this.sections.get(0);
        if(other.getType().equals("Snake")){
            for(int i = 1; i < ((SnakeAbstract)other).sections.size(); i++){
                SnakeSection s = ((SnakeAbstract)other).sections.get(i);
                if(s.collider.getBoundsInLocal().intersects(head.collider.getBoundsInLocal())){
                    return true;
                }
            }
        }
        if(other.getType().equals("Snake")){
            return false;
        }
        //System.out.println(other.getType()+other.collider);
        if(other.collider != null) {
            return other.collider.getBoundsInLocal().intersects(head.collider.getBoundsInLocal());
        }
        return false;
    }
    public boolean Collided(GameObject other){
        if (other == this){
            return isCollidingWithSelf();
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
        speed += scs;
        scoreCnt.set(scoreCnt.get() + scs);
        for (int i = 0; i < scs; i++){
            sections.add(new SnakeSection(drop.x, drop.y, dims.x, collisionDeadZone,
                    (int) (c.getRed() * 255),
                    (int) (c.getGreen() * 255),
                    (int) (c.getBlue() * 255)));
        }
    }
    public boolean isDead(){
        return dead;
    }
    public void kill(){// returns the number of points the player collected
        this.dead = true;
    }
}
