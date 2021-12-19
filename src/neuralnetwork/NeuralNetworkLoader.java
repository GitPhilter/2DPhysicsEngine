package neuralnetwork;

import math.Matrix;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class NeuralNetworkLoader {

    public static NeuralNetwork loadFile(String path){
        File file = new File(path);
        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
        } catch(Exception e){
            System.out.println("NeuralNetworkLoader.loadFile(): File could not be Loaded!");
            return null;
        }
        // weightsIH
        Matrix weights_IH = getMatrixFromScanner(scanner);
        //scanner.nextLine();
        // weightsHO
        Matrix weights_HO = getMatrixFromScanner(scanner);
        //scanner.nextLine();
        // biasH
        Matrix bias_H = getMatrixFromScanner(scanner);
        //scanner.nextLine();
        // biasO
        Matrix bias_O = getMatrixFromScanner(scanner);
        return new NeuralNetwork(weights_IH, weights_HO, bias_H, bias_O);
    }

    private static Matrix getMatrixFromScanner(Scanner scanner){
        List<String> strings = new ArrayList<>();
        String currentLine = "";
        try{
            currentLine = scanner.nextLine();
        } catch (Exception e){
            System.out.println("Something is wrong with te file: Matrix could not be read!");
        }

        while(!currentLine.equals("#") && scanner.hasNextLine()){
            System.out.println(currentLine);
            strings.add(currentLine);
            currentLine = scanner.nextLine();
        }
        if(!currentLine.equals("#")){
            System.out.println(currentLine);
            strings.add(currentLine);
        }
        double[][] data = new double[strings.size()][];
        for(int i = 0; i < strings.size(); ++i){
            String[] currentDoubleStrings = strings.get(i).split(",");
            data[i] = new double[currentDoubleStrings.length];
            for(int d = 0; d < currentDoubleStrings.length; ++d){
                try{
                    data[i][d] = Double.parseDouble(currentDoubleStrings[d]);
                } catch(Exception e){
                    System.out.println("NeuralNetworkLoader.getMatrixFromScanner(): could not parse double: " + currentDoubleStrings[d]);
                    return null;
                }
            }
        }
        // create Matrix
        Matrix resultMatrix = new Matrix(data.length, data[0].length);
        resultMatrix.setData(data);
        return resultMatrix;
    }
}
