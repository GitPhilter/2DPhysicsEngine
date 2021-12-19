package enginespawns.airhockey.evolution;

import enginespawns.airhockey.evaluation.EvaluationInfo;
import enginespawns.airhockey.evaluation.EvaluationInfoSort;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;

public final class Evolution_1 {


    public static PlayerStickDisc evolutionAlgorithm_1(){
        // key parameters
        int numberOfGenerations = 100;
        int numberOfInitialDiscs = 100;
        int numberOfDiscsPerGeneration = 100;
        int numberOfBestKeptPerGeneration = 10;
        int numberOfRepopulatedChildrenPerParent = 6;
        int numberOfGamesPerEvaluation = 4;
        PlayerStickDisc bestDisc = null;
        // initial generation / generation 0
        EvaluationInfo[] startingGeneration = Evolution.getBestFromRandomCreated(numberOfInitialDiscs, numberOfDiscsPerGeneration);
        System.out.println(startingGeneration[0]);
        PlayerStickDisc[] currentDiscs = BasicHelpers.getDiscsFromEvaluationInfos(startingGeneration);
        // generation cycle
        for(int g = 0; g < numberOfGenerations; ++g){
            System.out.println("currentDiscs.length = " + currentDiscs.length);
            System.out.println("Evolution_1.evolutionAlgorithm_1: generation " + (g + 1) + ".");
            // evaluate
            EvaluationInfo[] evaluationInfos = Evolution.getAverageOverNGamesVSShootingRangeArray(currentDiscs, numberOfGamesPerEvaluation);
            PlayerStickDisc[] bestDiscs = Evolution.getBestFromEvaluationInfos(evaluationInfos, numberOfBestKeptPerGeneration);
            // repopulate

            if(g == numberOfGenerations - 1){
                bestDisc = bestDiscs[0];
            } else {
                currentDiscs = Repopulation.repopulate(bestDiscs, numberOfDiscsPerGeneration, numberOfRepopulatedChildrenPerParent);

            }
        }
        return bestDisc;
    }


}
