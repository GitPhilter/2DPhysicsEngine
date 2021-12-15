package enginespawns.airhockey.objects.playerstickdiscimplementations;

import engine.objects.PhysicalObject;
import engine.physics.Position;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.CircularHockeyObject;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.team.TeamEnum;
import enginespawns.airhockey.training.AirHockeyTraining;
import enginespawns.airhockey.training.DataConverter;
import neuralnetwork.NeuralNetwork;

import javax.xml.crypto.Data;
import java.awt.*;

public class NeuralNetworkPlayerStickDisc extends PlayerStickDisc {
    NeuralNetwork neuralNetwork;
    AirHockey engine;
    double maxValue = 0;
    double minValue = 1;

    public NeuralNetworkPlayerStickDisc(String name, Position position, int radius, Color color, AirHockey engine){
        super(name, position, radius, color);
        neuralNetwork = new NeuralNetwork(8,8,2);
        this.engine = engine;
    }

    public NeuralNetworkPlayerStickDisc(String name, Position position, int radius, Color color, AirHockey engine,
                                        NeuralNetwork neuralNetwork){
        super(name, position, radius, color);
        this.neuralNetwork = neuralNetwork;
        this.engine = engine;
    }


    @Override
    public void tick(){
        if(engine == null) return;
        //System.out.println("NeuralnetworkPlayerStickDisc '" + name + "'.tick()");
        double[] originalGameStateData = DataConverter.getNeuralNetworkInputDataFromEngine(this, engine);
        //System.out.println("originalGameStateData: ");
        for(int i = 0; i < 8; ++i){
            //System.out.println("[" + i + "]:" + originalGameStateData[i]);
        }
        double[] output = DataConverter.getActualAccelerationFromNeuralNetworkOutput(neuralNetwork.predict(originalGameStateData));
        //System.out.println("NeuralNetwork unaltered output: ");
        //System.out.println("suggested xAcceleration: " + output[0]);
        //System.out.println("suggested yAcceleration: " + output[1]);

        xAcceleration = output[0];
        yAcceleration = output[1];
        if(xAcceleration > maxValue) maxValue = xAcceleration;
        if(yAcceleration > maxValue) maxValue = yAcceleration;
        if(xAcceleration < minValue) minValue = xAcceleration;
        if(yAcceleration < minValue) minValue = yAcceleration;

        double totalLengthOfAccelerationVector = Math.sqrt(xAcceleration * xAcceleration + yAcceleration * yAcceleration);
        if(totalLengthOfAccelerationVector > maxTotalAcceleration){
            double divisor = (1.0/maxTotalAcceleration) * totalLengthOfAccelerationVector;
            xAcceleration = xAcceleration / divisor;
            yAcceleration = yAcceleration / divisor;
        }
        //System.out.println("maxValue: " + maxValue);
        //System.out.println("minValue: " + minValue);
        //System.out.println("xAcceleration set to:" + xAcceleration + ", yAcceleration set to: " + yAcceleration);
        applyAcceleration();
    }

    public void setEngine(AirHockey engine) {
        this.engine = engine;
    }
}
