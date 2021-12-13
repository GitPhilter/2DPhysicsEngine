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
    protected Timer timer;

    public PhysicsEngine2D(){
        //
    }

    public PhysicsEngine2D(int width, int height){
        this.width = width;
        this.height = height;
        objects = new ArrayList<>();
        physicsManager = new PhysicsManager_2();
        engineFrame = new EngineFrame(this);
    }

    public void addObject(PhysicalObject object){
        objects.add(object);
    }

    public ArrayList<PhysicalObject> getObjects(){
        return objects;
    }

    public void tick(){
        physicsManager.moveObjects(this);
        engineFrame.repaint();
    }

    public void start(){
        start(17);
    }

    public void start(int msPerFrame){
        timer = new Timer(msPerFrame, this);
        timer.start();
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
