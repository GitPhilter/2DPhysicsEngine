package enginespawns.airhockey.training.heuristic;

import engine.objects.PhysicalObject;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.CircularHockeyObject;
import enginespawns.airhockey.team.TeamEnum;
import enginespawns.airhockey.training.AirHockeyTraining;

public class FollowThePuckHeuristic extends Heuristic{

    public FollowThePuckHeuristic(CircularHockeyObject thisObject, AirHockeyTraining engine){
        super(thisObject, engine);
    }

    @Override
    public double getHeuristicValue() {
        double result = 0;
        for(PhysicalObject po : engine.getObjects()) {
            CircularHockeyObject co = (CircularHockeyObject) po;
            if(co.getTeamEnum() == TeamEnum.UNKNOWN){
                double xDist = co.getPosition().getX() - thisObject.getPosition().getX();
                double yDist = co.getPosition().getY() - thisObject.getPosition().getY();
                double maximumPossibleValue = Math.sqrt(engine.getWidth() * engine.getWidth() + engine.getHeight()* engine.getHeight());
                result = maximumPossibleValue - Math.sqrt(xDist*xDist + yDist*yDist);
            }
        }
        return result;
    }
}
