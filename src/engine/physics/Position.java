package engine.physics;

public class Position {
    double x;
    double y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Position(Position position){
        this.x = position.getX();
        this.y = position.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Direction getDirection(Position b){
        double xDist = b.getX() - x;
        double yDist = b.getY() - y;
        return new Direction(xDist, yDist);
    }

    public double getDistance(Position b){
        double xDist = b.getX() - x;
        double yDist = b.getY() - y;
        return Math.sqrt(xDist*xDist + yDist * yDist);
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
