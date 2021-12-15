package engine;

import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.physics.Position;
import engine.physics.manager.PhysicsManager;
import engine.physics.manager.implementations.PhysicsManager_1;
import engine.physics.manager.implementations.PhysicsManager_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PhysicsEngine2D implements ActionListener {
    protected EngineFrame engineFrame;
    protected ArrayList<PhysicalObject> objects;
    protected PhysicsManager physicsManager;
    protected int width;
    protected int height;
    protected boolean isVisible;
    protected boolean running = false;

    public PhysicsEngine2D(){
        //
    }

    public PhysicsEngine2D(int width, int height, boolean isVisible){
        this.width = width;
        this.height = height;
        this.isVisible = isVisible;
        objects = new ArrayList<>();
        physicsManager = new PhysicsManager_2();
        if(isVisible){
            engineFrame = new EngineFrame(this);
        } else {
            engineFrame = null;
        }
    }

    public void addObject(PhysicalObject object){
        objects.add(object);
    }

    public ArrayList<PhysicalObject> getObjects(){
        return objects;
    }

    public void tick(){
        physicsManager.moveObjects(this);
        if(engineFrame != null){
            engineFrame.repaint();
        }
    }

    public void run(){
        running = true;
        int fps = 60;
        int nsPerFrame = (int)Math.round(1000000000.0 / fps);

        while(running){
            double start = System.nanoTime();
            // actual computational steps
            tick();
            //
            if(isVisible){
                engineFrame.repaint();
                double timePassed = System.nanoTime() - start;
                if(timePassed < nsPerFrame){
                    try {
                        Thread.sleep((long)((nsPerFrame - timePassed) / (double)1000000));
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        tick();
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
