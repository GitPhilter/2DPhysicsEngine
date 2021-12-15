package enginespawns.airhockey.training.trainingdatagenerator;

import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.CircularHockeyObject;
import enginespawns.airhockey.training.AirHockeyDataSet;
import enginespawns.airhockey.training.AirHockeyTraining;
import enginespawns.airhockey.training.heuristic.Heuristic;

import java.util.ArrayList;

public abstract class TrainingDataGenerator{
    protected ArrayList<AirHockeyDataSet> dataSets;
    AirHockeyTraining engine;
    CircularHockeyObject thisObject;
    Heuristic heuristic;

    public TrainingDataGenerator(CircularHockeyObject thisObject, AirHockeyTraining engine, Heuristic heuristic){
        dataSets = new ArrayList<>();
        this.thisObject = thisObject;
        this.engine = engine;
        this.heuristic = heuristic;
    }

    public ArrayList<AirHockeyDataSet> getDataSets(){
        return dataSets;
    }

    /**
     * this is the most important method in the training data generator.
     * It should be called in every game tick, so that it can create data sets
     * in any way that the implementation suggests
     */
    public void tick(){
        //
    }

    public void setEngine(AirHockeyTraining engine) {
        this.engine = engine;
        this.heuristic.setEngine(engine);
    }

    public void reset(){
        dataSets.clear();
    }
}
