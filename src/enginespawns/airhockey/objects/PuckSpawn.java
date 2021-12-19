package enginespawns.airhockey.objects;

import engine.physics.Position;

/**
 * represents the information necessary for "spawning" a puck
 * in the game world.
 */
public class PuckSpawn {
    private final Position position;
    private final double xSpeed;
    private final double ySpeed;

    public PuckSpawn(Position position, double xSpeed, double ySpeed){
        this.position = new Position(position);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public String toString(){
        return "" + position + ", xSpeed: " + xSpeed + ", ySpeed: " + ySpeed;
    }

    public Position getPosition() {
        return position;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }
}
