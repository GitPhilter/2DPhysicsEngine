package enginespawns.airhockey.evolution;

import enginespawns.airhockey.evaluation.EvaluationInfo;
import enginespawns.airhockey.evaluation.shootingrange.ShootingRange;
import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;
import evolutioninterface.EvoPanel;
import neuralnetwork.NeuralNetwork;

public final class EvolutionFromOneParent {

    /**
     * a simple algorithm for evolving one disc.
     * @param numberOfCycles the number of cycles that are executed.
     * @param numberOfChildren the number of children that are created in each cycle.
     * @return the best of all children from the last generation.
     */
    public static NeuralNetworkPlayerStickDisc simpleCycle(NeuralNetworkPlayerStickDisc parent, int numberOfCycles, int numberOfChildren){
        // create family
        PlayerStickDisc[] discs = new PlayerStickDisc[numberOfChildren + 1];
        discs[0] = parent;
        for(int i = 0; i < discs.length - 1; ++i){
            discs[i + 1] = Evolution.getRandomChild(parent);
        }
        // gather evaluations
        ShootingRange range = new ShootingRange(false);
        EvaluationInfo[] evaluationInfos = new EvaluationInfo[discs.length];
        for(int i = 0; i < evaluationInfos.length; ++i){
            evaluationInfos[i] = range.getEvaluationOfPlayerDisc(discs[i]);
        }
        int currentCycle = 1;
        NeuralNetworkPlayerStickDisc bestDisc = (NeuralNetworkPlayerStickDisc) Evolution.getBestFromEvaluationInfos(evaluationInfos, 1)[0];
        while(currentCycle < numberOfCycles){
            // create family
            ++currentCycle;
            discs = new PlayerStickDisc[numberOfChildren + 1];
            discs[0] = bestDisc;
            for(int i = 0;i < discs.length - 1; ++i){
                discs[i + 1] = Evolution.getRandomChild(bestDisc);
            }
            // gather evaluations
            evaluationInfos = new EvaluationInfo[discs.length];
            for(int i = 0; i < evaluationInfos.length; ++i){
                evaluationInfos[i] = range.getEvaluationOfPlayerDisc(discs[i]);
            }
            bestDisc = (NeuralNetworkPlayerStickDisc) Evolution.getBestFromEvaluationInfos(evaluationInfos, 1)[0];
            //System.out.println("bestDisc: " + bestDisc.getName());
        }
        return bestDisc;
    }
}
