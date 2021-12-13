package enginespawns.airhockey.objects;

import engine.objects.CircularObject;
import engine.physics.Position;
import enginespawns.airhockey.team.TeamEnum;

import java.awt.*;

public class PlayerStickDisc extends CircularHockeyObject {


    public PlayerStickDisc(String name, Position position, int radius, Color color){
        super(name, position, radius, color);
        setHasTick(true);
        setMaxSpeed(10);
        setMaxTotalAcceleration(4);

    }

    @Override
    public void tick(){
        System.out.println("PlayerStickDisc '" + name + "'.tick() -> empty stub function. Please use a subclass of the PlayerStickDisc!");
    }


}
