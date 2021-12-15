package engine.physics.manager.helpers;

import engine.PhysicsEngine2D;
import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.physics.Position;

public class BoundaryCheck {

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

    protected static Position checkSouthernBorderCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularObject co = (CircularObject) object;
        if(newPosition.getY() > engine.getHeight() - co.getRadius()){
            double yDelta = Math.abs((engine.getHeight() - co.getRadius()) - newPosition.getY());
            double xDelta = 0;
            if(co.getYSpeed() != 0){
                xDelta = Math.abs((yDelta / co.getYSpeed()) * co.getXSpeed());
            }
            double newX = newPosition.getX() - xDelta;
            double newY = newPosition.getY() - yDelta;
            newPosition = new Position(newX, newY);
            // direction
            // x stays the same
            double newYDir = co.getYSpeed() * -1.0;
            co.setYSpeed(newYDir);
        }
        return newPosition;
    }

    protected static Position checkNorthernBorderCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularObject co = (CircularObject) object;
        if(newPosition.getY() < co.getRadius()){
            double yDelta = Math.abs((co.getRadius()) - newPosition.getY());
            double xDelta = 0;
            if(co.getYSpeed() != 0){
                xDelta = Math.abs((yDelta / co.getYSpeed()) * co.getXSpeed());
            }
            double newX = newPosition.getX() + xDelta;
            double newY = newPosition.getY() + yDelta;
            newPosition = new Position(newX, newY);
            // direction
            // x stays the same
            double newYDir = co.getYSpeed() * -1.0;
            co.setYSpeed(newYDir);
        }
        return newPosition;
    }

    protected static Position checkWesternBorderCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularObject co = (CircularObject) object;
        if(newPosition.getX() < co.getRadius()){
            double xDelta = Math.abs((co.getRadius()) - newPosition.getX());
            double yDelta = 0;
            if(co.getXSpeed() != 0){
                yDelta = Math.abs((xDelta / co.getXSpeed()) * co.getYSpeed());
            }
            double newX = newPosition.getX() + xDelta;
            double newY = newPosition.getY() - yDelta;
            newPosition = new Position(newX, newY);
            // direction
            // y stays the same
            double newXDir = co.getXSpeed() * -1.0;
            co.setXSpeed(newXDir);
        }
        return newPosition;
    }

    protected static Position checkEasternBorderCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularObject co = (CircularObject) object;
        if(newPosition.getX() > engine.getWidth() - co.getRadius()){
            double xDelta = Math.abs((engine.getWidth() - co.getRadius()) - newPosition.getX());
            double yDelta = 0;
            if(co.getYSpeed() != 0){
                yDelta = Math.abs((xDelta / co.getXSpeed()) * co.getYSpeed());
            }
            double newX = newPosition.getX() - xDelta;
            double newY = newPosition.getY() - yDelta;
            newPosition = new Position(newX, newY);
            // direction
            // y stays the same
            double newXDir = co.getXSpeed() * -1.0;
            co.setXSpeed(newXDir);
        }
        return newPosition;
    }

}
