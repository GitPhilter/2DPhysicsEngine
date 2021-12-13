package engine;

import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.physics.Position;
import engine.physics.manager.PhysicsManager;
import engine.physics.manager.implementations.PhysicsManager_1;
import engine.physics.manager.implementations.PhysicsManager_2;

import java.awt.*;
import java.util.ArrayList;

public class PhysicsEngine2D {
    private EngineFrame engineFrame;
    ArrayList<PhysicalObject> objects;
    PhysicsManager physicsManager = new PhysicsManager_2();
    final int width = 800;
    final int height = 600;

    public PhysicsEngine2D(){
        objects = new ArrayList<>();
        CircularObject co = new CircularObject("Grün", new Position(100,50), .8, 0, 30, Color.GREEN);
        CircularObject co_2 = new CircularObject("Blau", new Position(240,200), -1, .4, 20, Color.BLUE);
        CircularObject co_3 = new CircularObject("Gelb", new Position(300,300), -1, -1, 20, Color.YELLOW);
        CircularObject co_4 = new CircularObject("Hellgrau", new Position(500,420), -0.5, -1, 40, Color.lightGray);
        CircularObject co_5 = new CircularObject("Orange", new Position(600,550), 0, -1.8, 30, Color.ORANGE);
        CircularObject co_6 = new CircularObject("Magenta", new Position(300,460), -.3, 2, 50, Color.MAGENTA);
        CircularObject co_7 = new CircularObject("Pink", new Position(100,550), .4, .6, 40, Color.PINK);


        objects.add(co);
        objects.add(co_2);
        objects.add(co_3);
        objects.add(co_4);
        objects.add(co_5);
        objects.add(co_6);
        objects.add(co_7);

        engineFrame = new EngineFrame(this);

    }

    public void addObject(PhysicalObject object){
        objects.add(object);
    }

    public ArrayList<PhysicalObject> getObjects(){
        return objects;
    }

    public void tick(){

    }

    public void run(){
        boolean running = true;
        while(running){
            //tick(); // <- hier im tick auch repaint
            //
        }
    }

    public PhysicsManager getPhysicsManager(){
        return physicsManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
