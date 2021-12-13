package engine.physics.manager.helpers;

import engine.PhysicsEngine2D;
import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.physics.Position;

public final class BoundaryCheck {

    public static Position boundaryCollisionCheck(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        boolean everythingChecked = false;
        int numberOfIterations = 0;
        int maxNumberOfIterations = 3;
        while(!everythingChecked){
            ++numberOfIterations;
            everythingChecked = true;
            Position tempPosition = new Position(newPosition);
            newPosition = checkSouthernBorderCollision(object, newPosition, engine);
            if(tempPosition.getX() != newPosition.getX() || tempPosition.getY() != newPosition.getY()) everythingChecked = false;
            tempPosition = new Position(newPosition);
            newPosition = checkNorthernBorderCollision(object, newPosition, engine);
            if(tempPosition.getX() != newPosition.getX() || tempPosition.getY() != newPosition.getY()) everythingChecked = false;
            tempPosition = new Position(newPosition);
            newPosition = checkWesternBorderCollision(object, newPosition, engine);
            if(tempPosition.getX() != newPosition.getX() || tempPosition.getY() != newPosition.getY()) everythingChecked = false;
            tempPosition = new Position(newPosition);
            newPosition = checkEasternBorderCollision(object, newPosition, engine);
            if(tempPosition.getX() != newPosition.getX() || tempPosition.getY() != newPosition.getY()) everythingChecked = false;
            if(numberOfIterations > maxNumberOfIterations) {
                everythingChecked = true;
                newPosition = new Position(object.getPosition().getX(), object.getPosition().getY());
                object.setXSpeed(-object.getXSpeed());
                object.setYSpeed(-object.getYSpeed());
            }
        }
        return newPosition;
    }

    private static Position checkSouthernBorderCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularObject co = (CircularObject) object;
        if(newPosition.getY() > engine.getHeight() - co.getRadius()){
            //System.out.println(co.getName() + " has crossed the southern border!");
            //System.out.println("co new position: " + newPosition);
            double yDelta = Math.abs((engine.getHeight() - co.getRadius()) - newPosition.getY());
            //System.out.println("yDelta= " + yDelta);
            double xDelta = 0;
            if(co.getYSpeed() != 0){
                xDelta = Math.abs((yDelta / co.getYSpeed()) * co.getXSpeed());
            }
            //System.out.println("xDelta= " + xDelta);
            double newX = newPosition.getX() - xDelta;
            double newY = newPosition.getY() - yDelta;
            newPosition = new Position(newX, newY);
            // direction
            // x stays the same
            double newYDir = co.getYSpeed() * -1.0;
            co.setYSpeed(newYDir);
            //System.out.println("corrected newPosition: " + newPosition);
        }
        return newPosition;
    }

    private static Position checkNorthernBorderCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularObject co = (CircularObject) object;
        if(newPosition.getY() < co.getRadius()){
            //System.out.println(co.getName() + " has crossed the northern border!");
            //System.out.println("co new position: " + newPosition);
            double yDelta = Math.abs((co.getRadius()) - newPosition.getY());
            //System.out.println("yDelta= " + yDelta);
            double xDelta = 0;
            if(co.getYSpeed() != 0){
                xDelta = Math.abs((yDelta / co.getYSpeed()) * co.getXSpeed());
            }
            //System.out.println("xDelta= " + xDelta);
            double newX = newPosition.getX() + xDelta;
            double newY = newPosition.getY() + yDelta;
            newPosition = new Position(newX, newY);
            // direction
            // x stays the same
            double newYDir = co.getYSpeed() * -1.0;
            co.setYSpeed(newYDir);
            //System.out.println("corrected newPosition: " + newPosition);
        }

        return newPosition;
    }

    private static Position checkWesternBorderCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularObject co = (CircularObject) object;
        //System.out.println("co new position: " + newPosition);
        if(newPosition.getX() < co.getRadius()){
            //System.out.println(co.getName() + " has crossed the western border!");
            //System.out.println(co.getName() + " new position: " + newPosition);
            double xDelta = Math.abs((co.getRadius()) - newPosition.getX());
            //System.out.println("xDelta= " + xDelta);
            double yDelta = 0;
            if(co.getXSpeed() != 0){
                yDelta = Math.abs((xDelta / co.getXSpeed()) * co.getYSpeed());
            }
            //System.out.println("yDelta= " + yDelta);
            double newX = newPosition.getX() + xDelta;
            double newY = newPosition.getY() - yDelta;
            newPosition = new Position(newX, newY);
            // direction
            // y stays the same
            double newXDir = co.getXSpeed() * -1.0;
            co.setXSpeed(newXDir);
            //System.out.println("corrected newPosition: " + newPosition);
        }
        return newPosition;
    }

    private static Position checkEasternBorderCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularObject co = (CircularObject) object;
        //System.out.println("co new position: " + newPosition);
        if(newPosition.getX() > engine.getWidth() - co.getRadius()){
            //System.out.println(co.getName() + " has crossed the western border!");
            double xDelta = Math.abs((engine.getWidth() - co.getRadius()) - newPosition.getX());
            //System.out.println("yDelta= " + yDelta);
            double yDelta = 0;
            if(co.getYSpeed() != 0){
                yDelta = Math.abs((xDelta / co.getXSpeed()) * co.getYSpeed());
            }
            //System.out.println("xDelta= " + xDelta);
            double newX = newPosition.getX() - xDelta;
            double newY = newPosition.getY() - yDelta;
            newPosition = new Position(newX, newY);
            // direction
            // y stays the same
            double newXDir = co.getXSpeed() * -1.0;
            co.setXSpeed(newXDir);
            //System.out.println("corrected newPosition: " + newPosition);

        }
        return newPosition;
    }

}
