package enginespawns.airhockey.objects.playerstickdiscimplementations;

import engine.physics.Position;
import enginespawns.airhockey.objects.PlayerStickDisc;

import java.awt.*;

public class RandomMovementPlayerStickDisc extends PlayerStickDisc {

    public RandomMovementPlayerStickDisc(String name, Position position, int radius, Color color){
        super(name, position, radius, color);
    }

    @Override
    public void tick(){
        //System.out.println("RandomMovementPlayerStickDisc '" + name + "'.tick()");
        xAcceleration = Math.random() * maxTotalAcceleration - (maxTotalAcceleration / 2.0);
        yAcceleration = Math.random() * maxTotalAcceleration - (maxTotalAcceleration / 2.0);
        double totalLengthOfAccelerationVector = Math.sqrt(xAcceleration * xAcceleration + yAcceleration * yAcceleration);
        if(totalLengthOfAccelerationVector > maxTotalAcceleration){
            double divisor = (1.0/maxTotalAcceleration) * totalLengthOfAccelerationVector;
            xAcceleration = xAcceleration / divisor;
            yAcceleration = yAcceleration / divisor;
        }
        //System.out.println("xAcceleration set to:" + xAcceleration + ", yAcceleration set to: " + yAcceleration);
        applyAcceleration();
    }
}
