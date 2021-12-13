package enginespawns.airhockey.objects;

import engine.objects.CircularObject;
import engine.physics.Position;
import enginespawns.airhockey.team.TeamEnum;

import java.awt.*;

public class Puck extends CircularHockeyObject {

    public Puck(Position position, int radius, Color color){
        super("AirHockeyPuck", position, 10, color);
        teamEnum = TeamEnum.UNKNOWN;
        setMaxSpeed(20);
    }


}
