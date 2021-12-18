package enginespawns.airhockey.evaluation;

import enginespawns.airhockey.objects.playerstickdiscimplementations.NeuralNetworkPlayerStickDisc;

public class EvaluationInfo {
    private NeuralNetworkPlayerStickDisc disc;
    private double evaluationValue;

    public EvaluationInfo(NeuralNetworkPlayerStickDisc disc, double evaluationValue){
        this.disc = disc;
        this.evaluationValue = evaluationValue;
    }

    public NeuralNetworkPlayerStickDisc getDisc(){
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
