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
    private EngineFrame engineFrame;
    ArrayList<PhysicalObject> objects;
    PhysicsManager physicsManager = new PhysicsManager_2();
    final int width = 800;
    final int height = 600;
    private Timer timer;

    public PhysicsEngine2D(){
        objects = new ArrayList<>();
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
