package engine.objects;

import engine.physics.AngleCalculator;
import engine.physics.Direction;
import engine.physics.Position;

public class PhysicalObject {
    protected final String name;
    protected Position position;
    protected double xSpeed;
    protected double ySpeed;
    protected double maxSpeed;
    protected double xAcceleration;
    protected double yAcceleration;
    protected double maxTotalAcceleration;
    protected double mass;
    protected boolean hasTick = false;

    public PhysicalObject(String name, Position position, double mass){
        this.name = name;
        this.position = new Position(position);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.xAcceleration = 0;
        this.yAcceleration = 0;
        this.maxSpeed = 1;
        this.maxTotalAcceleration = 1;
        this.mass = mass;
    }

    public void tick(){
        System.out.println("PhysicalObject.tick() -> This function is a stub function and should not be called!");
    }

    public void applyAcceleration(){
        this.xSpeed = xSpeed + xAcceleration;
        this.ySpeed = ySpeed + yAcceleration;
        double lengthOfSpeedVector = Math.sqrt(xSpeed*xSpeed + ySpeed*ySpeed);
        // normalize to max speed if necessary
        if(lengthOfSpeedVector >= maxSpeed){
            double divisor = (1.0/maxSpeed) * lengthOfSpeedVector;
            xSpeed = xSpeed / divisor;
            ySpeed = ySpeed / divisor;
        }
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

    public void setHasTick(boolean hasTick){
        this.hasTick = hasTick;
    }

    public boolean hasTick(){
        return hasTick;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxTotalAcceleration() {
        return maxTotalAcceleration;
    }

    public void setMaxTotalAcceleration(double maxTotalAcceleration) {
        this.maxTotalAcceleration = maxTotalAcceleration;
    }


}
