package enginespawns.airhockey.manager.implementations;

import engine.PhysicsEngine2D;
import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.objects.PhysicalObjectPair;
import engine.physics.AngleCalculator;
import engine.physics.Direction;
import engine.physics.Position;
import engine.physics.manager.PhysicsManager;
import engine.physics.manager.helpers.BoundaryCheck;
import enginespawns.airhockey.manager.helpers.AirHockeyBoundaryCheck;

public class AirHockeyPhysicsManager extends PhysicsManager{


    public AirHockeyPhysicsManager(){
        super("AirHockeyPhysicsManager");
    }

    public void moveObjects(PhysicsEngine2D engine){
        //System.out.println("AirHockeyPhysicsManager.moveObjects()");
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
            newPosition = AirHockeyBoundaryCheck.boundaryCollisionCheck(object, newPosition, engine);
            if(tempPosition.getX() != newPosition.getX() || tempPosition.getY() != newPosition.getY()) everythingChecked = false;
            tempPosition = new Position(newPosition);
            newPosition = checkObjectCollision(object, newPosition, engine);
            if(tempPosition.getX() != newPosition.getX() || tempPosition.getY() != newPosition.getY()) everythingChecked = false;
        }
        return newPosition;
    }

    private boolean legitComparingCollisionObjects(PhysicalObject apo, PhysicalObject bpo){
        CircularObject a = (CircularObject) apo;
        CircularObject b = (CircularObject) bpo;
        if(a == b) return false;
        for(PhysicalObjectPair pop : currentCollisionPairs){
            if((pop.getA() == a && pop.getB() == b) || (pop.getA() == b && pop.getB() == a)){
                return false;
            }
        }
        return true;
    }

    private Position checkObjectCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularObject thisObject = (CircularObject) object;
        for(PhysicalObject po : engine.getObjects()){
            CircularObject co = (CircularObject) po;
            if(legitComparingCollisionObjects((CircularObject) object, co)){
                if(newPosition.getDistance(co.getPosition()) < thisObject.getRadius() + co.getRadius()){
                    // correct position
                    double correctionAmount = Math.abs(newPosition.getDistance(co.getPosition()) - (thisObject.getRadius() + co.getRadius()));
                    Direction correctionDirection = co.getPosition().getDirection(thisObject.getPosition());
                    double newX = newPosition.getX() + correctionAmount * correctionDirection.getX();
                    double newY = newPosition.getY() + correctionAmount * correctionDirection.getY();
                    newPosition = new Position(newX, newY);
                    // calculate basic parameters
                    double mass_1 = thisObject.getMass();
                    double mass_2 = co.getMass();
                    double phi_1 = AngleCalculator.getAngleFromDirection(thisObject.getDirectionVector());
                    double phi_2 = AngleCalculator.getAngleFromDirection(co.getDirectionVector());
                    System.out.println("The movement angles are: phi1=" + phi_1 + ", phi2=" + phi_2);
                    Direction collisionDirection = co.getPosition().getDirection(thisObject.getPosition());
                    // compute new directional "speeds"
                    // thisObject
                    double clampThreshold = 0.001;
                    Direction tangent = new Direction(-collisionDirection.getY(), collisionDirection.getX());
                    double tan_1 = thisObject.getXSpeed() * tangent.getX() + thisObject.getYSpeed() * tangent.getY();
                    double tan_2 = co.getXSpeed() * tangent.getX() + co.getYSpeed() * tangent.getY();
                    double newXSpeedThisObject = tangent.getX() * tan_1;
                    double newYSpeedThisObject = tangent.getY() * tan_1;
                    if(newXSpeedThisObject < clampThreshold && newXSpeedThisObject > -clampThreshold) newXSpeedThisObject = 0.0;
                    if(newYSpeedThisObject < clampThreshold && newYSpeedThisObject > -clampThreshold) newYSpeedThisObject = 0.0;
                    // co
                    double newXSpeedCo = tangent.getX() * tan_2;
                    double newYSpeedCo = tangent.getY() * tan_2;
                    if(newXSpeedCo < clampThreshold && newXSpeedCo > -clampThreshold) newXSpeedCo = 0.0;
                    if(newYSpeedCo < clampThreshold && newYSpeedCo > -clampThreshold) newYSpeedCo = 0.0;
                    double norm_1 = thisObject.getXSpeed() * collisionDirection.getX() + thisObject.getYSpeed() * collisionDirection.getY();
                    double norm_2 = co.getXSpeed() * collisionDirection.getX() + co.getYSpeed() * collisionDirection.getY();
                    double m_1 = (norm_1 * (mass_1 - mass_2) + 2.0 * mass_2 * norm_2) / (mass_1 + mass_2);
                    double m_2 = (norm_2 * (mass_2 - mass_1) + 2.0 * mass_1 * norm_1) / (mass_1 + mass_2);
                    // set new "speeds"
                    thisObject.setXSpeed(newXSpeedThisObject + collisionDirection.getX() * m_1);
                    thisObject.setYSpeed(newYSpeedThisObject + collisionDirection.getY() * m_1);
                    co.setXSpeed(newXSpeedCo + collisionDirection.getX() * m_2);
                    co.setYSpeed(newYSpeedCo + collisionDirection.getY() * m_2);
                    // add to currentCollisionPairs
                    currentCollisionPairs.add(new PhysicalObjectPair(thisObject, co));
                }
            }
        }
        return newPosition;
    }

}
