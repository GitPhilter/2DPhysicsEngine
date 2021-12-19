package neuralnetwork;

import math.Matrix;

import java.io.BufferedWriter;
import java.io.FileWriter;

public final class NeuralNetworkSaver {

    public static void saveNeuralNetwork(NeuralNetwork neuralNetwork, String path){
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(path));
        } catch(Exception e){
            System.out.println("NeuralNetworkSaver.saveNeuralNetwork(): Writer could not be instantiated!");
            return;
        }
        // weightsIH
        writeMatrixToWriter(neuralNetwork.getWeightsIH(), writer);
        writeHashLineToWriter(writer);
        // weightsHO
        writeMatrixToWriter(neuralNetwork.getWeightsHO(), writer);
        writeHashLineToWriter(writer);
        // biasH
        writeMatrixToWriter(neuralNetwork.getBiasH(), writer);
        writeHashLineToWriter(writer);
        // biasO
        writeMatrixToWriter(neuralNetwork.getBiasO(), writer);
        // close writer
        try{
            writer.close();
        } catch(Exception e){
            System.out.println("NeuralNetworkSaver.writeMatrixToWriter(): Could not close Writer!");
        }
    }

    public static void writeMatrixToWriter(Matrix matrix, BufferedWriter writer){
        double[][] data = matrix.getData();
        String currentLine = "";
        for(int r = 0; r < matrix.rows; ++r){
            for(int c = 0; c < matrix.cols; ++c){
                if(c == matrix.cols - 1){
                    currentLine += data[r][c] + "\n";
                } else {
                    currentLine += data[r][c] + ",";

                }
            }
            try{
                writer.write(currentLine);
            } catch(Exception e){
                System.out.println("NeuralNetworkSaver.writeMatrixToWriter(): Could not write line to File!");

            }

        }

    }

    private static void writeHashLineToWriter(BufferedWriter writer){
        try{
            writer.write("#\n");
        } catch(Exception e){
            System.out.println("NeuralNetworkSaver.writeHashLineToWriter(): could not write '#' to Writer!");
        }
    }
}
