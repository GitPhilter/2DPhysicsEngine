package enginespawns.airhockey.evaluation;

import enginespawns.airhockey.objects.PlayerStickDisc;
import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;

public class EvaluationInfo {
    private PlayerStickDisc disc;
    private double evaluationValue;

    public EvaluationInfo(PlayerStickDisc disc, double evaluationValue){
        this.disc = disc;
        this.evaluationValue = evaluationValue;
    }

    public PlayerStickDisc getDisc(){
        return disc;
    }

    public double getEvaluationValue(){
        return evaluationValue;
    }

    @Override
    public String toString(){
        return "Disc: " + disc.getName() + ", Eval = " + evaluationValue;
    }

}
