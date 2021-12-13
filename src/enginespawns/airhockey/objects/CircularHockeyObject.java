package enginespawns.airhockey.objects;

import engine.objects.CircularObject;
import engine.physics.Position;
import enginespawns.airhockey.team.TeamEnum;

import java.awt.*;

public class CircularHockeyObject extends CircularObject {
    TeamEnum teamEnum;

    public CircularHockeyObject(String name, Position position, int radius, Color color){
        super(name, position, radius, color);
        this.teamEnum = TeamEnum.UNKNOWN;
    }

    public TeamEnum getTeamEnum(){
        return teamEnum;
    }

    public void setTeamEnum(TeamEnum teamEnum){
        this.teamEnum = teamEnum;
    }
}
