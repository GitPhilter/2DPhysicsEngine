package enginespawns.airhockey.objects.playerstickdiscimplementations;

import engine.PhysicsEngine2D;
import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.training.AirHockeyTraining;
import enginespawns.airhockey.training.trainingdatagenerator.FollowThePuckTrainingDataGenerator;
import enginespawns.airhockey.training.trainingdatagenerator.TrainingDataGenerator;
import neuralnetwork.NeuralNetwork;

import java.awt.*;

public class TrainingPlayerStickDisc extends PlayerStickDisc {
    NeuralNetwork neuralNetwork;
    AirHockeyTraining engine;
    TrainingDataGenerator dataGenerator;

    public TrainingPlayerStickDisc(String name, Position position, int radius, Color color, PhysicsEngine2D engine){
        super(name, position, radius, color);
        neuralNetwork = new NeuralNetwork(8,8,2);
        this.engine = (AirHockeyTraining) engine;
        this.dataGenerator = new FollowThePuckTrainingDataGenerator(this, this.engine);
    }

    public NeuralNetwork getNeuralNetwork(){
        return neuralNetwork;
    }

    @Override
    public void tick(){
        //System.out.println("TrainingPlayerStickDisc '" + name + "'.tick() -> calling the trainingDataGenerator!");
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
        dataGenerator.tick();
        //System.out.println("There are " + dataGenerator.getDataSets().size() + " dataSets so far!");
    }

    public void setEngine(AirHockeyTraining engine){
        this.engine = engine;
        this.dataGenerator.setEngine(engine);
    }

    public void applyTrainingDataFromDattaGeneratorAndDeleteIt(){
        neuralNetwork.fit(dataGenerator.getDataSets(), dataGenerator.getDataSets().size());
        dataGenerator.reset();
    }
}
