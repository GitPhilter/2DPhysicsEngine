package main;

import engine.PhysicsEngine2D;
import engine.objects.CircularObject;
import engine.physics.Position;
import logger.Logger;

import java.awt.*;

public class Main {

    public static void main(String[] args){
        Logger.print("2DPhysicsEngine.");
        PhysicsEngine2D engine = new PhysicsEngine2D();
        CircularObject co = new CircularObject("Grün", new Position(100,50), 30, Color.GREEN);
        CircularObject co_2 = new CircularObject("Blau", new Position(240,200), 20, Color.BLUE);
        CircularObject co_3 = new CircularObject("Gelb", new Position(300,300), 20, Color.YELLOW);
        CircularObject co_4 = new CircularObject("Hellgrau", new Position(500,420), 40, Color.lightGray);
        CircularObject co_5 = new CircularObject("Orange", new Position(600,550),30, Color.ORANGE);
        CircularObject co_6 = new CircularObject("Magenta", new Position(300,460), 50, Color.MAGENTA);
        CircularObject co_7 = new CircularObject("Pink", new Position(100,550), 40, Color.PINK);
        engine.addObject(co);
        engine.addObject(co_2);
        engine.addObject(co_3);
        engine.addObject(co_4);
        engine.addObject(co_5);
        engine.addObject(co_6);
        engine.addObject(co_7);
        engine.start();

    }
}
