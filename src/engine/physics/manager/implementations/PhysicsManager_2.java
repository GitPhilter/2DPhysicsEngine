package engine.physics.manager.implementations;

import engine.PhysicsEngine2D;
import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.objects.PhysicalObjectPair;
import engine.physics.AngleCalculator;
import engine.physics.Direction;
import engine.physics.Position;
import engine.physics.manager.PhysicsManager;
import engine.physics.manager.helpers.BoundaryCheck;

public class PhysicsManager_2 extends PhysicsManager {

    public PhysicsManager_2(){
        super("PhysicsManager_2");
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
                    // debugging
                    System.out.println(thisObject.getName() + " & " + co.getName() + " are touching!");
                    double totalSumOfSpeedPre = Math.abs(thisObject.getXSpeed()) + Math.abs(thisObject.getYSpeed()) +
                            Math.abs(co.getXSpeed()) + Math.abs(co.getYSpeed());
                    //System.out.println("total speed sum: " + totalSumOfSpeedPre);
                    // correct position
                    double correctionAmount = Math.abs(newPosition.getDistance(co.getPosition()) - (thisObject.getRadius() + co.getRadius()));
                    Direction correctionDirection = co.getPosition().getDirection(thisObject.getPosition());
                    double newX = newPosition.getX() + correctionAmount * correctionDirection.getX();
                    double newY = newPosition.getY() + correctionAmount * correctionDirection.getY();
                    newPosition = new Position(newX, newY);
                    // calculate basic parameters
                    double v_1 = Math.sqrt(thisObject.getXSpeed() * thisObject.getXSpeed() + thisObject.getYSpeed() * thisObject.getYSpeed());
                    double v_2 = Math.sqrt(co.getXSpeed() * co.getXSpeed() + co.getYSpeed() * co.getYSpeed());
                    //System.out.println("The scalar velocities are: v1=" + v_1 + ", v2=" + v_2);
                    double mass_1 = thisObject.getMass();
                    double mass_2 = co.getMass();
                    //System.out.println("The masses are: m1=" + mass_1 + ", m2=" + mass_2);
                    double phi_1 = AngleCalculator.getAngleFromDirection(thisObject.getDirectionVector());
                    double phi_2 = AngleCalculator.getAngleFromDirection(co.getDirectionVector());
                    System.out.println("The movement angles are: phi1=" + phi_1 + ", phi2=" + phi_2);
                    //Direction collisionDirection = thisObject.getPosition().getDirection(co.getPosition());
                    Direction collisionDirection = co.getPosition().getDirection(thisObject.getPosition());
                    //Direction oppositeCollisionDirection = co.getPosition().getDirection(thisObject.getPosition());
                    //double collisionAngle = AngleCalculator.getAngleFromDirection(collisionDirection);
                    //double oppositeCollisionAngle = AngleCalculator.getAngleFromDirection(oppositeCollisionDirection);
                    //System.out.println("the collision direction: " + collisionDirection + ", equals collision angle: " + collisionAngle);
                    // compute new directional "speeds"
                    // thisObject
                    // turn angles into "computable angle"
                    double clampThreshold = 0.001;
                    //collisionAngle = ((collisionAngle / 180) * Math.PI);
                    //oppositeCollisionAngle = ((oppositeCollisionAngle / 180) * Math.PI);
                    Direction tangent = new Direction(-collisionDirection.getY(), collisionDirection.getX());
                    //tangent = ((tangent / 180) * Math.PI);
                    //System.out.println("oppositeCollisionAngle=" + oppositeCollisionAngle);
                    //phi_1 = ((phi_1 / 180) * Math.PI);
                    //phi_2 = ((phi_2 / 180) * Math.PI);
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
                    // debugging
                    //System.out.println("old xSpeed thisObject: " + thisObject.getXSpeed());
                    //System.out.println("old ySpeed thisObject: " + thisObject.getYSpeed());
                    //System.out.println("newXSpeedThisObject: " + newXSpeedThisObject);
                    //System.out.println("newYSpeedThisObject: " + newYSpeedThisObject);
                    //System.out.println("old xSpeed co: " + co.getXSpeed());
                    //System.out.println("old ySpeed co: " + co.getYSpeed());
                    //System.out.println("newXSpeedCo: " + newXSpeedCo);
                    //System.out.println("newYSpeedCo: " + newYSpeedCo);

                    //System.out.println("After the collision: ");
                    //System.out.println("total speed sum: " + totalSumOfSpeedPost);
                    double norm_1 = thisObject.getXSpeed() * collisionDirection.getX() + thisObject.getYSpeed() * collisionDirection.getY();
                    double norm_2 = co.getXSpeed() * collisionDirection.getX() + co.getYSpeed() * collisionDirection.getY();
                    double m_1 = (norm_1 * (mass_1 - mass_2) + 2.0 * mass_2 * norm_2) / (mass_1 + mass_2);
                    double m_2 = (norm_2 * (mass_2 - mass_1) + 2.0 * mass_1 * norm_1) / (mass_1 + mass_2);
                    // set new "speeds"
                    thisObject.setXSpeed(newXSpeedThisObject + collisionDirection.getX() * m_1);
                    thisObject.setYSpeed(newYSpeedThisObject + collisionDirection.getY() * m_1);
                    co.setXSpeed(newXSpeedCo + collisionDirection.getX() * m_2);
                    co.setYSpeed(newYSpeedCo + collisionDirection.getY() * m_2);
                    double totalSumOfSpeedPost = Math.abs(thisObject.getXSpeed()) + Math.abs(thisObject.getYSpeed()) +
                            Math.abs(co.getXSpeed()) + Math.abs(co.getYSpeed());
                    // add to currentCollisionPairs
                    /*
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                     */

                    currentCollisionPairs.add(new PhysicalObjectPair(thisObject, co));
                }
            }
        }
        return newPosition;
    }
}
