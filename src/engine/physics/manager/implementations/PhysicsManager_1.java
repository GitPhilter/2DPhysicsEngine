package engine.physics.manager.implementations;

import engine.PhysicsEngine2D;
import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.objects.PhysicalObjectPair;
import engine.physics.Direction;
import engine.physics.Position;
import engine.physics.manager.PhysicsManager;
import engine.physics.manager.helpers.BoundaryCheck;

public class PhysicsManager_1 extends PhysicsManager {

    public PhysicsManager_1(){
        super("PhysicsManager_1");
    }

    public void moveObjects(PhysicsEngine2D engine){
        currentCollisionPairs.clear();
        for(PhysicalObject po : engine.getObjects()){
            moveObject(po, engine);
        }
    }

    @Override
    public void moveObject(PhysicalObject object, PhysicsEngine2D engine) {
        Position currentPosition = object.getPosition();
        double newX = currentPosition.getX() + object.getXSpeed();
        double newY = currentPosition.getY() + object.getYSpeed();
        Position newPosition = new Position(newX, newY);
        newPosition = collisionCheck(object, newPosition, engine);
        object.setPosition(newPosition);
    }

    private Position collisionCheck(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        boolean everythingChecked = false;
        while(!everythingChecked){
            everythingChecked = true;
            Position tempPosition = new Position(newPosition);
            newPosition = BoundaryCheck.boundaryCollisionCheck(object, newPosition, engine);
            if(tempPosition.getX() != newPosition.getX() || tempPosition.getY() != newPosition.getY()) everythingChecked = false;
            tempPosition = new Position(newPosition);
            newPosition = checkObjectCollision(object, newPosition, engine);
            if(tempPosition.getX() != newPosition.getX() || tempPosition.getY() != newPosition.getY()) everythingChecked = false;
        }
        return newPosition;
    }

    private Position checkObjectCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularObject thisObject = (CircularObject) object;
        for(PhysicalObject po : engine.getObjects()){
            CircularObject co = (CircularObject) po;
            if(co != object){
                if(newPosition.getDistance(co.getPosition()) < thisObject.getRadius() + co.getRadius()){
                    double correctionAmount = Math.abs(newPosition.getDistance(co.getPosition()) - (thisObject.getRadius() + co.getRadius()));
                    Direction correctionDirection = co.getPosition().getDirection(thisObject.getPosition());
                    double newX = newPosition.getX() + correctionAmount * correctionDirection.getX();
                    double newY = newPosition.getY() + correctionAmount * correctionDirection.getY();
                    newPosition = new Position(newX, newY);
                    //
                    double massA = 1;
                    double massB = 1;
                    double newThisObjectXSpeed = (thisObject.getXSpeed() * (massA - massB) + (2 * massA * co.getXSpeed())) / (massB + massA);
                    double newThisObjectYSpeed = (thisObject.getYSpeed() * (massA - massB) + (2 * massA * co.getYSpeed())) / (massB + massA);
                    double newCoXSpeed = (co.getXSpeed() * (massB - massA) + (2 * massB * thisObject.getXSpeed())) / (massB + massA);
                    double newCoYSpeed = (co.getYSpeed() * (massB - massA) + (2 * massB * thisObject.getYSpeed())) / (massB + massA);
                    //
                    thisObject.setXSpeed(newThisObjectXSpeed);
                    thisObject.setYSpeed(newThisObjectYSpeed);
                    co.setXSpeed(newCoXSpeed);
                    co.setYSpeed(newCoYSpeed);
                    // add to currentCollisionPairs
                    currentCollisionPairs.add(new PhysicalObjectPair(thisObject, co));
                }
            }
        }
        return newPosition;
    }
}
