package enginespawns.airhockey.training.heuristic;

import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.CircularHockeyObject;
import enginespawns.airhockey.training.AirHockeyTraining;

public abstract class Heuristic {
    protected CircularHockeyObject thisObject;
    protected AirHockeyTraining engine;

    public Heuristic(CircularHockeyObject thisObject, AirHockeyTraining engine){
        this.thisObject = thisObject;
        this.engine = engine;
    }

    /**
     * this function must be overwritten in every implementation according
     * to the respective heuristics idea
     * @return the heuristic value depending on the state of the engine from the thisObjects
     * point of view.
     * the higher the value, the better the situation!
     */
    public double getHeuristicValue(){
        //
        return 0;
    }

    public void setEngine(AirHockeyTraining engine) {
        this.engine = engine;
    }
}
