package enginespawns.airhockey.training;

import engine.objects.PhysicalObject;
import enginespawns.airhockey.AirHockey;
import enginespawns.airhockey.objects.CircularHockeyObject;
import enginespawns.airhockey.team.TeamEnum;

/**
 * converts regular data from the game, such as Position-x and -y values
 * into more reasonable numbers;
 */
public final class DataConverter {

    /**
     * returns a normalized input-vector for the NeuralNetwork
     * @param thisObject
     * @param engine
     * @return a double[8] that contains the following data:
     * [0]: player position x
     * [1]: player position y
     * [2]: opponent position x
     * [3]: opponent position y
     * [4]: puck position x
     * [5]: puck position y
     * [6]: puck x speed (not normalized)
     * [7]: puck y speed (not normalized)
     */
    public static double[] getNeuralNetworkInputDataFromEngine(CircularHockeyObject thisObject, AirHockey engine){
        double[] resultingInputData = new double[8];
        for(PhysicalObject po : engine.getObjects()){
            CircularHockeyObject co = (CircularHockeyObject) po;
            if(co == thisObject){
                resultingInputData[0] = co.getPosition().getX() / 800;
                resultingInputData[1] = co.getPosition().getY() / 400;
            } else if(co.getTeamEnum() == TeamEnum.UNKNOWN){
                // puck
                resultingInputData[4] = co.getPosition().getX() / 800;
                resultingInputData[5] = co.getPosition().getY() / 400;
                resultingInputData[6] = co.getXSpeed();
                resultingInputData[7] = co.getYSpeed();
            } else {
                resultingInputData[2] = co.getPosition().getX() / 800;
                resultingInputData[3] = co.getPosition().getY() / 400;
            }
        }
        return resultingInputData;
    }

    /**
     * covert the output of the neural network into realistic acceleration values.
     * @param output the original output data from the neural network
     * @return the converted values that represent the actual acceleration values used by the player discs.
     */
    public static double[] getActualAccelerationFromNeuralNetworkOutput(double[] output){
        double[] convertedValues = new double[2];
        convertedValues[0] = (output[0] - 0.5) * 2;
        convertedValues[1] = (output[1] - 0.5) * 2;
        return convertedValues;
    }

    /**
     * converts actual accelerations from the game into data that would be expected by a
     * neural network to output
     * @param acceleration the original acceleration values from the game
     * @return the values as if they were put out by a neural network
     */
    public static double[] getExpectedNeuralNetworkOutputFromActualAccelerationValues(double[] acceleration){
        double[] convertedValues = new double[2];
        convertedValues[0] = (acceleration[0] / 2) + 0.5;
        convertedValues[1] = (acceleration[1] / 2) + 0.5;
        return convertedValues;
    }

}
