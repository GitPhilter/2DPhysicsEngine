package enginespawns.airhockey.training.trainingdatagenerator;

import engine.PhysicsEngine2D;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.CircularHockeyObject;
import enginespawns.airhockey.training.AirHockeyDataSet;
import enginespawns.airhockey.training.AirHockeyTraining;
import enginespawns.airhockey.training.DataConverter;
import enginespawns.airhockey.training.heuristic.FollowThePuckHeuristic;

/**
 * a simple data generator that creates training data whenever the player moves closer
 * to the puck (be this good or bad in the respective situation from a human point of view)
 */
public class FollowThePuckTrainingDataGenerator extends TrainingDataGenerator{
    double previousHeuristicValue = 0;
    double[] previousGameState;
    double[] previousAcceleration;

    public FollowThePuckTrainingDataGenerator(CircularHockeyObject thisObject, AirHockeyTraining engine){
        super(thisObject, engine, new FollowThePuckHeuristic(thisObject, engine));
    }

    @Override
    public void tick(){
        //System.out.println("FollowThePuckTrainingDataGenerator.tick()");
        double currentHeuristicValue = heuristic.getHeuristicValue();
        if(currentHeuristicValue > previousHeuristicValue){
            // generate data
            if(previousGameState != null){
                dataSets.add(new AirHockeyDataSet(previousGameState, previousAcceleration));

            }
        }
        // set data for next tick
        previousHeuristicValue = currentHeuristicValue;
        previousGameState = DataConverter.getNeuralNetworkInputDataFromEngine(thisObject, engine);
        double[] acc = new double[2];
        acc[0] = thisObject.getXSpeed();
        acc[1] = thisObject.getYSpeed();
        previousAcceleration = DataConverter.getExpectedNeuralNetworkOutputFromActualAccelerationValues(acc);
    }
}
