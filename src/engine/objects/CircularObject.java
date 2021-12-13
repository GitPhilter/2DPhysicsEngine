package engine.objects;

import engine.physics.Direction;
import engine.physics.Position;

import java.awt.*;

public class CircularObject extends PhysicalObject{
    private double radius;
    private Color color;

    public CircularObject(String name, Position position, double radius, Color color){
        super(name, position,Math.PI * radius * radius);
        this.radius = radius;
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    public Direction getDirectionVector(){
        return new Direction(xSpeed, ySpeed);
    }
}
