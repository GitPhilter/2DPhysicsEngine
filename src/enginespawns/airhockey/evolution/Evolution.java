package enginespawns.airhockey.evolution;

import enginespawns.airhockey.evaluation.EvaluationInfo;
import enginespawns.airhockey.evaluation.EvaluationInfoSort;
import enginespawns.airhockey.evaluation.PlayerDiscEvaluation;
import enginespawns.airhockey.evaluation.shootingrange.ShootingRange;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;
import math.Matrix;
import neuralnetwork.NeuralNetwork;

import java.awt.*;
import java.util.Random;

public final class Evolution {

    // TODO: implement
    public static NeuralNetworkPlayerStickDisc getBestNeuralNetworkDiscAfterNGenerations
    (int numberOfIndividualsPerGenerations, int numberOfGenerations, int numberOfGamesPerEvaluation,
     double fractionOfBestPickedPerGeneration){
        int numberOfDiscsPickedPerGeneration = (int)Math.round(numberOfIndividualsPerGenerations * fractionOfBestPickedPerGeneration);
        System.out.println("Discs picked per generation: " + numberOfDiscsPickedPerGeneration);
        NeuralNetworkPlayerStickDisc[] discs = new NeuralNetworkPlayerStickDisc[numberOfIndividualsPerGenerations];
        EvaluationInfo[] evalInfos = new EvaluationInfo[discs.length];
        for(int e = 0; e < numberOfGenerations; ++e){
            double startTime = System.nanoTime();
            System.out.println("Generation no: " + (e + 1));
            for(int i = 0; i < discs.length; ++i){
                discs[i] = new NeuralNetworkPlayerStickDisc("EvolutionDisc_" + i, null, 20, Color.BLUE, null);
                //System.out.println("simulating disc no: " + i);
                evalInfos[i] = new EvaluationInfo(discs[i], PlayerDiscEvaluation.getEvaluationVsRandom(discs[i], numberOfGamesPerEvaluation));
            }
            // pick the best
            evalInfos = EvaluationInfoSort.sort(evalInfos);
            EvaluationInfo[] elite = new EvaluationInfo[numberOfDiscsPickedPerGeneration];
            for(int i = 0; i < numberOfDiscsPickedPerGeneration; ++i){
                elite[i] = evalInfos[i];
                System.out.println("Elite[" + i + "]: " + elite[i]);
            }
            // breed
            for(int i = 0; i < numberOfIndividualsPerGenerations; ++i){
                int currentBestDiscIndex = (int)(i * fractionOfBestPickedPerGeneration);
                if(i % (numberOfDiscsPickedPerGeneration) == 0){
                    discs[i] = (NeuralNetworkPlayerStickDisc) elite[currentBestDiscIndex].getDisc();
                } else {
                    NeuralNetworkPlayerStickDisc nd = (NeuralNetworkPlayerStickDisc)elite[currentBestDiscIndex].getDisc();
                            NeuralNetwork randomChildNetwork = getRandomChild(nd.getNeuralNetwork());
                    discs[i] = new NeuralNetworkPlayerStickDisc("EvolutionDisc_" + i, null, 20, Color.BLUE, null, randomChildNetwork);
                }

            }
            //
            double finishTime = System.nanoTime();
            double generationTime = finishTime - startTime;
            double timeInSeconds = (int)((generationTime / 1000000000) * 100.0) / 100.0;
            int generationsLeft = numberOfGenerations - (e + 1);
            double estimatedRemainingTime = ((int)(timeInSeconds * (double)generationsLeft * 100)) / 100.0;
            System.out.println("Generation " + (e + 1) + " took " + timeInSeconds + " seconds.");
            System.out.println("The estimated time for the final " + generationsLeft + " generations is " + estimatedRemainingTime + " seconds.");
        }
        //
        return (NeuralNetworkPlayerStickDisc) evalInfos[0].getDisc();
    }

    public static NeuralNetworkPlayerStickDisc getDiscWithEvalGreaterThanByChance(int n){
        NeuralNetworkPlayerStickDisc randomDisc = new NeuralNetworkPlayerStickDisc("EvolutionDiscRandom", null, 20, Color.BLUE, null);
        boolean found = false;
        double bestSoFar = -101;
        if(PlayerDiscEvaluation.getEvaluationVsRandom(randomDisc, 1) >= n) found = true;
        while(!found){
            randomDisc = new NeuralNetworkPlayerStickDisc("EvolutionDiscRandom", null, 20, Color.BLUE, null);
            double eval = PlayerDiscEvaluation.getEvaluationVsRandom(randomDisc, 5);
            System.out.println("" + eval + ", best: " + bestSoFar);
            if(eval > bestSoFar) bestSoFar = eval;
            if(eval >= 50) found = true;
        }
        return randomDisc;
    }

    public static void getBestDiscVSShootingRange(){
        double startTime = System.nanoTime();
        NeuralNetworkPlayerStickDisc disc = new NeuralNetworkPlayerStickDisc("EvolutionDisc" , null, 20, Color.BLUE, null);
        ShootingRange range = new ShootingRange(false);
        int numberOfInfos = 1000;
        EvaluationInfo[] infos = new EvaluationInfo[numberOfInfos];
        for(int i = 0; i < infos.length; ++i){
            infos[i] = range.getEvaluationOfPlayerDisc(disc);;
        }
        double finishTime = System.nanoTime();
        double totalTime = finishTime - startTime;
        double totalTimeInSeconds = Math.round(((double)totalTime / 1000000000.0) * 100.0) / 100.0;
        System.out.println( numberOfInfos + " EvaluationsInfos were created in " + totalTimeInSeconds + " seconds!");
    }

    public static EvaluationInfo[] getBestFromRandomCreated(int numberOfDiscsCreated,
                                                                          int numberOfBestDiscsReturned){
        EvaluationInfo[] infos = new EvaluationInfo[numberOfDiscsCreated];
        for(int i = 0; i < numberOfDiscsCreated; ++i){
            NeuralNetworkPlayerStickDisc disc = new NeuralNetworkPlayerStickDisc("EvolutionDisc_" + i, null, 20, Color.BLUE, null);
            infos[i] = getAverageOverNGamesVSShootingRange(disc, 4);
        }
        infos = EvaluationInfoSort.sort(infos);
        EvaluationInfo[] elite = new EvaluationInfo[numberOfBestDiscsReturned];
        for(int e = 0; e < elite.length; ++e){
            elite[e] = infos[e];
            System.out.println(elite[e]);
        }
        return elite;
    }

    public static EvaluationInfo getAverageOverNGamesVSShootingRange(PlayerStickDisc disc, int numberOfGames){
        ShootingRange range = new ShootingRange(false);
        double sum = 0;
        for(int i = 0; i < numberOfGames; ++i){
            //System.out.println("game on range no." + (i + 1));
            EvaluationInfo temp = range.getEvaluationOfPlayerDisc(disc);
            sum += temp.getEvaluationValue();
        }
        EvaluationInfo resultInfo = new EvaluationInfo(disc, sum / numberOfGames);
        return resultInfo;
    }

    public static EvaluationInfo[] getAverageOverNGamesVSShootingRangeArray(PlayerStickDisc[] discs, int numberOfGames){
        EvaluationInfo[] result = new EvaluationInfo[discs.length];
        for(int i = 0; i < result.length; ++i){
            result[i] = getAverageOverNGamesVSShootingRange(discs[i], numberOfGames);
        }
        return result;
    }

    public static PlayerStickDisc[] getBestFromEvaluationInfos(EvaluationInfo[] infos, int numberOfBestDiscs){
        PlayerStickDisc[] bestDiscs = new PlayerStickDisc[numberOfBestDiscs];
        infos = EvaluationInfoSort.sort(infos);
        for(int i = 0; i < bestDiscs.length; ++i){
            bestDiscs[i] = infos[i].getDisc();
        }
        return bestDiscs;
    }

    public static NeuralNetworkPlayerStickDisc getRandomChild(NeuralNetworkPlayerStickDisc parent){
        return new NeuralNetworkPlayerStickDisc(parent.getName() + "_child", null,
                (int)parent.getRadius(), parent.getColor(), null, getRandomChild(parent.getNeuralNetwork()));
    }

    public static NeuralNetwork getRandomChild(NeuralNetwork parent){
        // weightsIH
        Matrix weightsIH = parent.getWeightsIH();
        //System.out.println(weightsIH);
        weightsIH = getRandomChild(weightsIH);
        //System.out.println(weightsIH);
        // weightsOH
        Matrix weightsHO = parent.getWeightsHO();
        weightsHO = getRandomChild(weightsHO);
        // biasH
        Matrix biasH = parent.getBiasH();
        biasH = getRandomChild(biasH);
        //
        Matrix biasO = parent.getBiasO();
        biasO = getRandomChild(biasO);
        //
        return new NeuralNetwork(weightsIH, weightsHO, biasH, biasO);
    }

    private static Matrix getRandomChild(Matrix matrix){
        Random rnd = new Random();
        Matrix randomChild = new Matrix(matrix.rows, matrix.cols);
        for(int r = 0; r < matrix.rows; ++r){
            for(int c = 0; c < matrix.cols; ++c){
                randomChild.getData()[r][c] = matrix.getData()[r][c] + rnd.nextGaussian() * 0.01 * matrix.getData()[r][c];
            }
        }
        return randomChild;
    }

}
