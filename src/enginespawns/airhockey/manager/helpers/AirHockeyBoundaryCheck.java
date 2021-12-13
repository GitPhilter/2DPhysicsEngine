package enginespawns.airhockey.manager.helpers;

import engine.PhysicsEngine2D;
import engine.objects.CircularObject;
import engine.objects.PhysicalObject;
import engine.physics.Position;
import engine.physics.manager.helpers.BoundaryCheck;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.manager.implementations.AirHockeyPhysicsManager;
import enginespawns.airhockey.objects.CircularHockeyObject;
import enginespawns.airhockey.objects.Puck;
import enginespawns.airhockey.team.TeamEnum;

import javax.sound.midi.SysexMessage;

public class AirHockeyBoundaryCheck extends BoundaryCheck{

    public static Position boundaryCollisionCheck(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        //System.out.println("AirHockeyBoundaryCollisionCheck.boundaryCollisionCheck()");
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




    protected static Position checkWesternBorderCollision(PhysicalObject object, Position newPosition, PhysicsEngine2D engine){
        CircularHockeyObject co = (CircularHockeyObject) object;
        if(co.getTeamEnum() == TeamEnum.AWAY){
            if(newPosition.getX() - co.getRadius() < engine.getWidth() / 2.0){
                double xDelta = (newPosition.getX() - co.getRadius()) - engine.getWidth() / 2.0;
                double yDelta = 0;
                if(co.getXSpeed() != 0){
                    yDelta = Math.abs((xDelta / co.getXSpeed()) * co.getYSpeed());
                }
                double newX = newPosition.getX() + xDelta;
                double newY = newPosition.getY() - yDelta;
                newPosition = new Position(newX, newY);
                return newPosition;
            }
        }
        if(newPosition.getX() < co.getRadius() && hasCrossedLeftBorders(object, newPosition, engine)){
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
        CircularHockeyObject co = (CircularHockeyObject) object;
        if(co.getTeamEnum() == TeamEnum.HOME){
            if(newPosition.getX() + co.getRadius() > engine.getWidth() / 2.0){
                double xDelta = (newPosition.getX() + co.getRadius()) - engine.getWidth() / 2.0;
                double yDelta = 0;
                if(co.getXSpeed() != 0){
                    yDelta = Math.abs((xDelta / co.getXSpeed()) * co.getYSpeed());
                }
                double newX = newPosition.getX() + xDelta;
                double newY = newPosition.getY() - yDelta;
                newPosition = new Position(newX, newY);
                return newPosition;
            }
        }
        if(newPosition.getX() > engine.getWidth() - co.getRadius() && hasCrossedRightBorders(object, newPosition,engine)){
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

    protected static boolean hasCrossedRightBorders(PhysicalObject object, Position newPosition, PhysicsEngine2D pe){
        if(!object.getName().equals("AirHockeyPuck")) return true;
        AirHockey engine = (AirHockey) pe;
        object = (Puck) object;
        double oldX = object.getPosition().getX();
        double oldY = object.getPosition().getY();
        double xDistanceTravelled = engine.getWidth() - oldX;
        double yGoalLineIntersection = oldY + (object.getYSpeed()/ object.getXSpeed()) * xDistanceTravelled;
        double xGoalLineIntersection = engine.getWidth();
        Position goalLinePuckPosition = new Position(xGoalLineIntersection, yGoalLineIntersection);
        Position upperPostPosition = new Position(engine.getWidth(), engine.getHeight() * (1.0/4.0));
        Position lowerPostPosition = new Position(engine.getWidth(), engine.getHeight() * (3.0/4.0));
        //System.out.println("oldPosition: " + object.getPosition());
        //System.out.println("goalLineIntersectionPosition: " + goalLinePuckPosition);
        //System.out.println("upperPostPosition: " + upperPostPosition);
        //System.out.println("lowerPostPosition: " + lowerPostPosition);
        boolean isBehindGoalLineAlready = false;
        if(oldX > engine.getWidth()) isBehindGoalLineAlready = true;
        if(!isBehindGoalLineAlready){
            if(oldY > lowerPostPosition.getY() - ((Puck) object).getRadius() &&
                    newPosition.getY() > lowerPostPosition.getY() - ((Puck) object).getRadius()){
                System.out.println("[RightGoal:]Puck is touching the lower post or above!");
                return true;
            }
            if(oldY < upperPostPosition.getY() + ((Puck) object).getRadius() &&
                    newPosition.getY() < upperPostPosition.getY() + ((Puck) object).getRadius()){
                //System.out.println("oldY: " + oldY + ", newY: " + newPosition.getY() + " upperPostY: " + upperPostPosition.getY());
                System.out.println("[RightGoal:]Puck is touching the upper post or above!");
                return true;
            }
        }
        // check upper and lower insides of the goal that are parallel to the xAxis
        // upper
        if(newPosition.getY() <= upperPostPosition.getY() + ((Puck)object).getRadius()){
            double yDelta = Math.abs((newPosition.getY() - ((Puck)object).getRadius()) - upperPostPosition.getY() );
            double xDelta = 0;
            if(object.getYSpeed() != 0){
                xDelta = (yDelta / object.getYSpeed()) * object.getXSpeed();
            }
            double newX = newPosition.getX() + xDelta;
            double newY = newPosition.getY() + yDelta;
            //newPosition = new Position(newX, newY);
            newPosition.setX(newX);
            newPosition.setY(newY);
            // direction
            // x stays the same
            double newYDir = object.getYSpeed() * -1.0;
            object.setYSpeed(newYDir);
            System.out.println("[RightGoal:]I am touching the upper inside of the goal!");
            return false;
        }
        // lower
        if(newPosition.getY() >= lowerPostPosition.getY() - ((Puck)object).getRadius()){
            double yDelta = (newPosition.getY() + ((Puck)object).getRadius()) - upperPostPosition.getY();
            double xDelta = 0;
            if(object.getYSpeed() != 0){
                xDelta = (yDelta / object.getYSpeed()) * object.getXSpeed();
            }
            double newX = newPosition.getX() + xDelta;
            double newY = newPosition.getY() + yDelta;
            newPosition = new Position(newX, newY);
            newPosition.setX(newX);
            newPosition.setY(newY);
            // direction
            // x stays the same
            double newYDir = object.getYSpeed() * -1.0;
            object.setYSpeed(newYDir);
            System.out.println("[RightGoal:]I am touching the lower inside of the goal!");
            return false;
        }
        if(newPosition.getX() > engine.getWidth() - ((Puck) object).getRadius() + engine.getWidth() * ( 1.0/40.0)){
            newPosition.setX(engine.getWidth() - ((Puck) object).getRadius() + engine.getWidth() * (1.0/40.0));
            object.setXSpeed(0);
            object.setYSpeed(0);
            System.out.println("[RightGoal:] I touched the back of the right goal!");
            //engine.homeGoal();
            engine.setPuckIsInRightGoal(true);
            return false;
        }
        return false;
    }

    protected static boolean hasCrossedLeftBorders(PhysicalObject object, Position newPosition, PhysicsEngine2D pe){
        if(!object.getName().equals("AirHockeyPuck")) return true;
        AirHockey engine = (AirHockey) pe;
        object = (Puck) object;
        double oldX = object.getPosition().getX();
        double oldY = object.getPosition().getY();
        double xDistanceTravelled = engine.getWidth() - oldX;
        double yGoalLineIntersection = oldY + (object.getYSpeed()/ object.getXSpeed()) * xDistanceTravelled;
        double xGoalLineIntersection = 0;
        Position goalLinePuckPosition = new Position(xGoalLineIntersection, yGoalLineIntersection);
        Position upperPostPosition = new Position(0, engine.getHeight() * (1.0/4.0));
        Position lowerPostPosition = new Position(0, engine.getHeight() * (3.0/4.0));
        //System.out.println("oldPosition: " + object.getPosition());
        //System.out.println("goalLineIntersectionPosition: " + goalLinePuckPosition);
        //System.out.println("upperPostPosition: " + upperPostPosition);
        //System.out.println("lowerPostPosition: " + lowerPostPosition);
        boolean isBehindGoalLineAlready = false;
        if(oldX < 0) isBehindGoalLineAlready = true;
        if(!isBehindGoalLineAlready){
            if(oldY > lowerPostPosition.getY() - ((Puck) object).getRadius() &&
                    newPosition.getY() > lowerPostPosition.getY() - ((Puck) object).getRadius()){
                System.out.println("[LeftGoal:] Puck is touching the lower left post or above!");
                return true;
            }
            if(oldY < upperPostPosition.getY() + ((Puck) object).getRadius() &&
                    newPosition.getY() < upperPostPosition.getY() + ((Puck) object).getRadius()){
                //System.out.println("oldY: " + oldY + ", newY: " + newPosition.getY() + " upperPostY: " + upperPostPosition.getY());
                System.out.println("[LeftGoal:] Puck is touching the upper post or above!");
                return true;
            }
        }
        // check upper and lower insides of the goal that are parallel to the xAxis
        // upper
        if(newPosition.getY() <= upperPostPosition.getY() + ((Puck)object).getRadius()){
            double yDelta = Math.abs((newPosition.getY() - ((Puck)object).getRadius()) - upperPostPosition.getY() );
            double xDelta = 0;
            if(object.getYSpeed() != 0){
                xDelta = (yDelta / object.getYSpeed()) * object.getXSpeed();
            }
            double newX = newPosition.getX() + xDelta;
            double newY = newPosition.getY() + yDelta;
            //newPosition = new Position(newX, newY);
            newPosition.setX(newX);
            newPosition.setY(newY);
            // direction
            // x stays the same
            double newYDir = object.getYSpeed() * -1.0;
            object.setYSpeed(newYDir);
            System.out.println("[LeftGoal:] I am touching the upper inside of the goal!");
            return false;
        }
        // lower
        if(newPosition.getY() >= lowerPostPosition.getY() - ((Puck)object).getRadius()){
            double yDelta = (newPosition.getY() + ((Puck)object).getRadius()) - upperPostPosition.getY();
            double xDelta = 0;
            if(object.getYSpeed() != 0){
                xDelta = (yDelta / object.getYSpeed()) * object.getXSpeed();
            }
            double newX = newPosition.getX() + xDelta;
            double newY = newPosition.getY() + yDelta;
            newPosition = new Position(newX, newY);
            newPosition.setX(newX);
            newPosition.setY(newY);
            // direction
            // x stays the same
            double newYDir = object.getYSpeed() * -1.0;
            object.setYSpeed(newYDir);
            System.out.println("[LeftGoal:] I am touching the lower inside of the goal!");
            return false;
        }
        if(newPosition.getX() < ((Puck) object).getRadius() - engine.getWidth() * (1.0/40.0)){
            newPosition.setX(((Puck) object).getRadius() - engine.getWidth() * (1.0/40.0));
            object.setXSpeed(0);
            object.setYSpeed(0);
            System.out.println("[LeftGoal:] I touched the back of the goal!");
            //engine.awayGoal();
            engine.setPuckIsInLeftGoal(true);
            return false;
        }
        return false;
    }


}
