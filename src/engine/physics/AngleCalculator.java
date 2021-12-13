package engine.physics;

public final class AngleCalculator {

    public static double getHalfCircleAngleBetweenDirections(Direction direction_1, Direction direction_2){
        double temp = getDirectionScalarProduct(direction_1, direction_2) / (getDirectionAbsolute(direction_1) * getDirectionAbsolute(direction_2));
        return Math.acos(temp) / Math.PI * 180;
    }

    public static double getDirectionScalarProduct(Direction direction_1, Direction direction_2){
        return direction_1.getX() * direction_2.getX() + direction_1.getY() * direction_2.getY();
    }

    public static double getDirectionAbsolute(Direction direction){
        return Math.sqrt(direction.getX() * direction.getX() + direction.getY() * direction.getY());
    }

    public static double getAngleFromDirection(Direction direction){
        Direction northReference = new Direction(1, 0);
        return getAngleFromDirection(northReference, direction);
    }

    public static double getAngleFromDirection(Direction referenceDirection, Direction direction){
        double result = getHalfCircleAngleBetweenDirections(direction, referenceDirection);
        Direction rotatedDirection = getRotationByDegreesClockwise(direction, result);
        if(result == 360.0) return 0.0;
        if(result == 0.0) return 0.0;
        if(!twoDirectionsAreIdentical(rotatedDirection, referenceDirection)){
            return 360 - result;
        }
        return result;
    }

    public static boolean twoDirectionsAreIdentical(Direction a, Direction b){
        double clampMargin = 0.1;
        double xDiff = Math.abs(a.getX() - b.getX());
        if(xDiff < clampMargin) xDiff = 0.0;
        double yDiff = Math.abs(a.getY() - b.getY());
        if(yDiff < clampMargin) yDiff = 0.0;
        if(xDiff == 0.0 && yDiff == 0.0) return true;
        return false;
    }

    public static Direction getRotationByDegreesClockwise(Direction direction, double degrees){
        degrees = degrees / 180 * Math.PI;
        double x = Math.cos(degrees) * direction.getX() - Math.sin(degrees) * direction.getY();
        double y = Math.sin(degrees) * direction.getX() + Math.cos(degrees) * direction.getY();
        return new Direction(x, y);
    }


}
