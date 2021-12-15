package enginespawns.airhockey.training;

public class AirHockeyDataSet {
    private double[] inputValues;
    private double[] outputValues;

    public AirHockeyDataSet(double[] input, double[] output){
        this(input[0], input[1], input[2], input[3], input[4], input[5], input[6], input[7],
                output[0], output[1]);
    }

    public AirHockeyDataSet(double playerXPosValue, double playerYPosValue, double opponentVectorXComponent,
                            double opponentVectorYComponent, double puckVectorXComponent,
                            double puckVectorYComponent, double puckXSpeedValue, double puckYSpeedValue,
                            double outputXSpeedValue, double outputYSpeedValue){
        inputValues = new double[8];
        inputValues[0] = playerXPosValue;
        inputValues[1] = playerYPosValue;
        inputValues[2] = opponentVectorXComponent;
        inputValues[3] = opponentVectorYComponent;
        inputValues[4] = puckVectorXComponent;
        inputValues[5] = puckVectorYComponent;
        inputValues[6] = puckXSpeedValue;
        inputValues[7] = puckYSpeedValue;
        //
        outputValues = new double[2];
        outputValues[0] = outputXSpeedValue;
        outputValues[1] = outputYSpeedValue;
    }

    public double[] getInputValues() {
        return inputValues;
    }

    public double[] getOutputValues() {
        return outputValues;
    }
}


