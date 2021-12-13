package engine.objects;

import engine.physics.Direction;
import engine.physics.Position;

public class PhysicalObject {
    protected final String name;
    protected Position position;
    protected double xSpeed;
    protected double ySpeed;
    protected double mass;

    public PhysicalObject(String name, Position position){
        this(name, position, 0, 0, 1);
    }

    public PhysicalObject(String name, Position position, double xSpeed, double ySpeed, double mass){
        this.name = name;
        this.position = new Position(position);
        //System.out.println(this.position);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getXSpeed(){
        return xSpeed;
    }

    public void setXSpeed(double xSpeed){
        this.xSpeed = xSpeed;
    }

    public double getYSpeed(){
        return ySpeed;
    }

    public void setYSpeed(double ySpeed){
        this.ySpeed = ySpeed;
    }

    public double getMass(){
        return mass;
    }
}
